package docesgraces.server.exception;

public class MailerException extends RuntimeException {
  public MailerException(String errorMessage) {
    super(errorMessage);
  }

}
