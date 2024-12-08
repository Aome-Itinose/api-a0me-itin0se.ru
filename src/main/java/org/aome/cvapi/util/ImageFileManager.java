package org.aome.cvapi.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class ImageFileManager {
    private final String PATH = "src/main/resources/static/profilePhotos";

    public String writePhoto(byte[] photo, String fullName) throws IOException {
        if (photo == null || photo.length == 0) return null;

        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu_MM_dd_HH_mm_ss"));
        String dirName = fullName.replace(" ", "");

        String dirPath = String.format("%s/%s", PATH, dirName);
        String filePath = String.format("%s/%s.jpg", dirPath, fileName);

        try {
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdir();
            }

            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(photo);
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }

        }catch (IOException e) {
            log.error(e.getMessage());
            throw new IOException(e.getMessage());
        }
        log.debug("File save at {}", filePath);
        return String.format("%s/%s", dirName, fileName);
    }

    public InputStreamResource readPhoto(String path) throws IOException {
        if (path == null || path.isEmpty()) return null;

        String filePath = String.format("%s/%s.jpg", PATH, path); //Todo: jpg depend on file type

        File file = new File(filePath);
        if (!file.exists()) return null;

        InputStreamResource photo;
        try {
            photo = new InputStreamResource(new FileInputStream(file));
        }catch (IOException e) {
            log.error(e.getMessage());
            throw new IOException(e.getMessage());
        }
        log.debug("File read from {}", filePath);
        return photo;
    }
}
