package server.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Rule extends AbstractModel
{
	private Integer id;
	private String ref;
	private String title;
	private String contents;
	private boolean enable;
	
	public Rule()
    {
	    super();
        this.id = 0;
        this.ref = "";
        this.title = "";
        this.contents = "";
        this.setEnable(false);
    }
	
	public Rule(Integer id, String ref, String title, String contents, boolean enable)
    {
        super();
        this.id = id;
        this.ref = ref;
        this.title = title;
        this.contents = contents;
        this.setEnable(enable);
    }

    public Integer getId()
	{
		return this.id;
	}
	
	public Rule setId(Integer id)
	{
		this.id = id;
		return this;
	}
	
	public String getRef()
	{
		return this.ref;
	}
	
	public Rule setRef(String ref)
	{
		this.ref = ref;
		return this;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public Rule setTitle(String title)
	{
		this.title = title;
		return this;
	}
	
	public String getContents()
	{
		return this.contents;
	}
	
	public Rule setContents(String contents)
	{
		this.contents = contents;
		return this;
	}
	
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	@Override
	public boolean equals(Object o)
	{
	    return o != null && o instanceof Rule
	        && ((Rule)o).getId() == this.getId()
	        && ((Rule)o).getRef() == this.getRef()
	        && ((Rule)o).getTitle().equals(this.getTitle())
	        && ((Rule)o).getContents().equals(this.getContents());
	}

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.getId());
            // jsonObject.put("ref", this.getRef());
            jsonObject.put("title", this.getTitle());
            jsonObject.put("contents", this.getContents());
            jsonObject.put("enable", this.isEnable());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }
}
