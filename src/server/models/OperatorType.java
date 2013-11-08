package server.models;

import org.json.JSONException;
import org.json.JSONObject;

public class OperatorType {

	private Long idType;
	private String title;
	private String description;
	private String image;
	private String className;
	
	public OperatorType() {
		this.idType = 0L;
		this.title = "";
		this.description = "";
		this.setImage("");
		this.setClassName(PlugOperator.class.getName());
	}
	
	public OperatorType(Long idType, String title, String description, String image, String className) {
		this.idType = idType;
		this.title = title;
		this.description = description;
		this.setImage(image);
		this.setClassName(className);
	}

	public Long getIdType() {
		return idType;
	}

	public void setIdType(Long idType) {
		this.idType = idType;
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

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	@Override
	public boolean equals(Object o)
	{
	    return o != null && o instanceof OperatorType
	       && ((OperatorType)o).getIdType().equals(this.getIdType())
	       && ((OperatorType)o).getTitle().equals(this.getTitle())
	       && ((OperatorType)o).getDescription().equals(this.getDescription());
	}

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("title", this.getTitle());
            jsonObject.put("type", this.getIdType());
            jsonObject.put("description", this.getDescription());
            jsonObject.put("image", this.getImage());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }


}
