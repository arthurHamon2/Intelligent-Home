package server;

import server.Sensors.OceanConnexionManager;
import server.Sensors.Frame.FrameFactory;
import server.Sensors.Frame.OceanAbstractFrame;
import server.models.FrameCatcher;

public class ThreadSensors extends Thread {

	public ThreadSensors() {

	}

	public void run() {

		try {
			while (true) {
				String s =null;
				try{
				s = OceanConnexionManager.readFrame();
				}
				catch (Exception e) {
					System.out.println("error while reading, thread OceanSensor is waiting 10 seconds and will resume !");
					Thread.sleep(10000);

				}
				if (s != null)
				{

				System.out.println(s + "\n");

				OceanAbstractFrame f = null;

				f = FrameFactory.getFrame(s);

					System.out.println(f.toString());
					FrameCatcher fc = new FrameCatcher();

					fc.Persist(f);

			}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
