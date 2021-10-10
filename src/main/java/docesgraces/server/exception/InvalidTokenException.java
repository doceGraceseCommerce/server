package docesgraces.server.exception;

public class InvalidTokenException extends RuntimeException {
	public InvalidTokenException(String errorMessage) {
		super(errorMessage);
	}
}
