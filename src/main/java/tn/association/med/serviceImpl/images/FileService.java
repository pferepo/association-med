package tn.association.med.serviceImpl.images;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService {

    public String saveImage(MultipartFile file) throws IOException {

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path path = Paths.get("uploads/images/" + fileName);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // ✅ IMPORTANT : inclure le dossier images
        return "/files/images/" + fileName;
    }

    public void deleteImage(String imageUrl) {
        try {
            if (imageUrl == null) return;

            // enlever "/files/"
            String relativePath = imageUrl.replace("/files/", "");

            // => images/xxx.jpg
            Path path = Paths.get("uploads").resolve(relativePath);

            Files.deleteIfExists(path);

            System.out.println("Image supprimée: " + path.toAbsolutePath());

        } catch (Exception e) {
            System.out.println("Erreur suppression image: " + e.getMessage());
        }
    }
}