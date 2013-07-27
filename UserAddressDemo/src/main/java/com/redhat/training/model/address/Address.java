/**
 * 
 */
package com.redhat.training.model.address;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.redhat.training.model.customer.Customer;

/**
 * @author Josh
 *
 */

/**
 * <p>Java class for address complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="address">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addressId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="addressLine1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="addressLine2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customer" type="{http://impl.ws.resources.training.redhat.com/}customer" minOccurs="0"/>
 *         &lt;element name="zip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@Entity
@Table(name="ADDRESS")
@NamedQueries({
	@NamedQuery(name="findAllAddresses", query="SELECT a FROM Address a")
})

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Address", propOrder = {
	"addressLine1", 
    "addressLine2", 
    "city",
    "state", 
    "zip"
})
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7770665187609310660L;

	@Id
	@Column(name="ADDRESS_ID", nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	@XmlTransient
	private int addressId;
	
	@Column(name="ADDRESS_1", nullable = false, length = 50)
	@XmlElement(required = true)
	private String addressLine1;
	
	@Column(name="ADDRESS_2", nullable = true, length = 15)
	@XmlElement(required = true)
	private String addressLine2;
	
	@Column(name="CITY", nullable = false, length = 20)
	@XmlElement(required = true)
	private String city;
	
	@Column(name="STATE", nullable = false, length = 25)
	@XmlElement(required = true)
	private String state;
	
	@Column(name="ZIP", nullable = false, length = 5)
	@XmlElement(required = true)
	private String zip;
	
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	@XmlTransient
	private Customer customer;
	/**
	 * @return the addressId
	 */
	
	
	public Address(){
		super();
	}
	
	public Address (String al1, String al2, String c, String s, String z){
		this.setAddressLine1(al1);
		this.setAddressLine2(al2);
		this.setCity(c);
		this.setState(s);
		this.setZip(z);
	}
	
	
	
	public int getAddressId() {
		return addressId;
	}
	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	/**
	 * @return the addressLine1
	 */
	
	public String getAddressLine1() {
		return addressLine1;
	}
	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	/**
	 * @return the addressLine2
	 */
	
	public String getAddressLine2() {
		return addressLine2;
	}
	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	/**
	 * @return the city
	 */
	
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the zip
	 */
	
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return the customer
	 */
	
	public Customer getUser() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setUser(Customer customer) {
		this.customer = customer;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
