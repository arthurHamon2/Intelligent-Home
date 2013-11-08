package server.models;


public abstract class VirtualFrame extends AbstractFrame {

	private Long id;
//	private Long idsensor;
	private String frameType;
	
	public abstract void updateState();
		
	public VirtualFrame(Long id, Long idSensor, String frameType)
	{
		super(idSensor);
		this.id = id;
		this.frameType = frameType;
	}


	public String getFrameType() {
		return frameType;
	}


	public void setFrameType(String frameType) {
		this.frameType = frameType;
	}


/*	public void setIdsensor(Long idsensor) {
		this.idsensor = idsensor;
	}


	public Long getIdsensor() {
		return idsensor;
	}
*/

	public void setId(Long id) {
		this.id = id;
	}


	public Long getId() {
		return id;
	}


	@Override
	public Sensor populate(Sensor s) {
		// TODO Auto-generated method stub
		return null;
	}

}
