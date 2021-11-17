package docesgraces.server.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

	@Value("${twilio.account-sid}")
	private String ACCOUNT_SID;

	@Value("${twilio.auth-token}")
	private String AUTH_TOKEN;

	@Value("${twilio.to}")
	private String to;

	@Value("${twilio.from}")
	private String from;

	@Getter
	@Setter
	public String mensagem;

	public void enviarWhatsAppMessage() {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message message = Message.creator(new PhoneNumber(to), new PhoneNumber(from), mensagem).create();
		System.out.println("Mensagem enviada");
	}
}