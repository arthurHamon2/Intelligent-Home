package server.models;

import java.util.ArrayList;
import java.util.Date;

import server.database.service.MeasureTypeService;

import com.google.gdata.data.DateTime;

public class VirtualCalendarSensor extends Sensor {

	protected final DateTime wakeUpHour = null;
	protected final int wakeUpHour_NUM = 0;
	protected final int currentTime_NUM = 1;

	public VirtualCalendarSensor(Long ref, String title) {
		super(ref, title, new ArrayList<Measure>(), new Date());
		MeasureTypeService s = new MeasureTypeService();
		MeasureType t = s.findByTitle("Heure_Reveil");
		getMeasures().add(new Measure(0L, getSensorRef(), t, 0));
		MeasureType w = s.findByTitle("Heure_Actuelle");
		getMeasures().add(new Measure(0L, getSensorRef(), w, 0));

	}

	public VirtualCalendarSensor() {
		super();
	}

	public Measure getWakeUpHour() {
		return getMeasures().get(wakeUpHour_NUM);

	}

	public Measure getCurrentTime() {
		return getMeasures().get(currentTime_NUM);

	}

	public void setWakeUpHour(long w) {
		getMeasures().get(wakeUpHour_NUM).setValue(w);

	}

	public void setCurrentTime(long h) {
		getMeasures().get(currentTime_NUM).setValue(h);

	}

}
