package server.Sensors.Frame;

import server.database.service.SensorTypeService;

public class FrameFactory {


	static public OceanAbstractFrame getFrame(String frame) throws Exception {
		OceanAbstractFrame retour = null;
		// If this is an ORG 7 device in teach Mode
		if (OceanAbstractFrame.isTeachInFrame(frame)) {
			TeachInFrame TIF = new TeachInFrame(frame);
			retour = TIF;
		} else {
			long id = OceanAbstractFrame.parseSensorId(frame);

				SensorTypeService s = new SensorTypeService();
				retour = s.makeFrame(id,frame);


		}
		return retour;

	}

}
