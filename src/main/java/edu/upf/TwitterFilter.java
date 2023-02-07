package edu.upf;

import edu.upf.filter.FileLanguageFilter;
import edu.upf.uploader.S3Uploader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TwitterFilter {
    public static void main( String[] args ) throws IOException {
        long startTime = System.currentTimeMillis();
        int outputSize = 0;
        boolean append_file = false; // variable to control when the program has to append the contents or not.

        List<String> argsList = Arrays.asList(args);
        String language = argsList.get(0);
        String outputFile = argsList.get(1);
        String bucket = argsList.get(2);
        System.out.println("Language: " + language + ". Output file: " + outputFile + ". Destination bucket: " + bucket);
        for(String inputFile: argsList.subList(3, argsList.size())) {
            System.out.println("Processing: " + inputFile);
            // Creates an instance of a FileLanguageFilter and filters every tweet for each file
            final FileLanguageFilter filter = new FileLanguageFilter(inputFile, outputFile);
            try{
                filter.filterLanguage(language, append_file);
                append_file = true;
                outputSize += filter.getOutputFileSize();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        // When the final output is finished we upload it to the AWS S3 bucket
        final S3Uploader uploader = new S3Uploader(bucket, "prefix", "upf");
        uploader.upload(Arrays.asList(outputFile));

        // Variables para el benchmark
        long endTime = System.currentTimeMillis();
        long time =  endTime - startTime;

        System.out.println("In language " + language + " has filtered " + outputSize + ". And the execution time is " + time + " ms.");
    }
}
