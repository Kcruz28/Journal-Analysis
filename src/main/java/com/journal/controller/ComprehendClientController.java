package com.journal.controller;

import com.journal.service.S3Service;
import com.journal.service.ComprehendService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/text")
public class ComprehendClientController {


    @Autowired
    private ComprehendService comprehendService;

    @GetMapping("/read")
    public ResponseEntity<String> readFromBucket(@RequestParam String key, @RequestParam String bucketName) {
        if (key == null || key.trim().isEmpty()) {
            return new ResponseEntity<>("Key must not be empty", HttpStatus.BAD_REQUEST);
        }
        String result = comprehendService.readFromBucket(key, bucketName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/analyze")
    public ResponseEntity<String> analyzeSentiment(@RequestParam String key, @RequestParam String bucketName) {
        if (key == null || key.trim().isEmpty()) {
            return new ResponseEntity<>("Key must not be empty", HttpStatus.BAD_REQUEST);
        }
        String text = comprehendService.readFromBucket(key, bucketName);
        if (text == null || text.trim().isEmpty()) {
            return new ResponseEntity<>("Text body must not be empty", HttpStatus.BAD_REQUEST);
        }
        String result = comprehendService.analyzeSentiment(text);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
