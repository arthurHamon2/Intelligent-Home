package server.models;

public class MeasureType extends AbstractModel
{

    private Long idMeasureType;
    private String title;
    private String unity;
    private String image;

    public MeasureType()
    {
        this.idMeasureType = 0L;
        this.title = "";
        this.unity = "";
        this.setImage("");
    }

    public MeasureType(Long id, String title, String unity, String image)
    {
        this.idMeasureType = id;
        this.title = title;
        this.unity = unity;
        this.setImage("");
    }

    public Long getIdMeasureType()
    {
        return idMeasureType;
    }

    public void setIdMeasureType(Long idMeasureType)
    {
        this.idMeasureType = idMeasureType;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getUnity()
    {
        return unity;
    }

    public void setUnity(String unity)
    {
        this.unity = unity;
    }
    
    public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	@Override
    public boolean equals(Object o)
    {
        return o != null && o instanceof MeasureType
            && ((MeasureType)o).getIdMeasureType().longValue() == this.getIdMeasureType().longValue()
            && ((MeasureType)o).getTitle().equals(this.getTitle())
            && ((MeasureType)o).getUnity().equals(this.getUnity());
    }
}
