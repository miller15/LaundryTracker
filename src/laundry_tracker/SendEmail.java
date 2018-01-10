package communication;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class SendEmail {
	
	private static MimeMessage createMessage(Session session, String from, String[] toArray, String subject, String body){
		// Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);

        System.out.println("");
		System.out.println("SendEmail.java: createMessage: ");
		for(int i=0; i<toArray.length; i++){
			System.out.println(toArray[i]);
		}
		System.out.println("");
        
        // Set To: header field of the header.
        try {
        	for(int i=0; i < toArray.length; i++){
        		message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(toArray[i]));
        	}
			
		} catch (MessagingException e1) {
			e1.printStackTrace();
		}

        // Set Subject: header field
        try {
			message.setSubject(subject);
		} catch (MessagingException e1) {
			e1.printStackTrace();
		}

        // Now set the actual message
        try {
			message.setText(body);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
        return message;

	}
	
	private static Properties setProps(){
		// Get system properties
	    Properties properties = System.getProperties();

	    // Setup mail server
	    properties.setProperty("mail.smtp.host", "smtp.gmail.com"); //using gmail server host
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.port", "587");
		properties.setProperty("mail.smtp.auth", "true");
		
		return properties;
		
		
	}
	
	private static void send(MimeMessage message, String password, String protocol, Session session, String username){
		try {
			Transport t = session.getTransport(protocol);
			t.connect(username, password);
			try {
				t.sendMessage(message, message.getAllRecipients());
				JOptionPane.showConfirmDialog(null,  "Message was successfully sent", "Sent Success", -1);
			} catch (NullPointerException e) {
				JOptionPane.showConfirmDialog(null, "Please select recipients", "No Recipients", -1);
				//e.printStackTrace();
			} catch (SendFailedException f) {
				JOptionPane.showConfirmDialog(null, "Invalid Email Address for a recipient", "Send Failed", -1);
			}
			t.close();
			//System.out.println("Message Sent successfully");
		} catch (MessagingException e) {
			JOptionPane.showConfirmDialog(null, "Please select recipients", "No Recipients", -1);
			e.printStackTrace();
		}
	}

	public static void setupAndSend(String subject, String body, String from, String password, String[] toArray, String username){
		Session session = Session.getInstance(setProps());
		System.out.println("");
		System.out.println("SendEmail.java: setupAndSend: ");
		for(int i=0; i<toArray.length; i++){
			System.out.println(toArray[i]);
		}
		System.out.println("");
		MimeMessage message = createMessage(session, from, toArray, subject, body);
		send(message, password, "smtp", session, username);
	}

}
