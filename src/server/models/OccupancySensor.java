package server.models;

import java.util.ArrayList;
import java.util.Date;

import server.database.service.MeasureTypeService;

public class OccupancySensor extends Sensor {
	protected final int PRESENCE_NUM = 0;
	protected final int TEMPERATURE_NUM = 1;
	protected final int ILLUMINATION_NUM = 2;
	protected final int VOLTAGE_NUM = 3;
	public enum enumPresence {
		SOMEONE_IS_HERE(1),
		NOBODY_IS_HERE(0),
		;
	 
		/** L'attribut qui contient la valeur associé à l'enum */
		private final int value;
	 
		/** Le constructeur qui associe une valeur à l'enum */
		private enumPresence(int value) {
			this.value = value;
		}
		
	    public static enumPresence byOrdinal(int ord) {
	        for (enumPresence m : enumPresence.values()) {
	            if (m.value == ord) {
	                return m;
	            }
	        }
	        return null;
	    }	
	}
	
	
	
	public OccupancySensor(Long ref, String title) {
		super(ref, title, new ArrayList<Measure>() ,new Date());
		MeasureTypeService s = new MeasureTypeService();
		MeasureType t = s.findByTitle("Presence");
		getMeasures().add(new Measure(0L, getSensorRef(), t, 0));
		t = s.findByTitle("Temperature");
		getMeasures().add(new Measure(0L, getSensorRef(), t, 0));
		t = s.findByTitle("Luminosite");
		getMeasures().add(new Measure(0L, getSensorRef(), t, 0));
		t = s.findByTitle("Voltage");
		getMeasures().add(new Measure(0L, getSensorRef(), t, 0));
		
	}
	
	public OccupancySensor() {
		super();
		
	}
	
	public enumPresence getPresence()
	{
		return enumPresence.byOrdinal((int) getMeasures().get(PRESENCE_NUM).getValue());

	}
	
	public Measure getTemperature()
	{
		return getMeasures().get(TEMPERATURE_NUM);
	}
	
	public Measure getVolatge()
	{
		return getMeasures().get(VOLTAGE_NUM);

	}
	
	public Measure getIllumination()
	{
		return getMeasures().get(ILLUMINATION_NUM);

	}
	
	public void setPresence(enumPresence p)
	{
		getMeasures().get(PRESENCE_NUM).setValue(p.value);

	}
	
	public void setTemperature(double t)
	{
		getMeasures().get(TEMPERATURE_NUM).setValue(t);
	}
	
	public void setVolatge(double v)
	{
		getMeasures().get(VOLTAGE_NUM).setValue(v);
	}
	
	public void setIllumination(double v)
	{
		getMeasures().get(ILLUMINATION_NUM).setValue(v);
	}
	

}
