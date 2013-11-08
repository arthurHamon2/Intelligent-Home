package server.models;

import org.json.JSONException;
import org.json.JSONObject;

import server.Sensors.OceanConnexionManager;
import server.Sensors.Frame.FrameBuilder;

public class PlugOperator extends Operator
{
    public PlugOperator(Long operatorRef, OperatorType operatorType, String title,
			String description){
    	super(operatorRef, operatorType, title, description);

	}
	@Override
	public void execute(Action a) throws Exception
	{
		OceanConnexionManager.sendFrame(FrameBuilder.buildFrame(this, a));
	}

}
