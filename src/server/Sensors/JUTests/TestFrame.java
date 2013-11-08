package server.Sensors.JUTests;



import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import server.Sensors.Frame.OceanAbstractFrame;
import server.Sensors.Frame.FrameBuilder;
import server.Sensors.Frame.FrameFactory;
import server.Sensors.Frame.TeachInFrame;
import server.Sensors.Frame.UnknowFrame;
import server.models.Action;
import server.models.Operator;
import server.models.PlugOperator;


public class TestFrame {
	
	
	
	@Before
	public void initBeforeTest() throws Exception
	{
		
		
		
	}
	
	
	@Test
	public void testIdDevice() throws Exception {
		
		long id = 0;

			id = OceanAbstractFrame.parseSensorId("A55A0B070084990FAE04E9570001");
		assertEquals(0xAE04E957L,id);
		


		
	}
	
	@Test
	public void testTeachIn() throws Exception {
	Boolean b = false;

		b = OceanAbstractFrame.isTeachInFrame("A55A0B07100802870004E9570088");

	assertEquals(true,b);
	

		b = OceanAbstractFrame.isTeachInFrame("A55A0B070084990F0004E9570001");
	assertEquals(false,b);
	
	}
	
	@Test
	public void testSensorType() throws Exception {
	Boolean b = false;

		b = OceanAbstractFrame.isTeachInFrame("A55A0B07100802870004E9570088");

	assertEquals(true,b);
	
	assertEquals("7-2-5",TeachInFrame.getTypeCapteur("A55A0B0708282C8000893382002C").toString());
	
	
	
	}
	
	@Test
	public void testChecksum() throws Exception {
		String s= new String();

		s = OceanAbstractFrame.generateChecksum("A55A0B06000000090001B25E002B");

	assertEquals("2B",s);
	
	s = OceanAbstractFrame.generateChecksum("A55A0B0708282C8000893382002C");
	assertEquals("2C",s);
	
	
	
	
	}
	
	@Test
	public void testObjectCreation() throws Exception {
		OceanAbstractFrame f = FrameFactory.getFrame("A55A0B06000000090001B000002B");
		assertTrue(f instanceof UnknowFrame);
	}
	
	
	@Test
	public void testOperator() throws Exception
	{

		//Bouton B : 011
		//Bouton Pressed :1
		Action a = new Action(1L,null, "50000000", "Allumer", "Action","");
		Action a2 = new Action(1L,null, "70000000", "Eteindre", "Action","");
		PlugOperator op = new PlugOperator(0xFF9F1E06L, null, new String(), new String());
		String s= FrameBuilder.buildFrame(op, a);
		String s2= FrameBuilder.buildFrame(op, a2);
		assertEquals("A55A6B0550000000FF9F1E0630B2",s);
		assertEquals("A55A6B0570000000FF9F1E0630D2",s2);
		
	}
	
	

	
	
}