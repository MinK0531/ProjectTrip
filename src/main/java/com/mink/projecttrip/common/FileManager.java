package com.mink.projecttrip.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    public final static String FILE_UPLOAD_PATH = "D:\\KMK\\SpringProject\\upload";

    public static String saveFile(long userId, MultipartFile file) {
        if (file == null) {
            return null;
        }

        String directoryName = "/" + userId + "_" + System.currentTimeMillis();

        String directoryPath = FILE_UPLOAD_PATH + directoryName;

        File directory = new File(directoryPath);

        if (!directory.mkdir()) {
            return null;
        }

        String filePath = directoryPath + "/" + file.getOriginalFilename();

        try {
            byte[] bytes = file.getBytes();

            Path path = Paths.get(filePath);
            Files.write(path, bytes);
        } catch (IOException e) {
            return null;
        }

        return "/images" + directoryName + "/" + file.getOriginalFilename();
    }
    public static boolean removeFile(String imagePath) {

        if(imagePath == null) {
            return false;
        }

        String fullFilePath = FILE_UPLOAD_PATH + imagePath.replace("/images", "");

        Path path = Paths.get(fullFilePath);
        Path directoryPath = path.getParent();

        try {
            Files.delete(path);
            Files.delete(directoryPath);

        } catch (IOException e) {
            return false;
        }

        return true;
    }
}