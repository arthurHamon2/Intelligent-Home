package server.Sensors.Frame;

import server.models.Sensor;
import server.models.SwitchWindowSensor;
import server.models.SwitchWindowSensor.enumContact;


public class SwitchFrame extends OceanAbstractFrame {
	protected static final byte CONTACT_MASK = 1 << 0;

	public SwitchFrame(String trame) throws Exception {
		super(trame);
		


	}
	
	
	public String toString()
	{
		String str = super.toString();
		str += "I'm a Switch Window Frame !\n";
		str += "State : ";
		enumContact c = getContact(mFrame);
		switch (c) {
		case CONTACT_OPEN:
			str += "State : Open";
			break;
		case CONTACT_CLOSE:
			str += "State : Close";
			break;

		}
		

		
		return str;
		
		
	}
	
	public enumContact getContact(byte[] b)
	{
		System.out.println("\n"+Integer.toString(readUnsigendByte(b, enumFrameStructure.DATA_BYTE0))+"\n");
		int state = readUnsigendByte(b, enumFrameStructure.DATA_BYTE0)&CONTACT_MASK;
		enumContact retour = enumContact.CONTACT_CLOSE;
		
	switch (state) {
	case 0:
		retour = enumContact.CONTACT_OPEN;
		break;
	case 1:
		retour = enumContact.CONTACT_CLOSE;
		break;

	}
	
	return retour;
	}


	@Override
	public Sensor populate(Sensor s){
		SwitchWindowSensor ts =(SwitchWindowSensor) s;
    	ts.setState(getContact(mFrame));
    	
    	return ts;
		
		
	}
	

}
