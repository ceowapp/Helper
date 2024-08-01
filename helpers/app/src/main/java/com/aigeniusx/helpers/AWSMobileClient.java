package com.aigeniusx.helpers;

// Initialize the AWS Mobile Client
AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
@Override
public void onResult(UserStateDetails result) {
        // Your initialization was successful
        }

@Override
public void onError(Exception e) {
        // Your initialization failed
        }
        });


// Create an S3 client
        AmazonS3Client s3Client = new AmazonS3Client(AWSMobileClient.getInstance());

// Specify the bucket name and object key
        String bucketName = "your-bucket-name";
        String objectKey = "your-object-key";

// Create a file object to upload
        File fileToUpload = new File("path-to-your-file");

// Upload the file to S3
        s3Client.putObject(new PutObjectRequest(bucketName, objectKey, fileToUpload));
