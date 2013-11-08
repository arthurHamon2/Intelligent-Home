package server.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import server.database.service.RuleService;
import server.models.Rule;

public class RuleServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("application/json");

		String pathInfo = req.getPathInfo();

		
		RuleService ruleService = new RuleService();
        List<Rule> rules = ruleService.findAll();


		JSONArray responseArray = new JSONArray();
        for (Rule rule : rules) {
            JSONObject ruleJson = rule.toJson();

            responseArray.put(ruleJson);
		}

		resp.getWriter().println(responseArray.toString());

		resp.setStatus(HttpServletResponse.SC_OK);

	}

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        RuleService ruleService = new RuleService();
        String title = req.getParameter("title");
        String contents = req.getParameter("contents");
        Boolean enable = Boolean.parseBoolean(req.getParameter("enable"));



        if (pathInfo != null) {

            if (pathInfo.startsWith("/"))
                pathInfo = pathInfo.substring(1);

            Integer id = Integer.parseInt(pathInfo);
            Rule rule = new Rule(id, null, title, contents, enable);

            ruleService.update(rule);

            resp.setStatus(HttpServletResponse.SC_OK);

        } else {
            Rule rule = new Rule(null, null, title, contents, enable);
            ruleService.create(rule);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer id = Integer.parseInt(req.getPathInfo().substring(1));
        RuleService ruleService = new RuleService();
        Rule rule = ruleService.find(id);
        ruleService.delete(rule);
        resp.setStatus(HttpServletResponse.SC_OK);

    }
}