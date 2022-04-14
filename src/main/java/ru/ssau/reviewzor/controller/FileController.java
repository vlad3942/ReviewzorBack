package ru.ssau.reviewzor.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.ssau.reviewzor.pojo.MessageResponse;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Value("${app.upload.path}")
    private String uploadPath;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        if(file != null && !file.getOriginalFilename().isEmpty()) {
            final File path = new File(uploadPath);
            if(!path.exists() && !path.mkdir()) {
                return ResponseEntity.internalServerError().body(new MessageResponse("Error creating images directory"));
            }
            final String uniqFileNamePart = UUID.randomUUID().toString();
            final String resultFileName = uniqFileNamePart + "." + file.getOriginalFilename();

            file.transferTo(new File(path.getAbsolutePath() + File.separator + resultFileName));
            return ResponseEntity.ok(resultFileName);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("File was null error."));
    }
}
