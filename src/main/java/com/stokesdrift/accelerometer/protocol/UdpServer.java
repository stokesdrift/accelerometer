package com.stokesdrift.accelerometer.protocol;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handle udp connections and notifies processor
 * 
 * @author driedtoast
 */
public class UdpServer implements Runnable {
		
	private DatagramSocket serverSocket;
	private boolean runServer = true;
	// TODO configuration
	private ExecutorService executorService = Executors.newFixedThreadPool(10);
	private MessageProcessor processor;
	private final static Logger logger = Logger.getLogger(UdpServer.class.getName()); 
	
	public UdpServer(MessageProcessor processor) {
		this.processor = processor;
	}
	
	
	public void start() throws Exception {
		serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[1024];	    
	    while(runServer) {	    	
	    	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            final String msg = new String(receivePacket.getData());
            executorService.submit(new Runnable() {
				@Override
				public void run() {
					processor.processMessage(msg);
				}            	
            });
            
            // TODO support this
            // InetAddress IPAddress = receivePacket.getAddress();
	    }	    
	}
	
	
	public void stop() {
		runServer = false;
		executorService.shutdown();
		serverSocket.close();	
	}


	@Override
	public void run() {
		try {
			start();
		} catch(Throwable e) {
			logger.log(Level.SEVERE, "server didn't start properly", e);
		}
	}


}
