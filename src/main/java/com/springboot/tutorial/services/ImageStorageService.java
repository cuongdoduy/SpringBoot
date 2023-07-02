package com.springboot.tutorial.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageStorageService implements IStorageService{
    private final Path storageFolder = Paths.get("uploads");
    private boolean isImageFile(MultipartFile file)
    {
        String FileExtension= FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"png","jpg","jpeg","gif"}).contains(FileExtension.toLowerCase());
    }
    public ImageStorageService() {
        try
        {
            Files.createDirectories(this.storageFolder);
        }
        catch (IOException exception)
        {
            throw new RuntimeException("Could not create directory to store files", exception);
        }
    }
    @Override
    public String storeFile(MultipartFile file) {
        try
        {
            System.out.println("create file");
            if (file.isEmpty())
            {
                throw new RuntimeException("Failed to store empty file");
            }
            if (!isImageFile(file))
            {
                throw new RuntimeException("Failed to store file, it is not an image file");
            }
            //File must be <= 5MB
            if (file.getSize() > 5 * 1024 * 1024)
            {
                throw new RuntimeException("Failed to store file, it is larger than 5MB");
            }
            //File must be renamed to avoid overwriting existing files
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String newFileName = UUID.randomUUID().toString().replace("-","") + "." + fileExtension;
            Path destination = this.storageFolder.resolve(Paths.get(newFileName)).normalize().toAbsolutePath();
            if (!destination.getParent().equals(this.storageFolder.toAbsolutePath()))
            {
                throw new RuntimeException("Cannot store file outside current directory");
            }
            try (InputStream inputStream = file.getInputStream())
            {
                Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
            }
            return newFileName;
        }
        catch (IOException exception)
        {
            throw new RuntimeException("Failed to store file", exception);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try
        {
            return Files.walk(this.storageFolder, 1)
                    .filter(path -> !path.equals(this.storageFolder))
                    .map(this.storageFolder::relativize);
        }
        catch (IOException exception)
        {
            throw new RuntimeException("Failed to read stored files", exception);
        }
    }

    @Override
    public byte[] load(String filename) {
        return new byte[0];
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try
        {
            Path file = this.storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists()||resource.isReadable())
            {
                byte[] bytes= StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else
            {
                throw new RuntimeException("Could not read file"+fileName);
            }
        }
        catch (IOException exception)
        {
                throw new RuntimeException("Failed to read file"+fileName, exception);
        }
    }

    @Override
    public void deleteAllFiles() {

    }
}
