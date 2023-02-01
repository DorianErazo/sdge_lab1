package edu.upf.uploader;

import java.util.List;
import java.util.Arrays;
import java.io.*;
import com.amazonaws.services.s3.*;

public class S3Uploader implements Uploader {
    final String bucket;
    final String prefix;
    final String profile;

    public S3Uploader(String bucket, String prefix, String profile){
        String raw_bucket[] = bucket.split("/");
        this.bucket = raw_bucket[2];
        String prefix_list[] = Arrays.copyOfRange(raw_bucket, 3, raw_bucket.length-1);
        this.prefix = String.join("/", prefix_list);
        this.profile = profile;

        System.out.println("Bucket: " + this.bucket + " Prefix: " + this.prefix);




    }

    @Override
    public void upload(List<String> files) {
        // First it creates a Client Connection
        // Credentials are assigned
        AWSCredentials credentials = new BasicAWSCredentials(
            "ASIAZHVQRA4MNAEM7QO6", 
            "P33J/963VyVwCJvaBel01cLhGRXGHYJxMuqEfgaQ"
            );
        // Create an instance of an AmazonS3 with the previous credentials
        AmazonS3 s3client = AmazonS3Builder
            .standard()
            .withCredentials(new AWSStaticCedentialsProvider(credentials))
            .withRegion(Regions.US_EAST_1)
            .build();
        
        s3client.putObject(
            this.getBucket(),
            files[0],
            new File(files[0])
        );

    }

    public String getBucket() {
        return bucket;
    }
    public String getPrefix() {
        return prefix;
    }
    public String getProfile() {
        return profile;
    }
}
