/**
 * 
 */
package com.redhat.training.resources.ws;

import javax.inject.Inject;
import javax.jws.WebService;

import org.slf4j.Logger;

import com.redhat.training.business.CustomerAddressBP;
import com.redhat.training.dao.CustomerAddressDao;
import com.redhat.training.model.customer.Customer;

/**
 * @author Josh
 * 
 */
@WebService(endpointInterface = "com.redhat.training.resources.ws.SaveCustomerInformationService")
public class SaveCustomerInformationServiceImpl implements SaveCustomerInformationService {

	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(SaveCustomerInformationServiceImpl.class);

	@Inject
	private CustomerAddressBP bp;
	@Inject
	private CustomerAddressDao dao;

	public void saveCustomerInformation(Customer customer) throws Exception {
		bp.startCustomerInformationProcess(dao.save(customer));
	}

	@Override
	public void updateCustomerInformation(Customer customer) {
		bp.update(customer);

	}
}
