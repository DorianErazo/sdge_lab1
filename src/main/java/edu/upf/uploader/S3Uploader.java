package edu.upf.uploader;

//Imports for amazon aws  s3
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

import java.util.List;
import java.util.Arrays;
import java.io.*;
import com.amazonaws.services.s3.*;

public class S3Uploader implements Uploader {
    final String bucket;
    final String prefix;
    final String file_name;
    final AmazonS3 Client;

    public S3Uploader(String bucket, String prefix, String Client){
        /*
        s3://name_bucket/some/prefix/file
        bucket : name_bucket
        prefix : some/prefix/file
        */
        String raw_bucket[] = bucket.split("/");
        this.bucket = raw_bucket[2];
        String prefix_list[] = Arrays.copyOfRange(raw_bucket, 3, raw_bucket.length);
        this.prefix = String.join("/", prefix_list);
        this.file_name = raw_bucket[raw_bucket.length-1];

            
        // Create an instance of an AmazonS3
        this.Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

    }

    @Override
    public void upload(List<String> files) {
        // Uploads each file in the AWS S3 bucket defined
        for (String file : files) {
            System.out.format("Uploading file: %s, to AWS S3 bucket: %s.\n", file, bucket);
            try {
                Client.putObject(bucket, prefix, new File(file));
            } catch (AmazonServiceException e) {
                System.err.println(e.getErrorMessage());
                System.exit(1);
            }

        }
    }
    
    // Basic getters to retrieve class variables
    public String getBucket() {
        return bucket;
    }
    public String getPrefix() {
        return prefix;
    }
    
    public String getFileName() {
        return file_name;
    }
}
