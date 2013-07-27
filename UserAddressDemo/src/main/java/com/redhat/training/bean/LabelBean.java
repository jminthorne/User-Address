/**
 * 
 */
package com.redhat.training.bean;

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.training.constants.LabelName;

/**
 * @author Josh
 *
 */
@Stateless
public class LabelBean {
	private static final Logger LOG = LoggerFactory.getLogger(CustomerInformationBean.class);
	
	private String firstName = LabelName.LABEL_FIRST_NAME.getValue();
	private String lastName = LabelName.LABEL_LAST_NAME.getValue();
	private String nickName = LabelName.LABEL_NICK_NAME.getValue();
	private String addressLine1 = LabelName.LABEL_ADDRESS_LINE_1.getValue();
	private String addressLine2 = LabelName.LABEL_ADDRESS_LINE_2.getValue();
	private String city = LabelName.LABEL_CITY.getValue();
	private String state = LabelName.LABEL_STATE.getValue();
	private String zipCode = LabelName.LABEL_ZIP_CODE.getValue();
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
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
	 * @param lastName the lastName to set
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
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	
	
	

}
