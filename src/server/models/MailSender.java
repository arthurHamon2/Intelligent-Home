package server.models;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import server.AppConfiguration;
import server.Main;
import server.database.service.UserService;

public class MailSender extends Operator {
	private User from ;
	
	private static AppConfiguration CONFIGURATION = AppConfiguration.instance();
	private static String HOST= CONFIGURATION.getMailHost();
	private static String PORT  = CONFIGURATION.getMailPort();
	private static String MDP = CONFIGURATION.getMailPassword();
	private static String MailUserServer = CONFIGURATION.getMailUserServer();
	
	
	private UserService s = new UserService();
	
	public MailSender(Long operatorRef, OperatorType type, String title, String desciption) {
		super(operatorRef, type, title, desciption);
		//this.HOST = Main.Configuration.getMailHost();
		/*this.PORT = Main.Configuration.getMailPort();
		this.MDP = Main.Configuration.getMailPassword();
		this.MailUserServer = Main.Configuration.getMailUserServer();*/
		this.from = new User(2, "", MDP, MailUserServer, "");
				//s.findByUser("admin");
	}

	@Override
	public void execute(Action a) {
		final String username = this.from.getMail();
		final String password = this.from.getPwd();
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.port", PORT);
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		
		/*// Get system properties
		Properties properties = System.getProperties();
		// Setup mail server
		properties.setProperty("mail.smtp.host", this.host);
		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);*/
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(this.from.getMail()));
			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					/*this.from.getMail())*/this.s.find(1).getMail()));
			// Set Subject: header field
			message.setSubject(a.getTitle());
			// Send the actual HTML message, as big as you like
			message.setContent(a.getDescription(), "text/html");
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
