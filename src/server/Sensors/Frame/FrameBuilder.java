package server.Sensors.Frame;

import server.models.Action;
import server.models.PlugOperator;

public class FrameBuilder {
	
	public static String buildFrame(PlugOperator Op,Action a) throws Exception
	{
		String Frame = new String();
		//Sync Bytes
		Frame += "A5";
		Frame += "5A";
		//
		Frame += "6B";
		//ORG
		Frame += "05";
		//Databyte
		Frame += a.getFrame();
		//IdByte
		String id = OceanAbstractFrame.toHexa(Op.getOperatorRef(), 8);
		Frame += id;
		
		//Status
		Frame += "30";
		
		Frame += "00";
		String chck = OceanAbstractFrame.generateChecksum(Frame);
		Frame = Frame.substring(0, Frame.length() - 2);
		Frame +=  chck;

		return Frame;
		
	}
	

}
