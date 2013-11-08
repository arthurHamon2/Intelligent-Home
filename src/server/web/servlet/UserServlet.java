package server.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.database.service.HouseService;
import server.database.service.UserService;
import server.models.House;
import server.models.Room;
import server.models.User;

public class UserServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json");

		String pathInfo = req.getPathInfo();

		UserService userService = new UserService();
		HouseService houseService = new HouseService();

		JSONArray responseArray = new JSONArray();
		JSONObject userJSON = new JSONObject();

		if (pathInfo != null) {

			if (pathInfo.startsWith("/"))
				pathInfo = pathInfo.substring(1);
			
			User user = userService.find(Integer.parseInt(pathInfo));
			userJSON = user.toJson();
			List<Room> listResult = houseService.findRooms(user.getHouse().getId());
			JSONArray room = new JSONArray();
			for (Room r : listResult) {
				room.put(r.toJson());
			}
			try {
				userJSON.put("rooms", room);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			responseArray.put(userJSON);
			resp.getWriter().println(responseArray.toString());
		} else {

			List<User> users = userService.findAll();

			for (User user : users) {
				userJSON = user.toJson();
				responseArray.put(userJSON);

			}

			resp.getWriter().println(responseArray.toString());

		}

		resp.setStatus(HttpServletResponse.SC_OK);

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String pathInfo = req.getPathInfo();
		UserService userService = new UserService();
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String mail = req.getParameter("mail");
		String houseId = req.getParameter("house[id]");
		String roomId = req.getParameter("");
		String roomTitle = req.getParameter("");
		String avatar = req.getParameter("avatar");

		if (login == null) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		if (pathInfo != null) {

			if (pathInfo.startsWith("/"))
				pathInfo = pathInfo.substring(1);

			Integer id = Integer.parseInt(pathInfo);
			HouseService houseService = new HouseService();

			User user = userService.find(id);

			if (login != null)
				user.setLogin(login);

			if (password != null)
				user.setPwd(password);

			if (mail != null)
				user.setMail(mail);

			if (avatar != null)
				user.setAvatar(avatar);

			if (houseId != null) {
				Long house = Long.parseLong(houseId);
				House h = houseService.find(house);
				user.setHouse(h);
			}

			userService.update(user);

			resp.setStatus(HttpServletResponse.SC_OK);

		} else {
			User user = new User(null, login, password, mail, avatar);
			// user.setHouse(house);

			userService.create(user);
		}

	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Long id = Long.parseLong(req.getPathInfo().substring(1));

		UserService userService = new UserService();

		User user = new User(id.intValue(), null, null, null, null);

		userService.delete(user);

		resp.setStatus(HttpServletResponse.SC_OK);

	}

}
