package com.springboot.tutorial.controllers;

import com.springboot.tutorial.models.ResponseObject;
import com.springboot.tutorial.services.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/uploadfile")
public class FileUploadController {
    @Autowired
    private IStorageService storageService;
    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file)
    {
        try
        {
            String generatedFileName = storageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","File uploaded successfully", generatedFileName));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed", e.getMessage(), ""));
        }

    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<byte[]> getFile(@PathVariable String filename)
    {
        try
        {
            byte[] fileContent = storageService.readFileContent(filename);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(fileContent);
        }
        catch (Exception e)
        {
            return ResponseEntity.noContent().build();
        }
    }
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllFiles()
    {
        try
        {
           List<String> urls= storageService.loadAll().map(
                    path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                            "getFile", path.getFileName().toString()).build().toString())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Files retrieved successfully", urls));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }
}
