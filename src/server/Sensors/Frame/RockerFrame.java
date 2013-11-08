package server.Sensors.Frame;

import server.models.Rocker2Sensor;
import server.models.Rocker2Sensor.enumButtonState;
import server.models.Sensor;

public class RockerFrame extends OceanAbstractFrame {

	public enum enumPress {
		BUTTON_A_ON, BUTTON_A_OFF, BUTTON_B_ON, BUTTON_B_OFF, BUTTON_RELEASE
	};

	protected static final int CONTACT_MASK = 7 << 4;

	public RockerFrame(String trame) throws Exception {
		super(trame);
	}

	public String toString() {
		String str = super.toString();
		str += "I'm a Rocker Frame !\n";
		str += "State : ";
		enumPress c = getPress();
		switch (c) {
		case BUTTON_A_ON:
			str += "Button A On";
			break;
		case BUTTON_B_ON:
			str += "Button B On";
			break;
		case BUTTON_A_OFF:
			str += "Button A Off";
			break;
		case BUTTON_B_OFF:
			str += "Button B Off";
			break;
		case BUTTON_RELEASE:
			str += "Button Release";
			break;

		}
		return str;
	}

	public enumPress getPress() {
		System.out.println("\n"
				+ Integer.toString(readUnsigendByte(mFrame,
						enumFrameStructure.DATA_BYTE3)) + "\n");
		int state = (readUnsigendByte(mFrame, enumFrameStructure.DATA_BYTE3) & CONTACT_MASK) >> 4;
		enumPress retour = enumPress.BUTTON_B_ON;

		switch (state) {
		case 5:
			retour = enumPress.BUTTON_A_ON;
			break;
		case 7:
			retour = enumPress.BUTTON_A_OFF;
			break;
		case 1:
			retour = enumPress.BUTTON_B_ON;
			break;
		case 3:
			retour = enumPress.BUTTON_B_OFF;
			break;
		case 0:
			retour = enumPress.BUTTON_RELEASE;
			break;
		}

		return retour;
	}

	@Override
	public Sensor populate(Sensor r){

		Rocker2Sensor rs = (Rocker2Sensor) r;
		enumPress state = getPress();
		switch (state) {
		case BUTTON_A_OFF:
			rs.setStateButtonAState(enumButtonState.BUTTON_OFF);
			break;
		case BUTTON_A_ON:
			rs.setStateButtonAState(enumButtonState.BUTTON_ON);
			break;
		case BUTTON_B_OFF:
			rs.setStateButtonBState(enumButtonState.BUTTON_OFF);
			break;
		case BUTTON_B_ON:
			rs.setStateButtonBState(enumButtonState.BUTTON_ON);
			break;
		case BUTTON_RELEASE:
			// Here, we do nothing
			break;
		}
		return rs;

	}

}
