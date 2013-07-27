/**
 * 
 */
package com.redhat.training.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.redhat.training.model.customer.Customer;

/**
 * @author Josh
 * 
 */
@Stateless
public class CustomerAddressDao {

	@PersistenceContext(unitName = "userAddress")
	EntityManager entityManager;

	public Customer save(Customer customer) {
		System.out.println("save");
		entityManager.persist(customer);
		entityManager.flush();
		entityManager.refresh(customer);

		return customer;
	}// save

	public void updateCustomer(Customer customer) {
		entityManager.merge(customer);
	}

	public Customer findUserInformationById(int id) {
		return (Customer) entityManager.createNamedQuery("findCustomerById").setParameter("id", id).getSingleResult();
	}// findUserInformationById

	public Customer findUserInformationByCustomerNumber(String customerNumber) {
		System.out.println("find");
		return (Customer) entityManager.createNamedQuery("findCustomerByCustomerNumber")
				.setParameter("customerNumber", customerNumber).getSingleResult();
	}

	/**
	 * @param entityManager
	 *            the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
