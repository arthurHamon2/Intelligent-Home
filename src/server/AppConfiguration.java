package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import server.database.dal.ConnectionException;
import server.database.dal.Dal;

public class AppConfiguration {
	String mIpAnOceanGateway;
	String mMailUserServer;
	String mMailPassword;
	String mMailHost;
	int mPortAnOceanGateway;
	String mMailPort;
	
	private static AppConfiguration instance = null;
	
	private AppConfiguration(String path) {
		parseConfig(path);
	}
	
	public static AppConfiguration instance()
	{
		if (null == instance){
			instance = new AppConfiguration("config.xml");
		}
		return instance;
		
	}
	
	protected void parseConfig(String path) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db=null;
			
		System.out.println("Parsing config....");
		
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc=null;
		try {
			doc = db.parse(new FileInputStream("config.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NodeList n = doc.getElementsByTagName("ipAnOceanGateway");
		mIpAnOceanGateway = n.item(0).getTextContent();
		n = doc.getElementsByTagName("portAnOceanGateway");
		mPortAnOceanGateway = Integer.parseInt(n.item(0).getTextContent(),10);
		n = doc.getElementsByTagName("mailPort");
		mMailPort = n.item(0).getTextContent();
		n = doc.getElementsByTagName("mailUserServer");
		mMailUserServer = n.item(0).getTextContent();
		n = doc.getElementsByTagName("mailPassword");
		mMailPassword = n.item(0).getTextContent();
		n = doc.getElementsByTagName("mailHost");
		mMailHost= n.item(0).getTextContent();
	
		  //= Integer.parseInt(n.item(0).getTextContent(), 10);
		  //= Integer.parseInt(n.item(0).getTextContent(), 10);
	
	}

	public String getIpAnOceanGateway() {
		return mIpAnOceanGateway;
	}

	public String getMailUserServer() {
		return mMailUserServer;
	}

	public String getMailPassword() {
		return mMailPassword;
	}

	public String getMailHost() {
		return mMailHost;
	}

	public int getPortAnOceanGateway() {
		return mPortAnOceanGateway;
	}

	public String getMailPort() {
		return mMailPort;
	}

}
