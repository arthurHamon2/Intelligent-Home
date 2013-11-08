package server.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import server.database.service.MeasureTypeService;
import server.database.service.SensorTypeService;

public class TemperatureSensor extends Sensor {

	protected final int TEMPERATURE_NUM = 0;

	public TemperatureSensor(Long ref, String title) {
		super(ref, title, new ArrayList<Measure>(), new Date());
		// getMeasures().add(0, new Measure(0, r, idMeasureType, value))
		
		
		MeasureTypeService s = new MeasureTypeService();
		MeasureType t = s.findByTitle("Temperature");
		getMeasures().add(new Measure(0L, getSensorRef(), t, 0));

	}

	public Measure getTemperature() {
		return getMeasures().get(TEMPERATURE_NUM);
	}

	public void setTemperature(double temperature) {
		List<Measure> L = getMeasures();
		L.get(TEMPERATURE_NUM).setValue(temperature);
		setMeasures(L);

	}

}
