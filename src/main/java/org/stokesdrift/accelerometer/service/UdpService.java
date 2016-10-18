package org.stokesdrift.accelerometer.service;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.stokesdrift.accelerometer.model.EventType;
import org.stokesdrift.accelerometer.model.ServiceEvent;
import org.stokesdrift.accelerometer.protocol.UdpServer;

public class UdpService {
	@Inject 
	private Event<ServiceEvent> serviceEvent;

	@Inject 
	private DataObjectMessageProcessor messageHandler;
	
	private UdpServer server;
	
	public void start() {
		notifyStart();
		
		// Run in a thread
		server = createUdpServer();		
		server.run();
		
	}
	
	public void stop() {
		notifyStop();
		if (server != null) {
			server.stop();
		}
 	}
	
	
	protected UdpServer createUdpServer() {
		UdpServer server = new UdpServer(messageHandler);
		return server;
	}
	
	

	/**
	 * Notify plugins of the receive of an event
	 * 
	 * @param object
	 */
	public void notifyStart() {
		serviceEvent.fire(new ServiceEvent(EventType.START));
	}
	
	/**
	 * Notify plugins of the complete processing of an event
	 * 
	 * @param object
	 */
	public void notifyStop() {
		serviceEvent.fire(new ServiceEvent(EventType.END));					
	}

	
}
