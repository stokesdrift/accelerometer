package com.stokesdrift.accelerometer.service;

import com.stokesdrift.accelerometer.model.DataObject;

/**
 * Parse the data object from the message. 
 * Route the object info to the right handler based on CEP analysis?
 * 
 * @author driedtoast
 *
 */
public class MessageHandler {

	// identifier:data_type:count[t=1000,s=pending,l=dbcall,ct=create]	
	public DataObject processMessage(String msg) {
		DataObject dataObj = null;
		if (msg.startsWith("{")) {
			dataObj = processJsonMessage(msg);
		} else {
			dataObj = processShorthandMessage(msg);
		}		
		return dataObj;
	}
	
	protected DataObject processShorthandMessage(String msg) {
		DataObject dataObj = new DataObject();		
		String[] sections = msg.split(":");
		
		if (sections.length >= 2) {
			dataObj.setIdentityId(sections[0]);
			dataObj.setDataType(sections[1]);			
		}
		
		if (sections.length == 3) {			
			String tailSection = sections[2];
			int idx = tailSection.indexOf('[');
			int lastIdx = tailSection.indexOf(']');
			String options = null;
			String countStr = null;
			if (idx > -1 && lastIdx > -1) {
				countStr = tailSection.substring(0, idx);
				options = tailSection.substring(idx+1, lastIdx);
				String[] nvs = options.split(",");
				for(String nv: nvs) {
					String[] kv = nv.split("=");
					dataObj.addQualifier(kv[0], kv[1]);
				}
				
			} else {
				countStr = tailSection;
			}
		
			dataObj.setCount(Integer.parseInt(countStr));
		}
		
		return dataObj;
	}
	
	protected DataObject processJsonMessage(String msg) {
		DataObject dataObj = null;
		// TODO parse long format
		return dataObj;
	}
	
}
