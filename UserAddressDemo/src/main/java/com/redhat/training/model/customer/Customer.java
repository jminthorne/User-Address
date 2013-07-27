package com.redhat.training.model.customer;

/**
 * 
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

import com.redhat.training.model.address.Address;

/**
 * @author Josh
 *
 */
/**
 * <p>
 * Java class for customer complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="customer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;element name="customerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nickName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */

@Entity
@Table(name = "CUSTOMER")
@NamedQueries({
		@NamedQuery(name = "findAllCustomers", query = "FROM Customer u"),
		@NamedQuery(name = "findCustomerById", query = "FROM Customer u WHERE u.customerId = :id"),
		@NamedQuery(name = "findCustomerByCustomerNumber", query = "FROM Customer u WHERE u.customerNumber = :customerNumber") })
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customer", propOrder = { "customerId", "firstName", "lastName", "nickName", "customerNumber",
		"customerAddresses" })
public class Customer implements Serializable {

	private static final long serialVersionUID = -459775162851294453L;

	@Id
	@Column(name = "CUSTOMER_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement(required = true)
	private int customerId;

	@Column(name = "FIRST_NAME", nullable = false, length = 20)
	@XmlElement(required = true)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false, length = 25)
	@XmlElement(required = true)
	private String lastName;

	@Column(name = "NICK_NAME", nullable = true, length = 25)
	@XmlElement(required = true)
	private String nickName;

	@Column(name = "CUST_NUM", nullable = true, length = 10)
	@XmlElement(required = true)
	private String customerNumber;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = Address.class, cascade = CascadeType.ALL)
	@XmlElementWrapper(name = "addresses")
	@XmlElement(name = "addressInformation")
	private List<Address> customerAddresses = new ArrayList<Address>();

	public Customer() {
		super();
	}

	public Customer(String f, String l, String n, String cn) {
		this.setFirstName(f);
		this.setLastName(l);
		this.setNickName(n);
		this.setCustomerNumber(cn);
	}

	/*
	 * This is a helper method that is used so that other classes don'thave to
	 * to call each name method
	 */

	public void setCustomerFullNameHelper(String first, String last, String nickname) {
		if (!StringUtils.isEmpty(first)) {
			setFirstName(first);
		}// first name if

		if (!StringUtils.isEmpty(last)) {
			setLastName(last);
		}// last name if

		if (!StringUtils.isEmpty(nickname)) {
			setNickName(nickname);
		}
	}

	/*
	 * This is a helper method that allows a single address to be added to the
	 * list so that other classses don't have to do it
	 */
	public void addAddress(Address address) {
		if (null != address) {
			getCustomerAddresses().add(address);

		}// if
	}

	/**
	 * @return the customerId
	 */

	public int getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the firstName
	 */

	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */

	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the nickName
	 */

	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the customerNumber
	 */
	public String getCustomerNumber() {
		return customerNumber;
	}

	/**
	 * @param customerNumber
	 *            the customerNumber to set
	 */
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @param customerAddresses
	 *            the customerAddresses to set
	 */
	public void setCustomerAddresses(List<Address> customerAddresses) {
		this.customerAddresses = customerAddresses;
	}

	/**
	 * @return the customerAddresses
	 */
	public List<Address> getCustomerAddresses() {
		return customerAddresses;
	}
}
