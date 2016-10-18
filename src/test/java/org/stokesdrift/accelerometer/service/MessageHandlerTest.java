package org.stokesdrift.accelerometer.service;

import org.junit.Assert;
import org.junit.Test;
import org.stokesdrift.accelerometer.model.DataObject;
import org.stokesdrift.accelerometer.service.DataObjectMessageProcessor;

public class MessageHandlerTest {
	
	@Test
	public void testParseFullShorthandMessageHandler() {
		String msg = "identifier:data_type:1[t=1000,s=pending,l=dbcall,ct=create]";
		
		DataObjectMessageProcessor handler = new DataObjectMessageProcessor();
		DataObject object = handler.parseMessage(msg);
		Assert.assertNotNull(object);
		
		Assert.assertEquals("identifier",object.getIdentityId());
		Assert.assertEquals("data_type",object.getDataType());
		Assert.assertEquals("pending",object.getQualifiers().get("s"));
	}


	@Test
	public void testParseNoOptionsShorthandMessageHandler() {
		String msg = "identifier:data_type:1";
		
		DataObjectMessageProcessor handler = new DataObjectMessageProcessor();
		DataObject object = handler.parseMessage(msg);
		Assert.assertNotNull(object);
		
		Assert.assertEquals("identifier",object.getIdentityId());
		Assert.assertEquals("data_type",object.getDataType());
		Assert.assertEquals((int)1, (int)object.getCount());
	}

	@Test
	public void testParseNoCountShorthandMessageHandler() {
		String msg = "identifier:data_type:";
		
		DataObjectMessageProcessor handler = new DataObjectMessageProcessor();
		DataObject object = handler.parseMessage(msg);
		Assert.assertNotNull(object);
		
		Assert.assertEquals("identifier",object.getIdentityId());
		Assert.assertEquals("data_type",object.getDataType());
		Assert.assertEquals((int)1, (int)object.getCount());
	}
	
	@Test
	public void testParseNoDatatypeShorthandMessageHandler() {
		String msg = "identifier::1";
		
		DataObjectMessageProcessor handler = new DataObjectMessageProcessor();
		DataObject object = handler.parseMessage(msg);
		Assert.assertNotNull(object);
		
		Assert.assertEquals("identifier",object.getIdentityId());
		Assert.assertEquals(null,object.getDataType());
		Assert.assertEquals((int)1, (int)object.getCount());
	}
	
	@Test
	public void testParseFullJsondMessageHandler() {
		
		// Should base some of the format off the statsd format
		String msg = "{ \"customer_id\": \"unique identifier\"," +
				" \"data_type\": \"object type\","+
				" \"count\": 1," +
		" \"qualifiers\": { " +
		 " \"time_took\": 1000," +
		 " \"state\": \"pending\", "+
		  " \"label\": \"dbcall\", " +
		  " \"change_type\": \"create\" " +
		"}";
		
		DataObjectMessageProcessor handler = new DataObjectMessageProcessor();
		DataObject object = handler.parseMessage(msg);
		Assert.assertNotNull(object);		
		Assert.assertEquals("identifier",object.getIdentityId());
		Assert.assertEquals("data_type",object.getDataType());
		Assert.assertEquals("pending",object.getQualifiers().get("s"));
	}
	
}
