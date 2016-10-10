package com.stokesdrift.accelerometer;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Assert;
import org.junit.Test;

import com.stokesdrift.accelerometer.service.DataObjectMessageProcessor;
import com.stokesdrift.accelerometer.service.UdpService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.Context;

public class ServiceTest {

	@Test
	public void testGeneralSetup() {
		 Weld weld = new Weld();
		 WeldContainer container = weld.initialize();
		 Context context = container.getBeanManager().getContext(ApplicationScoped.class);		 
		 Assert.assertTrue(context.isActive());
		 
		 UdpService service = container.instance().select(UdpService.class).get();
		 Assert.assertNotNull(service);
		 
		 DataObjectMessageProcessor processor = container.instance().select(DataObjectMessageProcessor.class).get();
		 Assert.assertNotNull(processor);
	}
	
}
