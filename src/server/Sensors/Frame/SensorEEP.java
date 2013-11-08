package server.Sensors.Frame;


public class SensorEEP {
	public SensorEEP(int oRG,int func, int type)
	{
		mORG = oRG;
		mFUNC = func;
		mTYPE = type;
	}
	
	public int mORG;
	public int mFUNC;
	public int mTYPE;
	public String toString() {
		return Integer.toString(mORG)+"-"+Integer.toString(mFUNC)+"-"+Integer.toString(mTYPE);
	}
	

	
};
