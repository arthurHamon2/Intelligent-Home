package server.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import server.database.service.SensorTypeService;
import server.models.SensorType;

public class SensorTypeServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("application/json");

		String pathInfo = req.getPathInfo();

        SensorTypeService sensorTypeService = new SensorTypeService();

		if (pathInfo != null) {

			if (pathInfo.startsWith("/"))
				pathInfo = pathInfo.substring(1);

            SensorType sensorType = sensorTypeService.find(Long.parseLong(pathInfo));
            resp.getWriter().println(sensorType.toJson().toString());

		} else {

            List<SensorType> sensorTypes = sensorTypeService.findAll();

			JSONArray responseArray = new JSONArray();
            for (SensorType sensorType : sensorTypes) {
                responseArray.put(sensorType.toJson());
			}

			resp.getWriter().println(responseArray.toString());

		}

		resp.setStatus(HttpServletResponse.SC_OK);

	}
}