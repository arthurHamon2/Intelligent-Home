package server.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import server.database.service.MeasureTypeService;
import server.models.Rocker2Sensor.enumButtonState;

public class VirtualMeteoSensor extends Sensor{
	
	protected final int TEMPERATURE = 0;
	protected final int PRESSION = 1;
	protected final int VENT = 2;

	public VirtualMeteoSensor(Long ref, String title) {
		super(ref, title, new ArrayList<Measure>() ,new Date());
		MeasureTypeService s = new MeasureTypeService();
		MeasureType t = s.findByTitle("Temperature");
		getMeasures().add(new Measure(0L, getSensorRef(), t, 0));
		MeasureType w = s.findByTitle("Pression");
		getMeasures().add(new Measure(0L, getSensorRef(), w, 0));
		MeasureType z = s.findByTitle("Vent");
		getMeasures().add(new Measure(0L, getSensorRef(), z, 0));

}

	public VirtualMeteoSensor() {
		super();
	}


	
	public void setTemperature(int t)
	{
		
		List<Measure> L = getMeasures();
		L.get(TEMPERATURE).setValue(t);
		
	}
	
	public int getTemperature()
	{
		List<Measure> L = getMeasures();
		return (int) L.get(TEMPERATURE).getValue();
		
	}
	
	public int getPression()
	{
		List<Measure> L = getMeasures();
		return (int) L.get(PRESSION).getValue();
		
	}

	public void setPression(int mPression) {
		
		List<Measure> L = getMeasures();
		L.get(PRESSION).setValue(mPression);

	}

	public void setVent(double mVent) {
		List<Measure> L = getMeasures();
		L.get(VENT).setValue(mVent);
		
	}
	
	public double getVent()
	{
		List<Measure> L = getMeasures();
		return L.get(VENT).getValue();
		
	}

}
