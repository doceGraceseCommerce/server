package docesgraces.server.listener;

import docesgraces.server.model.Produto;
import docesgraces.server.service.TelegramBotService;

import javax.persistence.PostPersist;

public class ProdutoListener {

	@PostPersist
	public void afterPresist(final Produto saved) {
		try {
			TelegramBotService telegram = new TelegramBotService();
			telegram.setMensagem("teste");
			telegram.enviarMensagem("save");
			System.out.println("salvou");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
