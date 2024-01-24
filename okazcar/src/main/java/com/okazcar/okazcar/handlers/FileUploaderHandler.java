package com.okazcar.okazcar.handlers;

import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class FileUploaderHandler {

    public static File uploadFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String absolutePath = new FileSystemResource("").getFile().getAbsolutePath();
        Path targetLocation = Paths.get(absolutePath+"/picture/" + fileName);
        File dir = new File(absolutePath+"/picture/");
        if (!dir.exists()) {
            boolean result = dir.mkdir();
            if (result) {
                System.out.println("Directory created");
            } else {
                System.out.println("Directory creation failed");
            }
        }
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {

            throw new IOException("Could not store file " + fileName + ". Please try again!", e);
        }
        return targetLocation.toFile();
    }

    public static void deleteFile(File file) {
        if (file.delete()) {
            System.out.println("Deleted the file: " + file.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

}
