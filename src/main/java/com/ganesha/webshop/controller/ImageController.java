package com.ganesha.webshop.controller;

import com.ganesha.webshop.model.dto.request.Base64ImageUploadRequest;
import com.ganesha.webshop.service.FileUploaderService;
import com.ganesha.webshop.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final FileUploaderService fileUploaderService;

    @GetMapping("/images/{fileName}")
    public ResponseEntity<FileSystemResource> serveFile(@PathVariable String fileName) { // FileSystemResource-olyan objektum, amikor statikus file-t szeretnenk visszakuldeni
        Optional<File> file = imageService.serveFile(fileName); // visszaadja a keresett file-t (Optional.of(file) vagy Optional.empty)

        if (file.isPresent()) {
            HttpHeaders headers = new HttpHeaders(); //HTTPHeaders-et lehet beallitani
            headers.setContentType(MediaType.parseMediaType(getMimeType(file.get())));
//            headers.setContentDispositionFormData("attachment", fileName); //letolti a file-t pl: http://localhost:8080/incense-nagchampa-1.1.jpg (automatikusan downloads folderbe menti)
                                                                            // amikor letölti a fájlt, ne nyissa meg, hanem mentse el fájlként, a megadott fileName néven.

            return ResponseEntity.ok()
                    .headers(headers) // egyedi fejlec beallitasa
                    .body(new FileSystemResource(file.get())); // konkret file a serverrol, amit megtalalt
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    private String getMimeType(File file) { //visszaadja image/png (mint az application/json)
        try {
            return Files.probeContentType(file.toPath());
        } catch (IOException e) {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE; //default type ha nem tudja kiolvasni
        }
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadBase64Image(@RequestBody Base64ImageUploadRequest request) throws IOException {
        fileUploaderService.upload(request.filename(), request.base64());
        return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded successfully");
    }
}


//    {
//      filename: "xy.jpg",
//      base64: "kasjdksjklajdskfjdasljkslajfdlajlkdsjal"
//        }
