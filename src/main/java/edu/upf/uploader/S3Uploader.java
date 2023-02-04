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
    //final String profile;
    final AmazonS3 Client;

    public S3Uploader(String bucket, String prefix, String Client){
        /*
        ------------ Alex y Guillem ------------
        s3://name_bucket/some/prefix/file
        bucket : name_bucket
        prefix : some/prefix
        */
        String raw_bucket[] = bucket.split("/");
        this.bucket = raw_bucket[2];
        String prefix_list[] = Arrays.copyOfRange(raw_bucket, 3, raw_bucket.length-1);
        this.prefix = String.join("/", prefix_list);
        //this.profile = profile;

        System.out.println("Bucket: " + this.bucket + " Prefix: " + this.prefix);
        /*---*/
        //Para implementar esto necesitamos 3 variables. Las informacion la saco de 
        //https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/s3/AmazonS3ClientBuilder.html
        // https://github.com/awsdocs/aws-doc-sdk-examples/tree/main/java/example_code/s3/src/main/java/aws/example/s3

        //this.bucket = bucket;
        //this.prefix = prefix;

        AWSCredentials credentials = new BasicAWSCredentials("ASIAZHVQRA4MD5ISUVB6", "9CjsKFrxFRCi15QsbZ9Ad2GfGacpo6Zbo0JwkcMV");

            
        // Create an instance of an AmazonS3 with the previous credentials
        this.Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1).build();

        //this.Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

    }

    @Override
    public void upload(List<String> files) {
        for (String file : files) {
            System.out.format("Uploading file: %s, to AWS S3 bucket: %s.\n", file, bucket);
            try {
                Client.putObject(bucket, file, new File(file));
            } catch (AmazonServiceException e) {
                System.err.println(e.getErrorMessage());
                System.exit(1);
            }

        }
    }
    /*
        ------------ Alex y Guillem ------------
    
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

    */
    public String getBucket() {
        return bucket;
    }
    public String getPrefix() {
        return prefix;
    }
    /*
    public String getProfile() {
        return profile;
    }
    */
}
