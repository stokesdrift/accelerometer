package com.stokesdrift.accelerometer.listeners;

import java.util.logging.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;

import com.stokesdrift.accelerometer.model.DataObject;
import com.stokesdrift.accelerometer.model.EventType;
import com.stokesdrift.accelerometer.model.MessageEvent;
import com.stokesdrift.accelerometer.model.ServiceEvent;

@Singleton
public class LoggerListener {

	final static Logger logger = Logger.getLogger("com.stokesdrift.accelerometer.server"); 
	
	public void onEvent(@Observes MessageEvent event) {
		DataObject object = event.getData();
		String logMessage = (event.getType() == EventType.START) ? "Receiving" : "Completing";
		logger.fine(logMessage + ": " + object.getIdentityId());
	}

	public void onServiceEvent(@Observes ServiceEvent event) {
		logger.fine("Service: " + event.getType().name());
	}
	
}
