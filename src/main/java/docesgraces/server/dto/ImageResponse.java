package docesgraces.server.dto;

import lombok.Getter;

@Getter
public class ImageResponse {

	private String imagem;

	public ImageResponse(String imagem) {
		this.imagem = imagem;
	}

	public ImageResponse() {
	}

	public static ImageResponse toDTO(String imagem) {
		return new ImageResponse(imagem);
	}
}
