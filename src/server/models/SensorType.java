package server.models;

import org.json.JSONException;
import org.json.JSONObject;

public class SensorType extends AbstractModel
{

    private Long idType;
    private String eed;
    private String sensorClass;
    private String frameClass;
    private String title;
    private String image;


	public SensorType()
    {
        super();
        this.idType = 0L;
        this.eed = "";
        this.sensorClass = "";
        this.frameClass = "";
        this.title = "";
        this.image = "";
    }

    public SensorType(Long id, String eed, String sensorClass, String frameClass, String title, String image)
    {
        super();
        this.idType = id;
        this.eed = eed;
        this.sensorClass = sensorClass;
        this.frameClass = frameClass;
        this.title = title;
        this.image = image;
    }

    public Long getIdType()
    {
        return idType;
    }

    public void setIdType(Long idType)
    {
        this.idType = idType;
    }

    public String getEed()
    {
        return eed;
    }

    public void setEed(String eed)
    {
        this.eed = eed;
    }

    public String getSensorClass()
    {
        return this.sensorClass;
    }

    public void setSensorClass(String sensorClass)
    {
        this.sensorClass = sensorClass;
    }

    public String getFrameClass()
    {
        return this.frameClass;
    }

    public void setFrameClass(String frameClass)
    {
        this.frameClass = frameClass;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

    @Override
    public boolean equals(Object o)
    {
        return o != null && o instanceof SensorType
            && ((SensorType)o).getIdType().equals(this.getIdType())
            && ((SensorType)o).getEed().equals(this.getEed())
            && ((SensorType)o).getTitle().equals(this.getTitle())
            && ((SensorType)o).getSensorClass().equals(this.getSensorClass())
            && ((SensorType)o).getFrameClass().equals(this.getFrameClass());
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.getIdType());
            jsonObject.put("title", this.getTitle());
            jsonObject.put("image", this.getImage());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
