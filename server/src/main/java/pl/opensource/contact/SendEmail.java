package pl.opensource.contact;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import pl.opensource.user.User;

@Component
@PropertySource("classpath:appConfig.properties")
public class SendEmail {

	@Value("${MAIL_USERNAME}")
	private String USERNAME;

	@Value("${MAIL_PASSWORD}")
	private String PASSWORD;

	public void welcome(User user) {
		
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USERNAME));
			String email = user.getUserDetail().getEmail();
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Witamy w grupie ZagrajmyRazem.pl!");
			message.setText("Drogi użytkowniku," + "\n Cieszymy się, że dołączyłeś do naszej grupy."
					+ "\n Aplikacja jest ciągle w fazie rozwoju, a jej głównym zadaniem jest umożliwienie wspólnej rozgrywki."
					+ "\n Zapraszamy do dołączenia do grupy na slack'u, aby zostawić feedback"
					+ "\n\n Wiadomość została wysłana automatycznie. Prosimy na tą wiadomość nie odpowiadać!");

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
