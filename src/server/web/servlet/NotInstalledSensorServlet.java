package server.web.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.database.service.RoomService;
import server.database.service.RuleService;
import server.database.service.SensorService;
import server.database.service.SensorTypeService;
import server.models.Measure;
import server.models.Rule;
import server.models.Sensor;
import server.models.SensorType;

public class NotInstalledSensorServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("application/json");

		String pathInfo = req.getPathInfo();

		SensorService sensorService = new SensorService();

		List<Sensor> sensors = sensorService.findAll();

		JSONArray responseArray = new JSONArray();

		for (Sensor sensor : sensors) {
			JSONObject sensorJson = sensor.toJson();
			responseArray.put(sensorJson);
		}

		resp.getWriter().println(responseArray.toString());
		resp.setStatus(HttpServletResponse.SC_OK);

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String pathInfo = req.getPathInfo();
		RuleService ruleService = new RuleService();

		String title = req.getParameter("title");
		Long idType = Long.parseLong(req.getParameter("idType"));
		Long idRoom = Long.parseLong(req.getParameter("idRoom"));
		Boolean installed = Boolean.parseBoolean(req.getParameter("installed"));

		if (pathInfo != null) {

			if (pathInfo.startsWith("/"))
				pathInfo = pathInfo.substring(1);

			Long sensorRef = Long.parseLong(pathInfo);

			
			SensorService sensorService = new SensorService();
			Sensor sensor = sensorService.find(sensorRef);
			
			sensor.setTitle(title);
			
			RoomService roomService = new RoomService();
			sensor.setRoom(roomService.find(idRoom));

			SensorTypeService sensorTypeService = new SensorTypeService();
			sensor.setSensorType(sensorTypeService.find(idType));
			
			//if(sensor.isInstalled() != installed){
				sensor.setInstalled(installed);
				sensorService.update(sensor);
			//}
			
			resp.setStatus(HttpServletResponse.SC_OK);

		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

	}

}