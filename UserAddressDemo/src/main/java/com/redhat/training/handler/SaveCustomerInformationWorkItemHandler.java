/**
 * @author Josh
 * @date Jan 30, 2013
 * @version
 */
package com.redhat.training.handler;

import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;

import com.redhat.training.business.CustomerAddressBP;
import com.redhat.training.model.address.Address;
import com.redhat.training.model.customer.Customer;

public class SaveCustomerInformationWorkItemHandler implements WorkItemHandler {



	/* (non-Javadoc)
	 * @see org.drools.runtime.process.WorkItemHandler#executeWorkItem(org.drools.runtime.process.WorkItem, org.drools.runtime.process.WorkItemManager)
	 */
	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		CustomerAddressBP bp = new CustomerAddressBP();
		Customer customer = new Customer(
				(String) workItem.getParameter("save_FirstName"),
				(String) workItem.getParameter("save_LastName"), 
				(String) workItem.getParameter("save_NickName"),
				(String) workItem.getParameter("save_CustomerNumber"));
	
		Address address = new Address(
				(String) workItem.getParameter("save_AddressLine1"),
				(String) workItem.getParameter("save_AddressLine2"),
				(String) workItem.getParameter("save_City"),
				(String) workItem.getParameter("save_State"),
				(String) workItem.getParameter("save_Zipcode"));
	
		customer.addAddress(address);
		
		manager.completeWorkItem(workItem.getId(), null);
	}
	
	/* (non-Javadoc)
	 * @see org.drools.runtime.process.WorkItemHandler#abortWorkItem(org.drools.runtime.process.WorkItem, org.drools.runtime.process.WorkItemManager)
	 */
	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager){
		System.out.println("The process was aborted......It's a trap!!!!");
	}

}


