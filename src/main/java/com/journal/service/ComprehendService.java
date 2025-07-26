package com.journal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

import software.amazon.awssdk.services.comprehend.ComprehendClient;
import software.amazon.awssdk.services.comprehend.model.DetectSentimentRequest;

@Service
public class ComprehendService {
    @Value("${aws.s3.bucket}")
    private String bucketName;

    private final S3Client s3Client;
    private final ComprehendClient comprehendClient;

    public ComprehendService(S3Client s3Client, ComprehendClient comprehendClient) {
        this.s3Client = s3Client;
        this.comprehendClient = comprehendClient;
    }

    public String readFromBucket(String key, String bucketName) {
        return s3Client.getObjectAsBytes(
                software.amazon.awssdk.services.s3.model.GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build()
        ).asUtf8String();
    }

    public String analyzeSentiment(String text) {
        DetectSentimentRequest request = DetectSentimentRequest.builder()
                .text(text)
                .languageCode("en")
                .build();
        return comprehendClient.detectSentiment(request).sentimentAsString();
    }
}
