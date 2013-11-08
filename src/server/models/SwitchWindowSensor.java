package server.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import server.database.service.MeasureTypeService;

public class SwitchWindowSensor extends Sensor {

	public enum enumContact {
		CONTACT_OPEN(0),
		CONTACT_CLOSE(1),
		;
	 
		/** L'attribut qui contient la valeur associé à l'enum */
		private final int value;
	 
		/** Le constructeur qui associe une valeur à l'enum */
		private enumContact(int value) {
			this.value = value;
		}
		
	    public static enumContact byOrdinal(int ord) {
	        for (enumContact m : enumContact.values()) {
	            if (m.value == ord) {
	                return m;
	            }
	        }
	        return null;
	    }	
	}
		
	public SwitchWindowSensor(Long ref, String title) {
		super(ref, title, new ArrayList<Measure>() ,new Date());
		MeasureTypeService s = new MeasureTypeService();
		MeasureType t = s.findByTitle("Fenetre");
		getMeasures().add(new Measure(0L, getSensorRef(), t, 0));
	}
	
	public SwitchWindowSensor() {
		super();
	}
	
	public enumContact getState()
	{
		enumContact c = enumContact.byOrdinal((int)getMeasures().get(0).getValue());
		return c;

	}
	
	public void setState(enumContact c)
	{
		
		List<Measure> L = getMeasures();
		L.get(0).setValue(c.value);
		setMeasures(L);
	}
	

}
