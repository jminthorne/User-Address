/**
 * 
 */
package com.redhat.training.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.drools.KnowledgeBase;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.UrlResource;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.task.Status;
import org.jbpm.task.User;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.TaskClientHandler.TaskSummaryResponseHandler;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.TaskServiceSession;
import org.jbpm.task.service.hornetq.CommandBasedHornetQWSHumanTaskHandler;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.task.service.responsehandlers.BlockingGetTaskResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskSummaryResponseHandler;
import org.jbpm.workflow.instance.impl.WorkflowProcessInstanceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.training.dao.CustomerAddressDao;
import com.redhat.training.model.customer.Customer;
import com.redhat.training.resources.ws.SaveCustomerInformationServiceClient;

/**
 * @author Josh
 * 
 */
@ApplicationScoped
public class CustomerAddressBP implements Serializable {

	private static final long serialVersionUID = 1965045194833892651L;

	private static final Logger LOG = LoggerFactory.getLogger(CustomerAddressBP.class);

	private static final String URL = "http://localhost:8080/jboss-brms/org.drools.guvnor.Guvnor/package/defaultPackage/LATEST";
	private static final String PROCESS_NAME = "UserAddressDemo";

	private final List<String> groups = new ArrayList<>();
	private final List<Status> statuses = new ArrayList<>();

	private String assignee;

	private Map<String, Object> params = new HashMap<String, Object>();

	private StatefulKnowledgeSession ksession;
	private KnowledgeBuilder kbuilder;
	private KnowledgeBase kbase;
	private TaskServiceSession tsSession;
	private TaskClient client;
	private CommandBasedHornetQWSHumanTaskHandler handler;
	private TaskSummary task;
	private BlockingTaskOperationResponseHandler responseHandler;
	private TaskSummaryResponseHandler summaryHandler;
	private BlockingTaskSummaryResponseHandler taskSummaryResponseHandler;
	private BlockingGetTaskResponseHandler responseHandlerGet;
	private org.drools.runtime.process.ProcessInstance processInstance;
	private LocalTaskService localTaskService;
	private EntityManagerFactory emf;
	private TaskService taskService;
	private WorkflowProcessInstanceImpl wpi;
	private TaskServiceSession taskSession;

	@Inject
	private SaveCustomerInformationServiceClient wsclient;
	@Inject
	private CustomerAddressDao customerAddressDao;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		setKbuilder(KnowledgeBuilderFactory.newKnowledgeBuilder());
		getKbuilder().add(setupUrlResource(), ResourceType.PKG);

		setKbase(getKbuilder().newKnowledgeBase());

		setKsession(getKbase().newStatefulKnowledgeSession());
		getKsession().getWorkItemManager().registerWorkItemHandler("Human Task",
				new CommandBasedHornetQWSHumanTaskHandler(getKsession()));

		getGroups().add("user");

		getStatuses().add(Status.Ready);
		getStatuses().add(Status.InProgress);

		taskService = new TaskService(Persistence.createEntityManagerFactory("org.jbpm.task"),
				SystemEventListenerFactory.getSystemEventListener());
		taskSession = taskService.createSession();

