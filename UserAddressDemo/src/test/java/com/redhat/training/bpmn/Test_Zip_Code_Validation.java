package com.redhat.training.bpmn;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkItem;
import org.jbpm.test.JbpmJUnitTestCase;
import org.junit.Test;

import com.redhat.training.handler.SaveCustomerInformationWorkItemHandler;
import com.redhat.training.model.address.Address;
import com.redhat.training.model.customer.Customer;

public class Test_Zip_Code_Validation extends JbpmJUnitTestCase {

	//@Test
	public void testEnterCustomerInformation(){
        StatefulKnowledgeSession ksession = createKnowledgeSession("EnterCustomerInformation.bpmn"); //knowledge session
        TestWorkItemHandler testWorkItemHandler = new TestWorkItemHandler(); //work handler
        ProcessInstance processInstance = null;
        WorkItem workItem = null;
        Map<String, Object> params = new HashMap<String, Object>(); //variables map
        Customer customer = setupCustomerWithAddress();
        Address address = customer.getCustomerAddresses().get(0);
        
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", testWorkItemHandler); //register work handler
        
        processInstance = ksession.startProcess("zipCodeValidation", null);
        assertProcessInstanceActive(processInstance.getId(), ksession); //active process
        
        params.put("enteredFirstName", customer.getFirstName());
        params.put("enteredLastName", customer.getLastName());
        params.put("enteredNickName", customer.getNickName());
        params.put("enteredAddressLine1", address.getAddressLine1());
        params.put("enteredCity", address.getCity());
        params.put("enteredState", address.getState());
        params.put("enteredZipcode", address.getZip());
        
        workItem = testWorkItemHandler.getWorkItem();
        
        ksession.getWorkItemManager().completeWorkItem(workItem.getId(), params); //complete Customer Information Input
        
        assertNodeTriggered(processInstance.getId(), "Verify Customer Information"); //assert nodes were triggered	
        assertNotNull("The work item is null", workItem); //work item
        assertEquals("The first names do not match", getVariableValue("firstName", processInstance.getId(), ksession), customer.getFirstName()); //first name
        assertEquals("The last names do not match", getVariableValue("lastName", processInstance.getId(), ksession), customer.getLastName()); //last name
        assertEquals("The nicknames doe not match", getVariableValue("nickName", processInstance.getId(), ksession), customer.getNickName()); //nick name
        assertEquals("Address line 1 does not match", getVariableValue("addressLine1", processInstance.getId(), ksession), address.getAddressLine1()); //address line 1
        assertEquals("Address line 1 does not match", getVariableValue("addressLine2", processInstance.getId(), ksession), address.getAddressLine2()); //address line 2
        assertEquals("The city does not match", getVariableValue("city", processInstance.getId(), ksession),address.getCity()); //city
        assertEquals("The state does not match", getVariableValue("state", processInstance.getId(), ksession), address.getState()); //state
        assertEquals("The zipcodes do not match", getVariableValue("zipcode", processInstance.getId(), ksession), address.getZip()); //zip code
	}
	
	//@Test
    public void testNonCharlotteZipCodeConstraint() {
        StatefulKnowledgeSession ksession = createKnowledgeSession("EnterCustomerInformation.bpmn"); //knowledge session
        //KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession); //logger
        TestWorkItemHandler testWorkItemHandler = new TestWorkItemHandler(); //work handler
        Map<String, Object> params = new HashMap<String, Object>(); //variables map
        
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", testWorkItemHandler); //register work handler
                
        ProcessInstance processInstance = ksession.startProcess("zipCodeValidation", null);
        	
        assertProcessInstanceActive(processInstance.getId(), ksession); //active process
        
        params.put("enteredZipcode", setupCustomerWithAddress().getCustomerAddresses().get(0).getZip()); //zipcode parameter
        
        ksession.getWorkItemManager().completeWorkItem(testWorkItemHandler.getWorkItem().getId(), params); //complete Customer Information Input
        
        assertNodeTriggered(processInstance.getId(), "Verify Customer Information"); //assert nodes were triggered	
    }

	@Test
    public void testCharlotteZipCodeConstraint() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("EnterCustomerInformation.bpmn"); //knowledge session
        TestWorkItemHandler testWorkItemHandler = new TestWorkItemHandler(); //work handler
        TestWorkItemHandler handler_JWPSCI = new TestWorkItemHandler();
        ProcessInstance processInstance = null; //process instance
        Map<String, Object> params = new HashMap<String, Object>(); //variables map
        
        ksession.getWorkItemManager().registerWorkItemHandler("JAXWSProxy_SaveCustomerInformationService", handler_JWPSCI);
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", testWorkItemHandler); //register work handler
		
		processInstance = ksession.startProcess("zipCodeValidation", null);
        
        assertProcessInstanceActive(processInstance.getId(), ksession); //active process
        
        params.put("enteredZipcode", setupCustomerWithCharlotteAddress().getCustomerAddresses().get(0).getZip()); //zipcode parameter
        
        ksession.getWorkItemManager().completeWorkItem(testWorkItemHandler.getWorkItem().getId(), params); //complete Customer Information Input
        
