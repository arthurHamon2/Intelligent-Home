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

import server.database.service.SensorService;
import server.models.Measure;
import server.models.Sensor;

public class SensorServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("application/json");

		String pathInfo = req.getPathInfo();

		SensorService sensorService = new SensorService();

		if (pathInfo != null) {

			if (pathInfo.startsWith("/"))
				pathInfo = pathInfo.substring(1);

			Sensor sensor = sensorService.find(Long.parseLong(pathInfo));
			resp.getWriter().println(sensor.toJson().toString());

		} else {

			// TODO: a changer par findByInstalled !!
			//List<Sensor> sensors = sensorService.findAll();
			List<Sensor> sensors = sensorService.findSensorInstalled();
			
			JSONArray responseArray = new JSONArray();

			for (Sensor sensor : sensors) {
				JSONObject sensorJson = sensor.toJson();
                JSONArray measureArray = new JSONArray();
                for (Measure measure : sensor.getMeasures()) {
				try {
                        JSONObject measureJSON = measure.toJson();

                        measureJSON.put("history", getSensorHistory(measure));
                        measureArray.put(measureJSON);
                        sensorJson.put("measures", measureArray);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                }
                responseArray.put(sensorJson);
			}

			resp.getWriter().println(responseArray.toString());

		}

		resp.setStatus(HttpServletResponse.SC_OK);

	}

    private JSONArray getSensorHistory(Measure measure) {

		DateFormat dateFormat = SimpleDateFormat.getTimeInstance(
				SimpleDateFormat.DEFAULT, Locale.FRANCE);

		SensorService sensorService = new SensorService();
        List<Measure> listResult = sensorService.historicOf(measure.getIdMeasure());

		JSONArray returnArray = new JSONArray();
        for (Measure m : listResult) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("value", m.getValue());
                jsonObject.put("modifiedAt", m.getModifiedat().getTime());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            returnArray.put(jsonObject);

		}

		return returnArray;

	}
}