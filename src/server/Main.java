package server;

import java.util.Date;
import java.util.Scanner;

import com.google.gdata.data.DateTime;

import server.Sensors.OceanConnexionManager;
import server.database.dal.ConnectionException;
import server.database.dal.Dal;
import server.database.service.RuleService;
import server.models.Rule;
import server.web.WebServer;



public class Main {
	public static AppConfiguration Configuration;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Configuration = AppConfiguration.instance();

		try {
			Dal.instance();
		} catch (ConnectionException e) {
			
			e.printStackTrace();
		}
		
		DateTime d = new DateTime(new Date(2013, 2, 19, 2, 0));
		
		/*String rule = "ON M9 > "+d.getValue()+" DO P1:3 ";
		Rule o = new Rule(42, "R", "T", rule, true);
		RuleService s = new RuleService();
		s.create(o);*/
		
        new WebServer().run();
		
		ThreadVitualFrame tvf = new ThreadVitualFrame() ;
		tvf.start(); 
		
		/*
		System.out.println("Entrer ip serveur (par defaut localhost) :");
		Scanner sc = new Scanner(System.in);
		String addr=sc.nextLine();
		
		System.out.println("Entrer port serveur (par defaut 5000) :");
		int port = sc.nextInt();
		sc.close();
		if (addr == "")
		{
			addr = "localhost";
		}
		if (port == 0)
		{
			port=5000;
		}
		*/


	   try {
		OceanConnexionManager.connect(Configuration.getIpAnOceanGateway(),Configuration.getPortAnOceanGateway());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		ThreadSensors t = new ThreadSensors() ;
		t.start();
		
		ThreadOperators to = new ThreadOperators();
		to.start();
	}

}
