package com.example.springsecurity.utilities;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;

import com.example.springsecurity.models.User;

public class MailUtil {

	public static boolean sendMail(User user,String status) throws SQLException, ServletException, IOException {
		// Security.addProvider(new

		final String username = "ngagenoreply@gmail.com";
		final String password = "Welcome@01";

		// Security.addProvider(new
		// com.sun.net.ssl.internal.ssl.Provider());
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		// If you want you use TLS
		// props.put("mail.smtp.auth", "true");

		props.put("mail.smtp.starttls.enable", "true");
		// If you want to use SSL
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.socketFactory.port", "587");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		String subject = "Account " + status ;
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(username));
			// InternetAddress addressTo = new InternetAddress(USERNAME);
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
			msg.setSubject(subject);

			msg.setSentDate(new java.util.Date());

			StringBuffer sb = new StringBuffer();
			String newline = System.getProperty("line.separator");
			sb.append("Dear " + user.getUserName() + "," + newline);

			sb.append("Your account " + status + " Successfully" + newline);
			sb.append("This is a system generated email, Please do not reply.");
			MimeBodyPart messageBodyPart = new MimeBodyPart();

			msg.setContent(sb.toString(), "text/html");
			messageBodyPart.setText(sb.toString());

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			msg.setContent(multipart);

			Transport.send(msg);

			return true;
		} catch (Exception exc) {
			return false;
		}
	}

}
