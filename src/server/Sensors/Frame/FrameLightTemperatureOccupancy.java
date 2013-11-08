package server.Sensors.Frame;

import server.models.OccupancySensor;
import server.models.OccupancySensor.enumPresence;
import server.models.Sensor;

public class FrameLightTemperatureOccupancy extends OceanAbstractFrame {
	
	protected int mVInf;
	protected int mVSup;
	protected int mIlluInf;
	protected int mIlluSup;
	protected int mTempInf;
	protected int mTempSup;
	
		
	

	public FrameLightTemperatureOccupancy(String trame) throws Exception {
		super(trame);
		mVInf = 0;
		mVSup = 5;
		mIlluInf = 0;
		mIlluSup = 510;
		mTempInf = 0;
		mTempSup = 51;


	}
	

	public String toString() {
		String str = super.toString();
		str += "-----------------------------\n";
		str += "I'm a Temperature Frame !\n";
		str += "Device Id is  " + Long.toString(getSensorId()) + "\n";
		str += "State : t° : ";
		str += Double.toString(getTemperature());
		str += "\n-----------------------------\n";
		str += "State : Voltage : ";
		str += Double.toString(getSupplyVoltage());
		str += "\n-----------------------------\n";
		str += "State : Illumination : ";
		str += Double.toString(getIlluminanion());
		str += "\n-----------------------------\n";
		str += "State : presence : ";
		str += getOccupancy();
		
		str += "\n-----------------------------\n";

		return str;

	}
	
	
	public double getIlluminanion()
	{

		int b = readUnsigendByte(mFrame, enumFrameStructure.DATA_BYTE2);
		return scale(mIlluInf, mIlluSup, b);
	}
	
	public double getSupplyVoltage()
	{
		int b = readUnsigendByte(mFrame, enumFrameStructure.DATA_BYTE1);
		return scale(mVInf, mVSup, b);
	}
	
	public enumPresence getOccupancy()
	{
		enumPresence p = enumPresence.NOBODY_IS_HERE;
		int b = readUnsigendByte(mFrame, enumFrameStructure.DATA_BYTE0)&2;
		
		if (b==1)
			p = enumPresence.SOMEONE_IS_HERE;
		
		
		return p;
	}
	
	public double getTemperature()
	{

		int b = readUnsigendByte(mFrame, enumFrameStructure.DATA_BYTE3);
		return scale(mTempInf, mTempSup, b);
	}
	
	protected double scale(int inf, int sup, int data)
	{
		double result=0;
		result = ((double)(data)/(double)255)*(double)(sup-inf);
		result = inf + result;
		
		return result;
	}


	@Override
	public Sensor populate(Sensor s) {
		OccupancySensor ts =(OccupancySensor) s;
    	ts.setPresence(getOccupancy());
    	ts.setTemperature(getTemperature());
    	ts.setVolatge(getSupplyVoltage());
    	ts.setIllumination(getIlluminanion());
    	return ts;
		
	}



}
