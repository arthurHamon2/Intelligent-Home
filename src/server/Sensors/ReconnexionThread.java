package server.Sensors;


public class ReconnexionThread extends Thread {

	final private static int UPDATE_TIME = 2000;
	private static boolean mIsRunning = false;

    public void run() {
    	mIsRunning = true;
		try {
			while (!OceanConnexionManager.getConnexionisActive()) {
				System.out.println("Tentative de reconnexion dans 2 secondes...");
				Thread.sleep(UPDATE_TIME);
				OceanConnexionManager.reconnect();
				
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
    	mIsRunning = false;

    }

	public static boolean getIsRunning() {
		return mIsRunning;
	}
	
	
}	