package com.redhat.training.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.redhat.training.model.address.Address;
import com.redhat.training.model.customer.Customer;

public class Test_UserAddressDao extends TestCase {

	@PersistenceContext(unitName = "userAddressTest")
	EntityManager testEntityManager;
	
	EntityManagerFactory factory;

	private static final Logger LOG = Logger.getLogger(Test_UserAddressDao.class.getName());

	@Before
	public void setup() {
		System.out.println("\n\tem");
		if (LOG.isDebugEnabled()) {
			LOG.debug("getting ready to setup");
		}
		// System.out.println
		factory = Persistence.createEntityManagerFactory("userAddressTest");
		if (LOG.isDebugEnabled()) {

		}
		// System.out.println("Yeah, factory");
		testEntityManager = factory.createEntityManager();

	}

	// @Test
	@SuppressWarnings("unchecked")
	public void testUser() {
		Query userQuery = testEntityManager.createNamedQuery("findAllUsers");
		List<Customer> userList = userQuery.getResultList();
		List<Customer> list = testEntityManager.createQuery("FROM Customer").getResultList();
		Assert.assertEquals(userList.size(), list.size());

	}

	// @Test
	@SuppressWarnings("unchecked")
	public void testAddress() {
		Query addressQuery = testEntityManager.createNamedQuery("findAllAddresses");
		List<Address> addressList = addressQuery.getResultList();
		List<Address> list = testEntityManager.createQuery("FROM Address").getResultList();
		Assert.assertEquals(addressList.size(), list.size());
	}

	@Test
	public void testSave() {
		factory = Persistence.createEntityManagerFactory("userAddressTest");
		testEntityManager = factory.createEntityManager();

		Customer customer = setUpUserObject();
		CustomerAddressDao customerAddressDao = new CustomerAddressDao();

		int currentSize = testEntityManager.createQuery("FROM Customer").getResultList().size();

		customerAddressDao.setEntityManager(testEntityManager);

		testEntityManager.getTransaction().begin();

		customerAddressDao.save(customer);

		testEntityManager.getTransaction().commit();

		int newSize = testEntityManager.createQuery("FROM Customer").getResultList().size();
		testEntityManager.close();

		Assert.assertEquals(currentSize + 1, newSize);
	}

	// @Test
	public void testFindUserInformationById() {
		Customer user1 = new Customer();
		Customer user2 = new Customer();
		CustomerAddressDao customerAddressDao = new CustomerAddressDao();

		customerAddressDao.setEntityManager(testEntityManager);

		testEntityManager.getTransaction().begin();

		user1 = customerAddressDao.findUserInformationById(1);

		user2 = (Customer) testEntityManager.createQuery("FROM Customer u where u.userId = ?1").setParameter(1, 1)
				.getSingleResult();

		if (LOG.isDebugEnabled()) {

		}

		Assert.assertEquals(user1, user2);
	}

	// @Test
	public void testFindUserInformationByCustomerNumber() {
		String customerNumber = "DEFFCOORD";
		Customer user1 = new Customer();
		Customer user2 = new Customer();
		CustomerAddressDao customerAddressDao = new CustomerAddressDao();

		customerAddressDao.setEntityManager(testEntityManager);

		testEntityManager.getTransaction().begin();

		user1 = customerAddressDao.findUserInformationByCustomerNumber(customerNumber);

		user2 = (Customer) testEntityManager.createQuery("FROM Customer u where u.customerNumber = ?1")
				.setParameter(1, customerNumber).getSingleResult();

		if (LOG.isDebugEnabled()) {

		}

		Assert.assertEquals(user1, user2);
	}

	private Customer setUpUserObject() {
		Customer testUser = new Customer("Joshua", "Morgan", null, null);

		Address testAddress = new Address("2003 Main St", "Anytown", null, "NC", "28202");

		testUser.addAddress(testAddress);

		return testUser;
	}
}
