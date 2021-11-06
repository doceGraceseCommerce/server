package docesgraces.server.dto;

import lombok.Getter;

@Getter
public class MailerRequest {

  private String email;
  private String nome;
  private String telefone;
  private String assunto;
  private String mensagem;

}
