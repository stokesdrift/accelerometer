package org.stokesdrift.accelerometer.model;

public class ServiceEvent {
	
	private EventType type;

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}
	
	public ServiceEvent() {	
	}
	
	public ServiceEvent(EventType type) {
		this.type = type;		
	}
	
}
