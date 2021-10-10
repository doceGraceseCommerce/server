package docesgraces.server.exception;

public class ExpiredTokenException extends RuntimeException {
	public ExpiredTokenException(String errorMessage) {
		super(errorMessage);
	}
}
