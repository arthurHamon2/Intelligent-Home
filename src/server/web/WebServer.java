package server.web;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import server.web.servlet.ActionServlet;
import server.web.servlet.HouseServlet;
import server.web.servlet.LoginServlet;
import server.web.servlet.LogoutServlet;
import server.web.servlet.NotInstalledSensorServlet;
import server.web.servlet.OperatorServlet;
import server.web.servlet.OperatorTypeServlet;
import server.web.servlet.RuleServlet;
import server.web.servlet.SensorServlet;
import server.web.servlet.SensorTypeServlet;
import server.web.servlet.UserServlet;

public class WebServer extends Thread {

	public void run() {

		System.out.println("Starting WebsServer");

		Server server = new Server(8081);

		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setDirectoriesListed(true);
		resource_handler.setWelcomeFiles(new String[] { "index.html" });
		resource_handler.setResourceBase("webclient");

		ServletContextHandler context = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		FilterHolder loginFilterHolder = new FilterHolder(
				new LoginCheckFilter());

		context.addFilter(loginFilterHolder, "/user/*", 0);
		// context.addFilter(loginFilterHolder, "/sensor/*", 0);
		// context.addFilter(loginFilterHolder, "/operator/*", 0);

		context.addServlet(new ServletHolder(new NotInstalledSensorServlet()),
				"/allsensor/*");
		context.addServlet(new ServletHolder(new HouseServlet()), "/house/*");
		context.addServlet(new ServletHolder(new RuleServlet()), "/rule/*");
		context.addServlet(new ServletHolder(new OperatorServlet()),
				"/operator/*");
        context.addServlet(new ServletHolder(new OperatorTypeServlet()), "/operatorType");
		context.addServlet(new ServletHolder(new SensorServlet()), "/sensor/*");
		context.addServlet(new ServletHolder(new SensorTypeServlet()),
				"/sensorType");
		context.addServlet(new ServletHolder(new ActionServlet()), "/action/*");
		context.addServlet(new ServletHolder(new UserServlet()), "/user/*");
		context.addServlet(new ServletHolder(new LoginServlet()), "/login");
		context.addServlet(new ServletHolder(new LogoutServlet()), "/logout");

		HandlerList handlers = new HandlerList();

		handlers.setHandlers(new Handler[] { resource_handler, context });
		server.setHandler(handlers);

		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
