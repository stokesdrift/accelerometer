package com.stokesdrift.accelerometer.model;

/** 
 * Data representation of the core data
 * 
 * @author driedtoast
 *
 */
public class DataObject {

	private String dataType;
	private Integer count;
	private String customerId;
	
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}	

}
