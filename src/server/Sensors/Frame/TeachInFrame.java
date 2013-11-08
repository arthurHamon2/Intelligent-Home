package server.Sensors.Frame;

import server.database.service.SensorService;
import server.database.service.SensorTypeService;
import server.models.UnknowSensor;
import server.models.Sensor;

public class TeachInFrame extends OceanAbstractFrame {
	protected static final long SENSOR_FUNC_MASK = 0xFC000000L;
	protected static final long SENSOR_TYPE_MASK = 0x03F80000L;

	private SensorEEP mSensorEEP;

	public TeachInFrame(String trame) throws Exception {
		super(trame);
		mSensorEEP = getTypeCapteur(mFrame);
	}

	public String toString() {
		String str = super.toString();
		str += "\n-----------------------------\n";
		str += "I'm a Teach-In Frame !! \n";
	

		try {
			str += "EEP: "+getTypeCapteur(mFrame).toString()+"\n";
			if (getTypeCapteur(mFrame).toString().contains("7-2"))
					str += "I was send by a temperature sensor \n";
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}


		str += "Device Id is  " + Long.toString(getSensorId()) + "\n";
		str += "-----------------------------\n";

		return str;

	}

	private static SensorEEP getTypeCapteur(byte[] bytes) throws Exception {
		long data = getData(bytes);
		int type = (int)((data & SENSOR_TYPE_MASK) >> 19);
		int func = (int) ((data & SENSOR_FUNC_MASK) >> 26);
		int ORG = readUnsigendByte(bytes, enumFrameStructure.ORG);
		
		SensorEEP s = new SensorEEP(ORG,func,type);


		return s;
	}

	public static SensorEEP getTypeCapteur(String Frame) throws Exception {
		byte b[] = convertToBytesArray(Frame);
		return getTypeCapteur(b);

	}

	public SensorEEP getTypeCapteur() {
		return mSensorEEP;

	}
	
	public Sensor createSensor()
	{
		Sensor s =null;
		
		String EED = getTypeCapteur().toString();
		SensorTypeService sts = new SensorTypeService();
		s = sts.makeSensor(EED);
		
		if (s==null)
		{
			System.out.println("Materiel Non Implementé !! : makeSensor has failed !");
		}
		s.setSensorRef(getSensorId());

		return s;
	}

	@Override
	public Sensor populate(Sensor s){
		SensorService ser = new SensorService();
		s = ser.find(getSensorId());
		if (s==null || (s instanceof UnknowSensor)) // If teach in and new sensor (not present in database
    	{
			s = createSensor();
    	}
		// else : We do nothing
    	
    	return s;
		
	}
	
	

}
