package com.stokesdrift.accelerometer.service;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.stokesdrift.accelerometer.model.DataObject;

/**
 * Parse the data object from the message. 
 * Route the object info to the right handler based on CEP analysis?
 * 
 * @author driedtoast
 *
 */
public class MessageHandler {

	
	private ServiceLoader<MessageHandlerPlugin> pluginLoader;
	private ExecutorService executorService;
	
	public MessageHandler() {
		pluginLoader = ServiceLoader.load(MessageHandlerPlugin.class);
		// TODO add configuration for thread pool;		
		executorService = Executors.newCachedThreadPool();
	}
	
	/**
	 * Process message by parsing it and notifying plugins on receive and processing of the event
	 * 
	 * @param msg
	 * @return true on success
	 */
	public boolean processMessage(String msg) {
		boolean cond = false;
		
		DataObject dataObject = parseMessage(msg);
		notifyPluginReceive(dataObject);
	
		// this is based off plugins on receive
		// if the data object is deemed irrelevant, don't store or process
		if (dataObject.isIrrelevant()) {
		  return false;
		}
		
		// save data object to the db
		// process data object
		
		// notify of completion
		notifyPluginComplete(dataObject);
		
		return cond;				
	}
	
	/**
	 * Notify plugins of the receive of an event
	 * 
	 * @param object
	 */
	public void notifyPluginReceive(DataObject object) {
		Iterator<MessageHandlerPlugin> plugins = pluginLoader.iterator();
		while(plugins.hasNext()) {
			MessageHandlerPlugin plugin = plugins.next();
			if (isSupportedByPlugin(object, plugin)) {
				plugin.onReceive(object);
			}
		}		
	}
	
	/**
	 * Notify plugins of the complete processing of an event
	 * 
	 * @param object
	 */
	public void notifyPluginComplete(DataObject object) {
		Iterator<MessageHandlerPlugin> plugins = pluginLoader.iterator();
		while(plugins.hasNext()) {
			MessageHandlerPlugin plugin = plugins.next();
			if (!isSupportedByPlugin(object, plugin)) {
				continue;
			}
			if (plugin.hasExternalResource() ) {
				// add to worker threads
				executorService.execute(new Runnable() {					
					@Override
					public void run() {
						plugin.onComplete(object);
					}
				});
			} else {
			   plugin.onComplete(object);
			}
			
		}		
	}

	
	public boolean isSupportedByPlugin(DataObject obj, MessageHandlerPlugin plugin) {
		if(plugin.filterQualifiers() != null) {
			// TODO filter event?
		}
		
		return true;
	}
	
	
	/**
	 * Entry point for parsing of a message into the data object format for easier manipulation
	 * 
	 * @param msg
	 * @return DataObject
	 */
	public DataObject parseMessage(String msg) {
		DataObject dataObj = null;
		if (msg.startsWith("{")) {
			dataObj = parseJsonMessage(msg);
		} else {
			dataObj = parseShorthandMessage(msg);
		}		
		return dataObj;
	}
	
	/**
	 * Parse short hand message and return a data object representation 
	 *  
	 *  Format: "identifier:data_type:count[t=1000,s=pending,l=dbcall,ct=create]"
 	 *  
 	 *  Example: "customerxyz:account:1[t=1000,s=complete,l=admin,ct=create]"
	 * 
	 * @param msg
	 * @return DataObject
	 */
	protected DataObject parseShorthandMessage(String msg) {
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
	
	/**
	 * Parse long format/json message and return a data object representation 
	 * 
	 * @param msg
	 * @return DataObject
	 */
	protected DataObject parseJsonMessage(String msg) {
		DataObject dataObj = null;
		// TODO parse long format
		return dataObj;
	}
	
}
