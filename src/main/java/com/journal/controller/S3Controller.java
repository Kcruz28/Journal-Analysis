package com.journal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.journal.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/s3")
public class S3Controller {
    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadText(@RequestBody String text) {
        if (text == null || text.trim().isEmpty()) {
            return new ResponseEntity<>("Text body must not be empty", HttpStatus.BAD_REQUEST);
        }
        String result = s3Service.uploadText(text);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
