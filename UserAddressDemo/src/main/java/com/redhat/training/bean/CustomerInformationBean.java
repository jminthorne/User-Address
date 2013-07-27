/**
 * 
 */
package com.redhat.training.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jbpm.task.query.TaskSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.training.business.CustomerAddressBP;
import com.redhat.training.model.address.Address;
import com.redhat.training.model.customer.Customer;

/**
 * @author Josh
 * 
 */
@Named
@SessionScoped
public class CustomerInformationBean implements Serializable {

	private static final long serialVersionUID = -7052549481218266017L;

	private static final Logger LOG = LoggerFactory.getLogger(CustomerInformationBean.class);

	@Inject
	private CustomerAddressBP bp;

	private Customer selectedCustomer;
	
	private Address selectedAddress;

	private String csrName;

	private List<TaskSummary> tasks = null;
	
	private TaskSummary selectedTask;

	@PostConstruct
	public void createCustomer() {
		if (getSelectedCustomer() == null) {
			setSelectedCustomer(new Customer());
		}

		if (getSelectedAddress() == null) {
			setSelectedAddress(new Address());
		}
	}

	public boolean startCustomerInformationProcess() throws Exception {
		getSelectedCustomer().getCustomerAddresses().add(getSelectedAddress());
		
		bp.saveNewCustomer(getSelectedCustomer(), getCsrName());
		
		return true;
	}
	
	public void checkTasks(){
		setTasks(bp.getTasks(getCsrName()));
	}
	
	public String startTask(TaskSummary task){
		setSelectedTask(task);
		
		setSelectedCustomer(bp.startTask(getSelectedTask(), getCsrName()));
		
		return task.getName();
	}

	public boolean finishVerify() {
		bp.finishVerify(getSelectedTask(), getCsrName());
		
		reset();
		
		return true;
	}

	public boolean finishCustomerNumber() {
		bp.finishCustomerNumber(getSelectedCustomer(), getSelectedTask(), getCsrName());
		
		reset();
		
		return true;
	}
	
	public void reset(){
		getTasks().remove(getSelectedTask());
		
		setSelectedCustomer(new Customer());
		
		setSelectedAddress(new Address());
	}

	/**
	 * @return the csrName
	 */
	public String getCsrName() {
		return csrName;
	}

	/**
	 * @param csrName
	 *            the csrName to set
	 */
	public void setCsrName(String csrName) {
		this.csrName = csrName;
	}

	/**
	 * @return the selectedCustomer
	 */
	public Customer getSelectedCustomer() {
		return selectedCustomer;
	}

	/**
	 * @param selectedCustomer
	 *            the selectedCustomer to set
	 */
	public void setSelectedCustomer(Customer selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}

	/**
	 * @return the selectedAddress
	 */
	public Address getSelectedAddress() {
		return selectedAddress;
	}

	/**
	 * @param selectedAddress
	 *            the selectedAddress to set
	 */
	public void setSelectedAddress(Address selectedAddress) {
		this.selectedAddress = selectedAddress;
	}

	/**
	 * @return the tasks
	 */
	public List<TaskSummary> getTasks() {
		return tasks;
	}

	/**
	 * @param tasks
	 *            the tasks to set
	 */
	public void setTasks(List<TaskSummary> tasks) {
		this.tasks = tasks;
	}

	/**
	 * @return the selectedTask
	 */
	public TaskSummary getSelectedTask() {
		return selectedTask;
	}

	/**
	 * @param selectedTask the selectedTask to set
	 */
	public void setSelectedTask(TaskSummary selectedTask) {
		this.selectedTask = selectedTask;
	}

}
