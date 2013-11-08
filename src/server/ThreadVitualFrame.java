package server;

import java.util.List;

import server.Sensors.OceanConnexionManager;
import server.Sensors.Frame.FrameBuilder;
import server.database.service.OperatorService;
import server.database.service.VirtualFrameService;
import server.models.Action;
import server.models.ActionOperator;
import server.models.FrameCatcher;
import server.models.Operator;
import server.models.VirtualFrame;

public class ThreadVitualFrame extends Thread {
	final private static int UPDATE_TIME = 60000;

	public ThreadVitualFrame() {

	}

	public void run() {

		VirtualFrameService se = new VirtualFrameService();
		FrameCatcher fc = new FrameCatcher();
		for (;;) {
			try {
				List<VirtualFrame> l = se.findAll();
				for (VirtualFrame v : l) {
					{
						v.updateState();
						fc.Persist(v);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(UPDATE_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
	}
}
