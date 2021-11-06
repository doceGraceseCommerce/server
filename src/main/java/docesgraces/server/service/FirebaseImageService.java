package docesgraces.server.service;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import com.google.cloud.storage.StorageOptions;

@Service
public class FirebaseImageService {

	@Value("${firebase.bucket-name}")
	private String bucketName;

	@Value("${firebase.image-url}")
	private String imageUrl;;

	public String uploadFile(MultipartFile multipartFile) throws Exception {

		try {
			String imageName = generateFileName(multipartFile.getOriginalFilename());
			byte[] fileByteArray = multipartFile.getBytes();
			BlobId blobId = BlobId.of(bucketName, imageName);
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();
			ClassPathResource serviceAccount = new ClassPathResource("firebase.json");
			Credentials credentials = GoogleCredentials.fromStream(serviceAccount.getInputStream());
			Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
			storage.create(blobInfo, fileByteArray);
			System.out.println(imageName);
			return String.format(imageUrl, URLEncoder.encode(imageName, StandardCharsets.UTF_8));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

	}

	private String generateFileName(String originalFileName) {
		return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
	}

	private String getExtension(String originalFileName) {
		return StringUtils.getFilenameExtension(originalFileName);
	}
}