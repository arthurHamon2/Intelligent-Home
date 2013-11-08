package server.models;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import server.models.*;

public class VirtualMeteoFrame extends VirtualFrame{
	protected int mTemperatureExterieure;
	protected int mPression;
	protected int mVent;
	protected boolean mUpdateSuccess;
	

	public VirtualMeteoFrame(Long id, Long idSensor, String type ) {
		super(id, idSensor,type );
		mTemperatureExterieure=0;
		mUpdateSuccess = false;
	}

	@Override
	public void updateState() {
		

		try {
			/*
			System.out.println("Capture de la météo");
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(new URL("http://api.itimeteo.com/getMetar.ims?icao=LFLL&format=xml&decoded=true&displayAirport=true").openStream());
			NodeList n = doc.getElementsByTagName("Temperature");
			mTemperatureExterieure  = Integer.parseInt(n.item(0).getTextContent(), 10);
			mUpdateSuccess = true;
				        */
			try {
				
		        String inputStreamString = new Scanner(new URL("http://api.itimeteo.com/getMetar.ims?icao=LFLY&format=json&decoded=true&displayAirport=true").openStream(),"UTF-8").useDelimiter("\\A").next();
		        JSONObject obj = new JSONObject(inputStreamString);
		        List<String> list = new ArrayList<String>();
		        JSONArray array;
				System.out.println(obj.toString());
				mTemperatureExterieure = obj.getJSONObject("Metar").getJSONObject("DecodeLine0").getInt("Temperature");
				mPression = obj.getJSONObject("Metar").getJSONObject("DecodeLine0").getInt("Pressure");
				mVent = obj.getJSONObject("Metar").getJSONObject("DecodeLine0").getInt("WindSpeedKmh");
				
				mUpdateSuccess = true;
		       
		        
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	@Override
	public Sensor populate(Sensor s) {
		VirtualMeteoSensor v= (VirtualMeteoSensor) s;
		if (mUpdateSuccess)
		{
			v.setTemperature(mTemperatureExterieure);
			v.setPression(mPression);
			v.setVent(mVent);
			
		}
		return s;
	}
	
}
