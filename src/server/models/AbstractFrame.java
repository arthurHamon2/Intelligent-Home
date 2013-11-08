package server.models;

abstract public class AbstractFrame {
	
	protected Long mIdSensor;
	
	abstract public Sensor populate(Sensor s);
	
	public AbstractFrame(Long idSensor)
	{
		mIdSensor = idSensor;
	}
	public 	Long getSensorId()
	{
		return mIdSensor;
	}
	

}
