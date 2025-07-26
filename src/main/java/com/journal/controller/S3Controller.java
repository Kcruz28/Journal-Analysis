package com.journal.controller;

import com.journal.service.S3Service;
import com.journal.service.ComprehendService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/s3")
public class S3Controller {
    @Autowired
    private S3Service s3Service;
    @Autowired
    private ComprehendService comprehendService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadText(@RequestBody String text) {
        if (text == null || text.trim().isEmpty()) {
            return new ResponseEntity<>("Text body must not be empty", HttpStatus.BAD_REQUEST);
        }
        String result = s3Service.uploadText(text);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/read")
    public ResponseEntity<String> readFromBucket(@RequestParam String key, @RequestParam String bucketName) {
        if (key == null || key.trim().isEmpty()) {
            return new ResponseEntity<>("Key must not be empty", HttpStatus.BAD_REQUEST);
        }
        String result = comprehendService.readFromBucket(key, bucketName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeSentiment(@RequestBody String text) {
        if (text == null || text.trim().isEmpty()) {
            return new ResponseEntity<>("Text body must not be empty", HttpStatus.BAD_REQUEST);
        }
        String result = comprehendService.analyzeSentiment(text);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
}
