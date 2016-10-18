package org.stokesdrift.accelerometer;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.stokesdrift.accelerometer.service.UdpService;

public class Service {

	private final static Logger logger = Logger.getLogger(Service.class.getName()); 
	
	private WeldContainer container;
	private UdpService udpService;
	
	public Service() {
			
	}
	
	public void start() {
		Weld weld = new Weld();
		container = weld.initialize();
		udpService = container.select(UdpService.class).get();
		Thread t = new Thread(new Runnable() {
			public void run() {
				udpService.start();
			}
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, "server didn't start properly", e);
		}
				
	}
	
	
	
	public void stop() {
		udpService.stop();		
		container.shutdown();		
	}

	public static void main(String[] args) {
		final Service service = new Service();
		service.start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() { service.stop(); }
		});
	}
	
}


// DB support for:
// - leveldb?
// - postgres db
// - mysql db
// - hql for testing