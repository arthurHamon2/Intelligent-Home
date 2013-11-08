package server.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.database.service.HouseService;
import server.database.service.RoomService;
import server.database.service.SensorService;
import server.models.House;
import server.models.Room;
import server.models.Sensor;

public class HouseServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("application/json");

		String pathInfo = req.getPathInfo();

		HouseService houseService = new HouseService();

		List<House> houses = houseService.findAll();

		JSONObject responseObject = new JSONObject();
		for (House house : houses) {
			JSONObject houseJson = house.toJson();
			try {
				houseJson.put("rooms", getRooms(house));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				responseObject.put(house.getId().toString(), houseJson);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//
		resp.getWriter().println(responseObject.toString());

		resp.setStatus(HttpServletResponse.SC_OK);

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String pathInfo = req.getPathInfo();
		RoomService roomService = new RoomService();

		Long id = Long.parseLong(req.getParameter("houseId"));

		Map params = req.getParameterMap();
		Iterator i = params.keySet().iterator();

		while (i.hasNext()) {
			String key = (String) i.next();
			String value = ((String[]) params.get(key))[0];
			System.out.println(key);
		}

		HouseService houseService = new HouseService();

		List<Room> rooms = houseService.findRooms(id);

		for (Room room : rooms) {
			room.setTitle(req.getParameter("rooms[" + room.getId().toString()
					+ "]"));
			roomService.update(room);
		}

		// Room room = roomService.find(idRoom);
		// room.setTitle(title);
		//
		// roomService.update(room);
		//
		// resp.setStatus(HttpServletResponse.SC_OK);
		/*
		 * if (pathInfo != null) {
		 * 
		 * if (pathInfo.startsWith("/")) pathInfo = pathInfo.substring(1);
		 * 
		 * Long idHouse = Long.parseLong(pathInfo);
		 * 
		 * 
		 * SensorService sensorService = new SensorService(); Sensor sensor =
		 * sensorService.find(sensorRef);
		 * 
		 * sensor.setTitle(title); sensor.setInstalled(installed); RoomService
		 * roomService = new RoomService();
		 * sensor.setRoom(roomService.find(idRoom));
		 * 
		 * SensorTypeService sensorTypeService = new SensorTypeService();
		 * sensor.setSensorType(sensorTypeService.find(idType));
		 * 
		 * resp.setStatus(HttpServletResponse.SC_OK);
		 * 
		 * sensorService.update(sensor);
		 * 
		 * } else { resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); }
		 */
	}

	private JSONArray getRooms(House house) {

		HouseService houseService = new HouseService();
		List<Room> listResult = houseService.findRooms(house.getId());

		JSONArray returnArray = new JSONArray();
		for (Room r : listResult) {
			JSONObject roomJson = r.toJson();
			try {
				roomJson.put("sensors", getSensors(r));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			returnArray.put(roomJson);
		}

		return returnArray;

	}

	private JSONArray getSensors(Room room) {

		SensorService sensorService = new SensorService();
		List<Sensor> listResult = new ArrayList<Sensor>();
		listResult = sensorService.findSensorInstalledByRoom(room
				.getId());

		JSONArray returnArray = new JSONArray();
		if(listResult!= null){
			for (Sensor s : listResult) {
	
				returnArray.put(s.toJson());
			}
		}

		return returnArray;

	}
}