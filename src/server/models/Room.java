package server.models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Room extends AbstractModel {

	private Long id;
	private House house;
	private String title;
	private List<Point> coordinates;

	public Room(Long id, House houseId, String title, Integer xLeft,
			Integer YLeft, Integer XRight, Integer YRight) {
		super();
		this.id = id;
		this.house = houseId;
		this.title = title;
		this.coordinates = new ArrayList<Point>();
		this.coordinates.add(new Point(xLeft, YLeft));
		this.coordinates.add(new Point(XRight, YRight));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House houseId) {
		this.house = houseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCoordinates(List<Point> coordinates) {
		this.coordinates = coordinates;
	}

	public List<Point> getCoordinates() {
		return coordinates;
	}

	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Room
				&& ((Room) o).getId().longValue() == this.id.longValue()
				&& ((Room) o).getTitle().equals(this.getTitle())
				&& ((Room) o).getHouse().equals(this.getHouse());
	}

	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", this.getId());
			jsonObject.put("title", this.getTitle());
			jsonObject.put("xLeft", this.getCoordinates().get(0).x);
			jsonObject.put("yLeft", this.getCoordinates().get(0).y);
			jsonObject.put("xRight", this.getCoordinates().get(1).x);
			jsonObject.put("yRight", this.getCoordinates().get(1).y);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;

	}
}
