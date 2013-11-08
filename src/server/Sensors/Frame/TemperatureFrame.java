package server.Sensors.Frame;

import server.models.Sensor;
import server.models.TemperatureSensor;

abstract public class TemperatureFrame extends OceanAbstractFrame {
	
	protected int mInf;
	protected int mSup;


	public TemperatureFrame(String trame,int inf,int sup) throws Exception {
		super(trame);
		mInf = inf;
		mSup = sup;


	}

	public String toString() {
		String str = super.toString();
		str += "-----------------------------\n";
		str += "I'm a Temperature Frame !\n";
		str += "Device Id is  " + Long.toString(getSensorId()) + "\n";
		str += "State : t° : ";
		str += Double.toString(getTemperature());
		str += "\n-----------------------------\n";

		return str;

	}
	
	public double getTemperature()
	{
		int b = readUnsigendByte(mFrame, enumFrameStructure.DATA_BYTE1);
		
		System.out.println("\n");
		System.out.println(b);
		System.out.println("\n");
		return scale(mInf, mSup, b);
	}
	
	protected double scale(int inf, int sup, int data)
	{
		double result=0;
		result = ((double)(255-data)/(double)255)*(double)(sup-inf);
		System.out.println("\n result");
		System.out.println(result);
		System.out.println("\n");
		result = inf + result;
		
		return result;
	}


	@Override
	public Sensor populate(Sensor s) {
		TemperatureSensor ts =(TemperatureSensor) s;
		ts.setTemperature(getTemperature());
		
		return ts;
	}

}
