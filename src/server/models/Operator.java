package server.models;

import org.json.JSONException;
import org.json.JSONObject;

import server.Sensors.OceanConnexionManager;
import server.Sensors.Frame.FrameBuilder;

public abstract class Operator extends AbstractModel
{

	private Long operatorRef;
	private Room room;
	private OperatorType operatorType;
	private String title;
	private String description;
	
	public Operator()
    {
	    this.operatorRef = 0L;
        this.operatorType = new OperatorType();
        this.title = "";
        this.description = "";
        this.room = new Room(1L,null,"empty house",0,0,0,0);
    }
	
	public Operator(Long operatorRef, OperatorType operatorType, String title,
			String description) {
		this.operatorRef = operatorRef;
		this.operatorType = operatorType;
		this.title = title;
		this.description = description;
		this.room = new Room(1L,null,"empty house",0,0,0,0);
	}
	
	public Long getOperatorRef() {
		return operatorRef;
	}

	public void setOperatorRef(Long operatorRef) {
		this.operatorRef = operatorRef;
	}

	public OperatorType getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(OperatorType operatorType) {
		this.operatorType = operatorType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}


	@Override
	public boolean equals(Object o)
	{
	    return o != null && o instanceof Operator
	       && ((Operator)o).getOperatorRef().longValue() == this.getOperatorRef().longValue()
	       && ((Operator)o).getTitle().equals(this.getTitle())
	       && ((Operator)o).getDescription().equals(this.getDescription());
	}

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ref", this.getOperatorRef());
            jsonObject.put("title", this.getTitle());
            jsonObject.put("type", this.getOperatorType().toJson());
            jsonObject.put("description", this.getDescription());
            jsonObject.put("room", this.getRoom().toJson());
            
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }
    



	abstract public void execute(Action a) throws Exception;

}
