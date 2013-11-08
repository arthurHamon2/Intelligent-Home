package server.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

public class Action extends AbstractModel {

	private Long id;
	private OperatorType operatorType;
	private String frame;
	private String title;
	private String description;
	private String image;
	private Date addedAt;
	private Date executedAt;

	public Action() {
		super();
		this.id = 0L;
		this.operatorType = null;
		this.frame = "";
		this.title = "";
		this.description = "";
		this.image = "";
	}

	public Action(Long id, OperatorType operatorType, String frame,
			String title, String description, String image) {
		super();
		this.id = id;
		this.operatorType = operatorType;
		this.frame = frame;
		this.title = title;
		this.description = description;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OperatorType getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(OperatorType operatorType) {
		this.operatorType = operatorType;
	}

	public String getFrame() {
		return frame;
	}

	public void setFrame(String frame) {
		this.frame = frame;
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Action))
			return false;

		Action a = (Action) obj;
		return a.id.longValue() == this.id.longValue() /*
														 * &&
														 * a.operatorType.equals
														 * (this.operatorType)
														 */
				&& a.frame.equals(frame) && a.title.equals(title)
				&& a.description.equals(description);
	}

	public Date getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(String addedAt) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			this.addedAt = formatter.parse(addedAt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Date getExecutedAt() {
		return executedAt;
	}

	public void setExecutedAt(String executedAt) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			this.executedAt = formatter.parse(executedAt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public JSONObject toJson() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm", Locale.FRENCH);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", this.getId());
			jsonObject.put("title", this.getTitle());
			jsonObject.put("description", this.getDescription());
			jsonObject.put("image", this.getImage());
            jsonObject.put("frame", this.getFrame());
			if (this.getAddedAt() != null)
                jsonObject.put("addedAt", simpleDateFormat.format(this.getAddedAt()));
			if (this.getExecutedAt() != null)
                jsonObject.put("executedAt", simpleDateFormat.format(this.getExecutedAt()));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;

	}

}
