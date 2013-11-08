package server.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import server.database.service.OperatorTypeService;
import server.models.OperatorType;

public class OperatorTypeServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("application/json");

		String pathInfo = req.getPathInfo();

        OperatorTypeService operatorTypeService = new OperatorTypeService();

            List<OperatorType> operatorTypes = operatorTypeService.findAll();

			JSONArray responseArray = new JSONArray();
        for (OperatorType operatorType : operatorTypes) {
            responseArray.put(operatorType.toJson());
        }

			resp.getWriter().println(responseArray.toString());



		resp.setStatus(HttpServletResponse.SC_OK);

	}
}