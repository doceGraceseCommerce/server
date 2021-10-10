package docesgraces.server.exception;

public class InvalidLoginException extends RuntimeException {
	public InvalidLoginException(String errorMessage) {
		super(errorMessage);
	}
}