        assertNodeTriggered(processInstance.getId(), "Enter Customer Number");	//assert nodes were triggered
    }
	
	//@Test
	public void testVerifyCustomerInformation(){
		//This will eventually need to test saving via a web service call
		StatefulKnowledgeSession ksession = createKnowledgeSession("EnterCustomerInformation.bpmn"); //knowledge session
       // KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession); //logger
        TestWorkItemHandler testWorkItemHandler = new TestWorkItemHandler(); //work handler
        ProcessInstance processInstance = null; //process instance
        Map<String, Object> params = new HashMap<String, Object>(); //variables map
        
        Customer customer = setupCustomerWithAddress();
        
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", testWorkItemHandler); //register work handler
        //this needs to be changed so that the customer variables are assigned to params and not an object
        params.put("customer", customer); //customer parameter
        
        processInstance = ksession.startProcess("zipCodeValidation", params);
        	
        assertProcessInstanceActive(processInstance.getId(), ksession); //active process
        
        ksession.getWorkItemManager().completeWorkItem(testWorkItemHandler.getWorkItem().getId(), null); //complete Customer Information Input
        ksession.getWorkItemManager().completeWorkItem(testWorkItemHandler.getWorkItem().getId(), null); //complete Verify Customer Information
        
        assertNodeTriggered(processInstance.getId(), "Verify Customer Information"); //assert nodes were triggered	
        assertProcessInstanceAborted(processInstance.getId(), ksession); //assert work item was aborted
       
       // logger.close();
		
	}
	
	//@Test
	public void testEnterCustomerNumberAndSave(){
		StatefulKnowledgeSession ksession = createKnowledgeSession("EnterCustomerInformation.bpmn"); //knowledge session
		TestWorkItemHandler handler_HT = new TestWorkItemHandler(); 
        TestWorkItemHandler handler_SCI = new TestWorkItemHandler();
        TestWorkItemHandler handler_JWPSCI = new TestWorkItemHandler();
        ProcessInstance processInstance = null; //process instance
        WorkItem workItem = null; //work item
        SaveCustomerInformationWorkItemHandler sciHandler = new SaveCustomerInformationWorkItemHandler();
        Map<String, Object> enteredParams = new HashMap<String, Object>();
        Map<String, Object> uuidParms = new HashMap<String, Object>(); //variables map
        Customer customer = setupCustomerWithCharlotteAddress();
        String uuid = UUID.randomUUID().toString().toUpperCase().substring(0, 7);
        
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", handler_HT); //register Human task handler
     	ksession.getWorkItemManager().registerWorkItemHandler("SaveCustomerInformation", handler_SCI); //register Save handler
     	//TODO: This needs to be removed as the only thing that needs to be validated is that the sub process was called
     	ksession.getWorkItemManager().registerWorkItemHandler("JAXWSProxy_SaveCustomerInformationService", handler_JWPSCI);
        
        processInstance = ksession.startProcess("zipCodeValidation", null);
        
        assertProcessInstanceActive(processInstance.getId(), ksession); //active process
        
        workItem = handler_HT.getWorkItem(); //work item
        
        assertNotNull("The work item is null", workItem);
        
        enteredParams.put("enteredFirstName", customer.getFirstName());
        enteredParams.put("enteredLastName", customer.getLastName());
        enteredParams.put("enteredNickName", customer.getNickName());
        enteredParams.put("enteredAddressLine1", customer.getCustomerAddresses().get(0).getAddressLine1());
        enteredParams.put("enteredCity", customer.getCustomerAddresses().get(0).getCity());
        enteredParams.put("enteredState", customer.getCustomerAddresses().get(0).getState());
        enteredParams.put("enteredZipcode", customer.getCustomerAddresses().get(0).getZip());
       
        ksession.getWorkItemManager().completeWorkItem(workItem.getId(), enteredParams); //complete Customer Information Input
		
        uuidParms.put("enteredCustomerNumber", uuid); //customer number
        
        ksession.getWorkItemManager().completeWorkItem(handler_HT.getWorkItem().getId(), uuidParms); //complete Enter Customer Number
        
        assertEquals("The customer numbers are not equal", uuid, getVariableValue("customerNumber", processInstance.getId(), ksession)); //change this
        assertNodeTriggered(processInstance.getId(), "Enter Customer Number");	//assert nodes were triggered
        
        /*Using abort instead of execute ensures the data won't 
         * be saved as this would also be testing the dao and 
         * a test for that already exists which is no bueno!
         */
        sciHandler.abortWorkItem(handler_SCI.getWorkItem(), ksession.getWorkItemManager()); //execute save
	}
	
	
	/**
	 * This method is used to setup a customer with an 
	 * Charlotte address. 
	 * 
	 * @return Customer customer
	 */
	private Customer setupCustomerWithCharlotteAddress(){
		Customer customer = new Customer();
		Address address = new Address();
		
		address.setAddressLine1("1001 Sandy Hole  St.");
		address.setCity("Tatooine");
		address.setState("NC");
		address.setZip("28203");
		
		customer.setCustomerFullNameHelper("Luke", "Skywalker", "The chosen one");
		customer.addAddress(address);
		
		return customer;
	}
	
	/**
	 * This method is used to setup a customer with a 
	 * non-Charlotte address. 
	 * 
	 * @return Customer customer
	 */
	private Customer setupCustomerWithAddress(){
		Customer customer = new Customer();
		Address address = new Address();

		address.setAddressLine1("1000 Millenium Falcon Ave");
		address.setCity("Corellia");
		address.setState("NC");
		address.setZip("28202");
		
		customer.setCustomerFullNameHelper("Han", "Solo", "Fuzzy Nerf Herder");
		customer.addAddress(address);
		
		return customer;
	}
	
}