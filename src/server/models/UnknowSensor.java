package server.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import server.database.service.HouseService;
import server.database.service.RoomService;

public class UnknowSensor extends Sensor {
	
	public UnknowSensor(Long ref,String str)
	{
		super(ref, "NULL", new ArrayList<Measure>(), new Date());	
		RoomService s = new RoomService();
		this.setRoom(s.findByTitle("empty room"));
	}
	
}
