package docesgraces.server.dto;

import lombok.Getter;

@Getter
public class MessageResponse {

	private String message;

	public MessageResponse(String message) {
		this.message = message;
	}

	public MessageResponse() {
	}

	public static MessageResponse toDTO(String message) {
		return new MessageResponse(message);
	}
}
