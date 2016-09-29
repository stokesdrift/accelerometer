package com.stokesdrift.accelerometer.service;

import java.util.Map;

import com.stokesdrift.accelerometer.model.DataObject;

/** 
 * Used for extending functionality of the monitoring system
 * 
 * @author driedtoast
 *
 */
public interface MessageHandlerPlugin {

	/**
	 * Set this if the plugin is using external resource,
	 * this way the system can handle async on the complete processing method
	 * 
	 * @return
	 */
	boolean hasExternalResource();
	
	/**
	 * Process data object before getting saved or evaluated.
	 * 
	 * @param object
	 */
	void onReceive(DataObject object);
	
	/**
	 * Filter only object that match these qualifiers
	 * 
	 * @return
	 */
	Map<String,String> filterQualifiers();
	
	/**
	 * Process data object after it is saved and evaluated
	 * 
	 * @param object
	 */
	void onComplete(DataObject object);
}
