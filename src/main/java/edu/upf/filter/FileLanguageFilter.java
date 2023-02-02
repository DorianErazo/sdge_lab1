package edu.upf.filter;

import java.io.*;
import java.util.Optional;

import edu.upf.parser.SimplifiedTweet;

public class FileLanguageFilter {
    final String inputFile;
    final String outputFile;
    int outputFileSize;

    public FileLanguageFilter(String inFile, String outFile) {
        this.inputFile = inFile;
        this.outputFile = outFile;
        outputFileSize = 0;
    }

    public void filterLanguage(String language) throws Exception {
        String input = this.getInputFile();
        try (FileReader fr = new FileReader(input); BufferedReader br = new BufferedReader(fr);) {
            FileWriter fw = new FileWriter(outputFile);
            BufferedWriter bw = new BufferedWriter(fw);
            String line = br.readLine(); // Read one line of content
            while (line != null) {
                Optional<SimplifiedTweet> parsedLine = SimplifiedTweet.fromJson(line);
                Long displayTweetId = parsedLine.map(SimplifiedTweet::getTweetId).orElse(null);
                String displayText = parsedLine.map(SimplifiedTweet::getText).orElse(null);
                Long displayUserId = parsedLine.map(SimplifiedTweet::getUserId).orElse(null);
                String displayName = parsedLine.map(SimplifiedTweet::getUserName).orElse(null);
                String displayLang = parsedLine.map(SimplifiedTweet::getLanguage).orElse(null);
                Long displayTimestampMs = parsedLine.map(SimplifiedTweet::getTimestampMs).orElse(null);
                if (displayName != null && displayLang.equals(language)) {
                    String simpletweet = "TweetId: " + displayTweetId + " Text: " + displayText + " UserId: " + displayUserId + " Name: " + displayName + " lang: " + displayLang + " TimestampMS: " + displayTimestampMs + "\n";
                    bw.write(simpletweet);
                }
                line = br.readLine(); // Read next line of content
            }

            // Close input file
            br.close();
            bw.close();
        } // Resources will be automatically closed
    }

    public String getInputFile() {
        return this.inputFile;
    }

    public String getOutputFile() {
        return this.outputFile;
    }

    public int getOutputFileSize(){
        return outputFileSize;
    }

}
