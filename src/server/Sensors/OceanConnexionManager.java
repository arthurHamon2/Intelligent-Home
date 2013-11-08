package server.Sensors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import server.Sensors.Frame.OceanAbstractFrame;

public class OceanConnexionManager {
	
	static private int mPortNumber;
	static private String mServaddr;
	static private Socket mSocket;
	static private InputStreamReader mSr;
	static private PrintWriter mSw;
	static private BufferedReader mbr;
	static private boolean mConnexionIsActive;
	
	
	
	static protected String read(int nbCaractere) throws IOException
	{
		String s = new String();
			while (s.length() < nbCaractere) {
				int i= mbr.read();
				if (i==-1)
				{
					throw (new IOException("Connexion perdue"));
				}
				s += (char)i;
			}
	return s;	
		
		
	}
	
	static public String readFrame() throws Exception
	{
		if (!mConnexionIsActive)
		{
			throw (new Exception("Reception error : Connexion is not active !"));
		}
		
		String s =null;
		try
		{
			s = read(OceanAbstractFrame.NB_FRAME_CARACTER);
			int i = s.indexOf("A55A");
			if (i!=0 && i!=-1)
			{
				System.out.println("Problem during Frame reception, we skip !\n");
				s = s.substring(i, s.length());
				s+=read(OceanAbstractFrame.NB_FRAME_CARACTER-s.length());
			}
			
			if (i==-1 || !OceanAbstractFrame.isValid(s))
			{
				System.out.println("Big Problem or integrity problem during Frame reception, we skip !\n");
				s = null;
				
			}
		}
		catch (IOException e)
		{
			System.out.println("IO error !");
			connexionIsLost();
			s=null;
		}

		return s;
	}
	
	static public void sendFrame(String str) throws Exception
	{
		if (!mConnexionIsActive)
		{
			throw (new Exception("Sending error : Connexion is not active !"));
		}
		
		try {
			if (OceanAbstractFrame.isValid(str))
			{
				mSw.write(str);
				mSw.flush();
			}
			else
			{
				throw (new Exception("Invalid Frame"));
			}
		} catch (IOException e) {
			System.out.println("IO error !");
			connexionIsLost();
		}
		
	}

	static protected void connexionIsLost()
	{

		System.out.println("Connexion has been lost :-( \n");
		mConnexionIsActive = false;
		
		if (!ReconnexionThread.getIsRunning())
		{
		ReconnexionThread t = new ReconnexionThread();
		t.start();
	    System.out.println("try to retablish connexion...");
		}
		
	}
	
	public static void connect(String Servaddr, int PortNumber) throws Exception {
		
		if (mConnexionIsActive)
		{
			throw (new Exception("Connexion is already active !"));
		}
		
		mServaddr = Servaddr;
		mPortNumber = PortNumber;
		System.out.println("Demande de connexion...");
		try {
			mSocket = new Socket(InetAddress.getByName(mServaddr), mPortNumber);
			mSr = new InputStreamReader(mSocket.getInputStream());
			mbr = new BufferedReader(mSr);
			mSw = new PrintWriter(mSocket.getOutputStream());
			mConnexionIsActive = true;
		} catch (UnknownHostException e) {
			System.out.println("Unknow host !");
			connexionIsLost();


		} catch (IOException e) {
			System.out.println("Server is not running !");
			connexionIsLost();


		}
		System.out.println("Connecté !!");
		
	}
	
	public static void reconnect() throws Exception {
		connect(mServaddr, mPortNumber);
	}
	
	public static boolean getConnexionisActive()
	{
		return mConnexionIsActive;
	}
	

}
