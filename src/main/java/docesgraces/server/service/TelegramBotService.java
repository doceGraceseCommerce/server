package docesgraces.server.service;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import docesgraces.server.exception.TelegramBotException;
import docesgraces.server.listener.EventListener;
import lombok.Getter;
import lombok.Setter;

@Service
public class TelegramBotService implements EventListener {

	@Getter
	@Setter
	public String mensagem;

	@Value("${telegram.api-token}")
	private String apiToken;

	@Value("${telegram.chat-id}")
	private String chatId;

	@Override
	public void enviarMensagem(String eventType) {

		String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

		String text = mensagem;

		urlString = String.format(urlString, apiToken, chatId, text);

		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			InputStream is = new BufferedInputStream(conn.getInputStream());
			System.out.println("O evento " + eventType + " foi acionado!");
		} catch (Exception e) {
			throw new TelegramBotException("Não foi possivel completar a requisão");

		}
	}

}