Group 6 - P201
Dorian Erazo - 219395
Guillem Gauchia - 240215
Àlex Herrero - 240799

Instructions:
To execute the program you will need to do the following:
1. mvn package
2. java -cp target/lab1.jar edu.upf.TwitterFilter arg1 arg2 ... argN 
    [arg1 -> language] [arg2 -> path for output local file] [arg3 -> s3://bucket_name/path/file] [argN -> Json tweet files]

*Be aware that the jar file is created in the target folder*

Answers:
Does the bucket exist? What happens if it doesn’t?
The program first checks if the bucket exists, if not it would notice it and stop the execution.