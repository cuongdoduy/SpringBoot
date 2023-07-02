package com.springboot.tutorial.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService {
    public String storeFile(MultipartFile file);
    public Stream<Path> loadAll();
    public byte[] load(String filename);
    public byte[] readFileContent(String fileName);
    public void deleteAllFiles();
}
