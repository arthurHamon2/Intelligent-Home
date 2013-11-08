package server.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import server.database.service.MeasureTypeService;
import server.models.SwitchWindowSensor.enumContact;



public class Rocker2Sensor extends Sensor {
	
	protected final int BUTTON_A_NUM = 0;
	protected final int BUTTON_B_NUM = 1;
	public enum enumButtonState {
	BUTTON_ON(1),
	BUTTON_OFF(0),
	;
 
	/** L'attribut qui contient la valeur associé à l'enum */
	private final int value;
 
	/** Le constructeur qui associe une valeur à l'enum */
	private enumButtonState(int value) {
		this.value = value;
	}
	
    public static enumButtonState byOrdinal(int ord) {
        for (enumButtonState m : enumButtonState.values()) {
            if (m.value == ord) {
                return m;
            }
        }
        return null;
    }	
}
	
	public Rocker2Sensor(Long ref, String title) {
		super(ref, title, new ArrayList<Measure>() ,new Date());
		MeasureTypeService s = new MeasureTypeService();
		MeasureType t = s.findByTitle("EtatBouton");
		getMeasures().add(new Measure(0L, getSensorRef(), t, 0));
		getMeasures().add(new Measure(0L, getSensorRef(), t, 0));
}

	public Rocker2Sensor() {
		super();
	}

	public enumButtonState getState()
	{
		enumButtonState c = enumButtonState.byOrdinal((int)getMeasures().get(0).getValue());
		return c;
	
	}
	
	public void setStateButtonAState(enumButtonState c)
	{
		
		List<Measure> L = getMeasures();
		L.get(BUTTON_A_NUM).setValue(c.value);
		
	}
	
	public void setStateButtonBState(enumButtonState c)
	{
		
		List<Measure> L = getMeasures();
		L.get(BUTTON_B_NUM).setValue(c.value);
		
	}
}
