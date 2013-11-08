package server.models;

import org.json.JSONException;
import org.json.JSONObject;

public class House extends AbstractModel {

	private Long id;
	private String name;
	private String image;		
	
	public House(Long id, String name, String image) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


	@Override
	public boolean equals(Object o) {
		return o != null
				&& o instanceof House
				&& ((House) o).getId().longValue() == this.id.longValue()
				&& ((House) o).getName().equals(this.getName())
				&& ((House) o).getImage().equals(this.getImage());
	}
	
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.getId());
            jsonObject.put("name", this.getName());
            jsonObject.put("image", this.getImage());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }

}
