package docesgraces.server.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import docesgraces.server.exception.MailerException;

@Service
public class MailerService {

	@Value("${spring.mail.username}")
	private String from;

	@Value("${spring.mail.password}")
	private String password;

	public String enviarEmail(String email, String assunto, String mensagemString) {

		String to = email;

		// String from = "";

		// String password = "";

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setHeader("Content-Type", "text/html; charset=\"iso-8859-1\"");
			message.setSubject(assunto);
			message.setContent(mensagemString, "text/html; charset=iso-8859-1");
			message.setHeader("Content-Transfer-Encoding", "quoted-printable");

			System.out.println("sending...");
			Transport.send(message);
			System.out.println("Sent message successfully....");
			return "Mensagem enviada com sucesso!";

		} catch (MessagingException mex) {
			mex.printStackTrace();
			System.out.println(mex.getMessage());
			throw new MailerException("Falha ao enviar mensagem, tente novamente!");
		}

	}

}
