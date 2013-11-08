package server.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.database.service.SensorTypeService;

abstract public class Sensor extends AbstractModel {
	private Long sensorRef;
	private SensorType sensorType;
	private Room room;
	private String title;
	private boolean installed;

	private List<Measure> measures;
	private Date modifiedat;

	public Sensor() {
		super();
		this.sensorRef = 0L;
		this.title = "";
		this.installed = false;
		this.measures = new ArrayList<Measure>();
		this.modifiedat = new Date();
		// Set du sensor type (Arthur)
		SensorTypeService serv = new SensorTypeService();
		this.setSensorType(serv.findByClass(this.getClass().getName()));
		}

	public Sensor(Long ref, String title, List<Measure> measures,
			Date modifiedat) {
		super();
		this.sensorRef = ref;
		this.title = title;
		this.modifiedat = modifiedat;
		this.measures = measures;
		// The sensor is not installed by default
		this.installed = false;
		this.room = new Room(1L, null, "empty house", 0, 0, 0, 0);
		// Set du sensor type (Arthur)
		SensorTypeService serv = new SensorTypeService();
		this.setSensorType(serv.findByClass(this.getClass().getName()));
	}

	public Long getSensorRef() {
		return sensorRef;
	}

	public void setSensorRef(Long sensorRef) {
		this.sensorRef = sensorRef;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getModifiedat() {
		return modifiedat;
	}

	public void setModifiedat(Date modifiedat) {
		this.modifiedat = modifiedat;
	}

	public void setModifiedat(String modifiedat) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			this.modifiedat = formatter.parse(modifiedat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public List<Measure> getMeasures() {
		return measures;
	}

	protected void setMeasures(List<Measure> measures) {
		this.measures = measures;
	}

	public boolean isInstalled() {
		return installed;
	}

	public void setInstalled(boolean installed) {
		this.installed = installed;
	}

	public SensorType getSensorType() {
		return sensorType;
	}

	public void setSensorType(SensorType sensorType) {
		this.sensorType = sensorType;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public boolean equals(Object o) {
		return o != null
				&& o instanceof Sensor
				&& o.getClass() == this.getClass()
				&& ((Sensor) o).getSensorRef().longValue() == this
						.getSensorRef().longValue()
				&& ((Sensor) o).getTitle().equals(this.getTitle())
				&& ((Sensor) o).getMeasures().size() == this.getMeasures()
						.size();
	}

	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("ref", this.sensorRef);
			jsonObject.put("title", this.getTitle());
			jsonObject.put("type", this.getSensorType().toJson());
			jsonObject.put("date", this.getModifiedat().getTime());
			jsonObject.put("installed", this.isInstalled());
			jsonObject.put("room", this.getRoom().toJson());

			JSONArray measuresJson = new JSONArray();
			for (Measure measure : this.getMeasures()) {
				measuresJson.put(measure.toJson());
			}

			jsonObject.put("measures", measuresJson);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;

	}

	@Override
	public String toString() {
		String re = new String();
		re += "************************\n";
		re += "Ref= " + this.sensorRef + "\n Title = " + this.title
				+ "\nMesures : \n";
		for (Measure m : measures) {
			re += m.toString();
			re += "\n";

		}
		re += "************************\n";
		return re;
	}
}
