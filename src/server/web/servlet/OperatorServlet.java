package server.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.database.service.ActionService;
import server.database.service.OperatorService;
import server.database.service.OperatorTypeService;
import server.database.service.RoomService;
import server.models.Action;
import server.models.Operator;
import server.models.OperatorType;

public class OperatorServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("application/json");


		OperatorService operatorService = new OperatorService();

		List<Operator> operators = operatorService.findAll();

		JSONArray responseArray = new JSONArray();
		for (Operator operator : operators) {
			JSONObject operatorJson = operator.toJson();
			try {
				operatorJson.put("actions", getOperatorActions(operator));
				operatorJson.put("history", getOperatorHistory(operator));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			responseArray.put(operatorJson);
		}

		resp.getWriter().println(responseArray.toString());

		resp.setStatus(HttpServletResponse.SC_OK);

	}

	// Create a new operator to the database
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		OperatorService operatorService = new OperatorService();
        RoomService roomService = new RoomService();
		OperatorTypeService opeTypeService = new OperatorTypeService();
		
        Long idType = Long.parseLong(req.getParameter("type[type]"));
		Long ref = Long.parseLong(req.getParameter("ref"));
		String title = req.getParameter("title");
		String description = req.getParameter("description");
        Long idRoom = Long.parseLong(req.getParameter("room[id]"));

		OperatorType type = opeTypeService.find(idType);
		Operator operator = null;
		try {
			operator = (Operator) Class.forName(type.getClassName())
			.getConstructor(Long.class, OperatorType.class ,String.class, String.class)
			.newInstance(ref, type, title, description);
            operator.setRoom(roomService.find(idRoom));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		operatorService.create(operator);
		resp.setStatus(HttpServletResponse.SC_OK);
		
	}
	
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            if (pathInfo.startsWith("/"))
                pathInfo = pathInfo.substring(1);

            Long actionId = Long.parseLong(req.getParameter("actionId"));

            OperatorService operatorService = new OperatorService();
            Long id = Long.parseLong(pathInfo);

            Operator targetOperator = operatorService.find(id);

            ActionService actionService = new ActionService();
            Action action = actionService.find(actionId);

            operatorService.createActionTodo(action, targetOperator);

            resp.setStatus(HttpServletResponse.SC_OK);  

	    }
        else{
	        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    }
    }
	private JSONArray getOperatorActions(Operator operator) {

		OperatorService operatorService = new OperatorService();
		List<Action> listResult = operatorService.findActions(operator);

		JSONArray returnArray = new JSONArray();
		for (Action a : listResult) {

			returnArray.put(a.toJson());
		}

		return returnArray;

	}

	private JSONArray getOperatorHistory(Operator operator) {

		OperatorService operatorService = new OperatorService();
		List<Action> listResult = operatorService.historicOf(operator
				.getOperatorRef());

		JSONArray returnArray = new JSONArray();
		for (Action a : listResult) {

			returnArray.put(a.toJson());
		}

		return returnArray;

	}
}