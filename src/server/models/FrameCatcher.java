package server.models;

import server.Sensors.Frame.OceanAbstractFrame;
import server.database.service.SensorService;;


public class FrameCatcher {
	
    private SensorService sensorService;
    public FrameCatcher()
    {
        this.sensorService = new SensorService();
    	
    }
    
    public void Persist(AbstractFrame f) throws Exception
    {
    	Sensor s =  sensorService.find(f.getSensorId());
    	s = f.populate(s);
    	System.out.println("Persistence Sensor Object : ");
    	System.out.println(s.toString());
    	sensorService.persist(s);
    	
    }
}