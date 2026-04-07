package com.example.projectbase.util;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

    // ================= BASE UPLOAD DIR =================
    private static final Path UPLOAD_DIR = Paths.get("uploads");

    // ================= INIT =================
    static {
        try {
            if (!Files.exists(UPLOAD_DIR)) {
                Files.createDirectories(UPLOAD_DIR);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize upload directory", e);
        }
    }

    // ================= SAVE FILE =================
    public static String saveFile(String newFileName, String uploadPath, MultipartFile multipartFile) throws IOException {
        String originalFileName = StringUtils.cleanPath(
                Objects.requireNonNull(multipartFile.getOriginalFilename())
        );

        // Validate filename
        if (originalFileName.contains("..")) {
            throw new IllegalArgumentException("Invalid file path");
        }

        if (!originalFileName.contains(".")) {
            throw new IllegalArgumentException("File must have extension");
        }

        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String newFile = newFileName + fileExtension;

        Path targetDir = UPLOAD_DIR.resolve(uploadPath).normalize();
        Files.createDirectories(targetDir);

        Path filePath = targetDir.resolve(newFile).normalize();

        // Prevent path traversal
        if (!filePath.startsWith(UPLOAD_DIR)) {
            throw new IllegalArgumentException("Invalid file path");
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return newFile;
    }

    // ================= GET FILE =================
    public static File getFileByPath(String pathFile) {
        Path path = UPLOAD_DIR.resolve(pathFile).normalize();
        return path.toFile();
    }

    // ================= GET FILE BYTES =================
    public static byte[] getBytesFileByPath(String pathFile) throws IOException {
        Path path = UPLOAD_DIR.resolve(pathFile).normalize();
        return Files.readAllBytes(path);
    }

    // ================= CHECK FOLDER =================
    public static boolean isFolderNotExists(String folderPath) {
        Path path = UPLOAD_DIR.resolve(folderPath);
        return !Files.exists(path);
    }

    // ================= ZIP FILE =================
    public static byte[] zipFileByPath(String... filePaths) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {

            for (String path : filePaths) {
                File file = getFileByPath(path);
                if (file.exists()) {
                    addToZip(UPLOAD_DIR, zos, file);
                }
            }

            return baos.toByteArray();
        }
    }

    // ================= ZIP FOLDER =================
    public static byte[] zipFolder(String... folderPaths) throws IOException {
        List<File> folders = new ArrayList<>();

        for (String folderPath : folderPaths) {
            File folder = UPLOAD_DIR.resolve(folderPath).toFile();
            if (folder.exists()) {
                folders.add(folder);
            }
        }

        if (folders.isEmpty()) {
            throw new IllegalArgumentException("No folder found to zip");
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {

            for (File folder : folders) {
                addToZip(UPLOAD_DIR, zos, folder);
            }

            return baos.toByteArray();
        }
    }

    // ================= ADD TO ZIP =================
    private static void addToZip(Path basePath, ZipOutputStream zos, File file) throws IOException {
        Path filePath = file.toPath();

        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    addToZip(basePath, zos, child);
                }
            }
        } else {
            String zipEntryName = basePath.relativize(filePath).toString().replace("\\", "/");

            ZipEntry entry = new ZipEntry(zipEntryName);
            zos.putNextEntry(entry);

            Files.copy(filePath, zos);

            zos.closeEntry();
        }
    }
}