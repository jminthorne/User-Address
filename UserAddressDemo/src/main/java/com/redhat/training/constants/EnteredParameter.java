/**
 * 
 */
package com.redhat.training.constants;

/**
 * @author Josh
 *
 */
public enum EnteredParameter {
	
	E_FIRST_NAME("enteredFirstName"), 
	E_LAST_NAME("enteredLastName"), 
	E_NICK_NAME("enteredNickName"), 
	E_ADDRESS_LINE_1("enteredAddressLine1"),
	E_ADDRESS_LINE_2("enteredAddressLine2"),
	E_CITY("enteredCity"),
	E_STATE("enteredState"),
	E_ZIP_CODE("enteredZipcode"),
	E_CUSTOMER_NUMBER("enteredCustomerNumber");
	
	private String value;
	
	private EnteredParameter(String v){
		value = v;
	}
	
	public String getValue(){
		return value;
	}
	
}
