package docesgraces.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import docesgraces.server.dto.ImageResponse;
import docesgraces.server.dto.MessageResponse;
import docesgraces.server.model.Usuario;
import docesgraces.server.repository.UsuarioRepository;
import docesgraces.server.service.AuthenticationService;
import docesgraces.server.service.FirebaseImageService;

@RestController
@CrossOrigin
@RequestMapping("/images")
public class ImageController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private FirebaseImageService firebaseImageService;

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping
	public ResponseEntity<?> create(@RequestParam(name = "file") MultipartFile file,
			@RequestHeader String Authorization) {
		try {
			String usuarioId = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(usuarioId)).get();
			if (usuario.getUsuarioTipo() == 1) {
				String fileName = firebaseImageService.uploadFile(file);
				System.out.println(fileName);
				return new ResponseEntity<ImageResponse>(ImageResponse.toDTO(fileName), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<MessageResponse>(
						MessageResponse.toDTO("Usuário não possui autorização para carregar imagens!"),
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getMessage()), HttpStatus.BAD_REQUEST);

		}
	}
}
