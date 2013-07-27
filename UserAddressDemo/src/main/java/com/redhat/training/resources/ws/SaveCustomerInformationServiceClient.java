package com.redhat.training.resources.ws;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.training.model.customer.Customer;

public class SaveCustomerInformationServiceClient implements Serializable {

	private static final long serialVersionUID = -3479345847321928858L;
	
	private static final Logger LOG = LoggerFactory.getLogger(SaveCustomerInformationServiceClient.class);

	public void save(Customer customer) throws Exception {
		URL url = null;
		try {
			url = new URL(
					"http://localhost:8080/customer-information/SaveCustomerInformationServiceImpl?wsdl");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		QName qn = new QName("http://ws.resources.training.redhat.com/", "SaveCustomerInformationServiceImplService");
		
		Service service = Service.create(url, qn);
		
		SaveCustomerInformationService saveService = service.getPort(SaveCustomerInformationService.class);
		saveService.saveCustomerInformation(customer);
	}
	
	
	public void update(Customer customer){
		URL url = null;
		try {
			url = new URL(
					"http://localhost:8080/customer-information/SaveCustomerInformationServiceImpl?wsdl");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		QName qn = new QName("http://ws.resources.training.redhat.com/", "SaveCustomerInformationServiceImplService");
		
		Service service = Service.create(url, qn);
		
		SaveCustomerInformationService saveService = service.getPort(SaveCustomerInformationService.class);
		saveService.updateCustomerInformation(customer);
	}

}
