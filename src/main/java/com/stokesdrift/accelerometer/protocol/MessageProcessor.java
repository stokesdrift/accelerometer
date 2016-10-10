package com.stokesdrift.accelerometer.protocol;

/**
 * Used in the connection protocol to process messages coming from the connections.
 * 
 * @author driedtoast
 *
 */
public interface MessageProcessor {
	
	// TODO support the remote address
	public boolean processMessage(String msg);
	
}
