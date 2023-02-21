package com.mews.mews_backend.global.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

    @Value("${cloud.aws.credentials.access-key}")
    private String iamAccessKey; // IAM Access Key

    @Value("${cloud.aws.credentials.secret-key}")
    private String iamSecretKey; // IAM Secret Key

    @Value("${cloud.aws.region.static}")
    private String region; // Bucket Region

    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(iamAccessKey, iamSecretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
//                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
//                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .build();
    }
}