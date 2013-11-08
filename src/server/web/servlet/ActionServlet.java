package server.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import server.database.service.ActionService;
import server.database.service.OperatorService;
import server.database.service.OperatorTypeService;
import server.models.Action;
import server.models.Operator;
import server.models.OperatorType;

public class ActionServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("application/json");


		ActionService actionService = new ActionService();

		List<Action> actions = actionService.findAll();
		
		JSONArray arr = new JSONArray();
		for (Action action : actions) {
			arr.put(action.toJson());
		}
		

		resp.getWriter().println(arr.toString());

		resp.setStatus(HttpServletResponse.SC_OK);

	}

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        OperatorService operatorService = new OperatorService();
        OperatorTypeService opeTypeService = new OperatorTypeService();

        Long idType = Long.parseLong(req.getParameter("type[type]"));
        String title = req.getParameter("title");
        String frame = req.getParameter("frame");
        String description = req.getParameter("description");

        OperatorType type = opeTypeService.find(idType);
        Operator operator = null;

        ActionService actionService = new ActionService();

        Action action = new Action();
        action.setDescription(description);
        action.setFrame(frame);
        action.setTitle(title);
        action.setOperatorType(type);

        actionService.create(action);

        resp.setStatus(HttpServletResponse.SC_OK);

    }


}