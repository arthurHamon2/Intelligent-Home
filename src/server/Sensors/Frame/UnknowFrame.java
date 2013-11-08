package server.Sensors.Frame;

import server.database.service.SensorService;
import server.models.Sensor;
import server.models.UnknowSensor;

public class UnknowFrame extends OceanAbstractFrame {

	public UnknowFrame(String trame) throws Exception {
		super(trame);
	}

	@Override
	public Sensor populate(Sensor s){
		SensorService ser = new SensorService();
		s = ser.find(getSensorId()); // A Null Sensor is return here
		if (s==null) // If teach in and new sensor (not present in database
    	{
			s = new UnknowSensor(getSensorId(),"Unknow");
    	}
		// else : We do nothing
    	return s;
	}
	
	@Override
	public String toString() {
	String s = new String("Unknow Frame !!"+"\n");
	s+="Id du capteur est: \n";
	s+=getSensorId();
	return s;
	}

}
