package server.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import server.database.service.UserService;
import server.models.User;

public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();

        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {

			resp.sendError(javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
        UserService userService = new UserService();
        User user = userService.find(userId);
		

		resp.getWriter().println(user.toJson().toString());
		resp.setStatus(HttpServletResponse.SC_OK);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String login = req.getParameter("login");
		String submittedPassword = req.getParameter("password");

		if (login != null && submittedPassword != null) {
			UserService userService = new UserService();
			User u = userService.authentificate(login, submittedPassword);

				if (u!=null) {
					resp.setStatus(HttpServletResponse.SC_OK);

					HttpSession session = req.getSession();
					session.setAttribute("userId", u.getId());

					return;
				}
		}

		resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

	}
}
