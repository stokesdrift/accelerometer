package org.stokesdrift.accelerometer.model;

public class MessageEvent {

	
	private EventType type;
	
	private DataObject data;

	public MessageEvent() {		
	}
	
	public MessageEvent(EventType type, DataObject data) {
		this.type = type;
		this.data = data;
	}
	
	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public DataObject getData() {
		return data;
	}

	public void setData(DataObject data) {
		this.data = data;
	}
	
	
	
}