		localTaskService = new LocalTaskService(taskService);
	}

	public void saveNewCustomer(Customer customer, String csr) throws Exception {
		setAssignee(csr);
		taskSession.addUser(new User(getAssignee()));

		if (wsclient == null) {
			wsclient = new SaveCustomerInformationServiceClient();
		}
		wsclient.save(customer);
	}

	private UrlResource setupUrlResource() {

		UrlResource resource = (UrlResource) ResourceFactory.newUrlResource(URL);
		resource.setBasicAuthentication("enabled");
		resource.setUsername("admin");
		resource.setPassword("admin");

		return resource;
	}

	public void startCustomerInformationProcess(Customer customer) throws Exception {
		params.put("cid", customer.getCustomerId());
		params.put("zipcode", customer.getCustomerAddresses().get(0).getZip());
		params.put("csr", getAssignee());

		wpi = (WorkflowProcessInstanceImpl) getKsession().startProcess(PROCESS_NAME, params);
	}

	public Customer startTask(TaskSummary task, String csr) {
		if (wpi == null) {
			wpi = (WorkflowProcessInstanceImpl) ksession.getProcessInstance(task.getProcessInstanceId());
		}
		setAssignee(csr);

		if(task.getStatus() == Status.Ready){
			localTaskService.claim(task.getId(), getAssignee(), getGroups());
			localTaskService.start(task.getId(), getAssignee());
		}

		return customerAddressDao.findUserInformationById((Integer) wpi.getVariable("cid"));

	}

	public void finishVerify(TaskSummary task, String csr) {
		localTaskService.complete(task.getId(), csr, null);
	}

	public void finishCustomerNumber(Customer customer, TaskSummary task, String csr) {
		if (wsclient == null) {
			wsclient = new SaveCustomerInformationServiceClient();
		}
		wsclient.update(customer);

		setAssignee(csr);
		
		localTaskService.complete(task.getId(), getAssignee(), null);
	}

	public List<TaskSummary> getTasks(String csr) {
		setAssignee(csr);

		return localTaskService.getTasksAssignedAsPotentialOwnerByStatus(csr, getStatuses(), "en-UK");
	}

	public void save(Customer customer) {
		customerAddressDao.save(customer);
	}

	public void update(Customer customer) {
		customerAddressDao.updateCustomer(customer);
	}

	/**
	 * @return the ksession
	 */
	public StatefulKnowledgeSession getKsession() {
		return ksession;
	}

	/**
	 * @param ksession
	 *            the ksession to set
	 */
	public void setKsession(StatefulKnowledgeSession ksession) {
		this.ksession = ksession;
	}

	/**
	 * @return the kbuilder
	 */
	public KnowledgeBuilder getKbuilder() {
		return kbuilder;
	}

	/**
	 * @param kbuilder
	 *            the kbuilder to set
	 */
	public void setKbuilder(KnowledgeBuilder kbuilder) {
		this.kbuilder = kbuilder;
	}

	/**
	 * @return the kbase
	 */
	public KnowledgeBase getKbase() {
		return kbase;
	}

	/**
	 * @param kbase
	 *            the kbase to set
	 */
	public void setKbase(KnowledgeBase kbase) {
		this.kbase = kbase;
	}

	/**
	 * @return the client
	 */
	public TaskClient getClient() {
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(TaskClient client) {
		this.client = client;
	}

	/**
	 * @return the handler
	 */
	public CommandBasedHornetQWSHumanTaskHandler getHandler() {
		return handler;
	}

	/**
	 * @param handler
	 *            the handler to set
	 */
	public void setHandler(CommandBasedHornetQWSHumanTaskHandler handler) {
		this.handler = handler;
	}

	/**
	 * @return the responseHandler
	 */
	public BlockingTaskOperationResponseHandler getResponseHandler() {
		return responseHandler;
	}

	/**
	 * @param responseHandler
	 *            the responseHandler to set
	 */
	public void setResponseHandler(BlockingTaskOperationResponseHandler responseHandler) {
		this.responseHandler = responseHandler;
	}

	/**
	 * @return the tsSession
	 */
	public TaskServiceSession getTsSession() {
		return tsSession;
	}

	/**
	 * @param tsSession
	 *            the tsSession to set
	 */
	public void setTsSession(TaskServiceSession tsSession) {
		this.tsSession = tsSession;
	}

	/**
	 * @return the assignee
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee
	 *            the assignee to set
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	/**
	 * @return the summaryHandler
	 */
	public TaskSummaryResponseHandler getSummaryHandler() {
		return summaryHandler;
	}

	/**
	 * @param summaryHandler
	 *            the summaryHandler to set
	 */
	public void setSummaryHandler(TaskSummaryResponseHandler summaryHandler) {
		this.summaryHandler = summaryHandler;
	}

	/**
	 * @return the task
	 */
	public TaskSummary getTask() {
		return task;
	}

	/**
	 * @param task
	 *            the task to set
	 */
	public void setTask(TaskSummary task) {
		this.task = task;
	}

	/**
	 * @return the groups
	 */
	public List<String> getGroups() {
		return groups;
	}

	/**
	 * @return the statuses
	 */
	public List<Status> getStatuses() {
		return statuses;
	}

	/**
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * @return the taskSummaryResponseHandler
	 */
	public BlockingTaskSummaryResponseHandler getTaskSummaryResponseHandler() {
		return taskSummaryResponseHandler;
	}

	/**
	 * @param taskSummaryResponseHandler
	 *            the taskSummaryResponseHandler to set
	 */
	public void setTaskSummaryResponseHandler(BlockingTaskSummaryResponseHandler taskSummaryResponseHandler) {
		this.taskSummaryResponseHandler = taskSummaryResponseHandler;
	}

	/**
	 * @return the responseHandlerGet
	 */
	public BlockingGetTaskResponseHandler getResponseHandlerGet() {
		return responseHandlerGet;
	}

	/**
	 * @param responseHandlerGet
	 *            the responseHandlerGet to set
	 */
	public void setResponseHandlerGet(BlockingGetTaskResponseHandler responseHandlerGet) {
		this.responseHandlerGet = responseHandlerGet;
	}

	/**
	 * @return the processInstance
	 */
	public org.drools.runtime.process.ProcessInstance getProcessInstance() {
		return processInstance;
	}

	/**
	 * @param processInstance
	 *            the processInstance to set
	 */
	public void setProcessInstance(org.drools.runtime.process.ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}
}
