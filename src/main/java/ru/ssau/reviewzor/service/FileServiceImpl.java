package ru.ssau.reviewzor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {

    @Value("${app.upload.path}")
    private String uploadPath;

    private final String absPath = new File("").getAbsolutePath();

    @Override
    public boolean isExistsByName(final String filename) {
        if (filename == null || filename.equals("default")) return true;
        return Path.of(absPath, uploadPath, filename).toFile().exists();
    }
}
