package com.stokesdrift.accelerometer.model;

import java.util.HashMap;
import java.util.Map;

/** 
 * Data representation of the core data
 * 
 * @author driedtoast
 *
 */
public class DataObject {

	private String dataType;
	private Integer count = 1;
	private String identityId;
	private Map<String,String> qualifiers;
	private boolean irrelevant = false;
	
	public Map<String, String> getQualifiers() {
		return qualifiers;
	}
	
	public void addQualifier(String key, String value) {
		if(qualifiers == null) {
			qualifiers = new HashMap<String,String>();
		}
		qualifiers.put(key, value);
	}
	
	public void setQualifiers(Map<String, String> qualifiers) {
		this.qualifiers = qualifiers;
	}
	
	public String getDataType() {
		return dataType;
	}
	
	// Only set null or full string, shouldn't have any empty strings
	public void setDataType(String dataType) {
		if (dataType == null) {
			this.dataType = dataType;
		} 
		else if (dataType != null && !dataType.trim().isEmpty()) {
			this.dataType = dataType;
		}		
	}
	
	public void markIrrelevant() {
		irrelevant = true;
	}
	
	public boolean isIrrelevant() {
		return irrelevant;
	}
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getIdentityId() {
		return identityId;
	}
	public void setIdentityId(String id) {
		this.identityId = id;
	}	

}
