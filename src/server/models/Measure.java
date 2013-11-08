package server.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Measure extends AbstractModel {

	private Long idMeasure;
	private MeasureType type;
	private Long sensorRef;
	private double value;
	private Date modifiedat;

	public Measure(Long idMeasure, Long sensorRef, MeasureType type,
			double value) {
		this.idMeasure = idMeasure;
		this.sensorRef = sensorRef;
		this.type = type;
		this.value = value;
	}

	public Long getIdMeasure() {
		return idMeasure;
	}

	public void setIdMeasure(Long idMeasure) {
		this.idMeasure = idMeasure;
	}

	public Long getSensorRef() {
		return sensorRef;
	}

	public void setSensorRef(Long sensorRef) {
		this.sensorRef = sensorRef;
	}

	public MeasureType getMeasureType() {
		return this.type;
	}

	public void setMeasureType(MeasureType type) {
		this.type = type;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Measure))
			return false;

		Measure m = (Measure) obj;
		return m.value == this.value && m.type == this.type
				&& m.sensorRef.longValue() == this.sensorRef.longValue()
				&& m.idMeasure.longValue() == this.idMeasure.longValue();
	}

	
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		try {
            jsonObject.put("id", this.getIdMeasure());
            jsonObject.put("value", this.value);
            jsonObject.put("unity", this.getMeasureType().getUnity());
            jsonObject.put("nom", this.getMeasureType().getTitle());
            jsonObject.put("modifiedAt", this.getModifiedat());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;

	}
	
	@Override
	public String toString()
	{
		return type.getTitle() + " : " + getValue() + type.getUnity();
		
	}
}
