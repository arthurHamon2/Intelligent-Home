package server.database.service;

import server.Sensors.Frame.*;
import server.database.dao.TSensorTypeDao;
import server.models.SensorType;
import server.models.Sensor;

public class SensorTypeService extends AbstractService<SensorType, Long>
{
    TSensorTypeDao dao;

    public SensorTypeService(TSensorTypeDao dao)
    {
        super(dao);
        this.dao = dao;
    }
    
    public SensorTypeService()
    {
        super(new TSensorTypeDao());
        this.dao = (TSensorTypeDao) this.getDao();
    }
    
    public SensorType findByEED(String eed)
    {
        return this.dao.findByEED(eed);
    }
    
    public Sensor makeSensor(String eed)
    {
        SensorType t = this.findByEED(eed);

        if (t == null)
            return null;

        Sensor r;
        try
        {
        	
            r = (Sensor) Class.forName(t.getSensorClass())
                    .getConstructor(Long.class, String.class)
                    .newInstance(0L, t.getTitle());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            r = null;
        }
        
        return r;
    }
    
    public OceanAbstractFrame makeFrame(Long sensorRef, String frame) throws Exception
    {
    	SensorType t = dao.findBySensorRef(sensorRef);

        try
        {
            return (OceanAbstractFrame) Class.forName(t.getFrameClass()).getConstructor(String.class).newInstance(frame);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new UnknowFrame(frame);
        }
    }
    
    public SensorType findByClass(String className) {
    	return this.dao.findByClass(className);
    }

}
