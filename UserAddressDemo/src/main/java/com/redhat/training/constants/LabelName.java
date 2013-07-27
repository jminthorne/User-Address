/**
 * 
 */
package com.redhat.training.constants;

/**
 * @author Josh
 *
 */
public enum LabelName {
	
	LABEL_FIRST_NAME("First Name: "), 
	LABEL_LAST_NAME("LastName: "), 
	LABEL_NICK_NAME("NickName: "), 
	LABEL_ADDRESS_LINE_1("AddressLine1: "),
	LABEL_ADDRESS_LINE_2("AddressLine2: "),
	LABEL_CITY("City: "),
	LABEL_STATE("State: "),
	LABEL_ZIP_CODE("Zip Code: ");
	
	private String value;
	
	private LabelName(String v){
		value = v;
	}
	
	public String getValue(){
		return value;
	}
}	


