package docesgraces.server.exception;

public class TelegramBotException extends RuntimeException {
	public TelegramBotException(String errorMessage) {
		super(errorMessage);
	}

}
