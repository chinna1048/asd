package com.genfare.reprocess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Service
public class TransactionFileReprocessingService {
	
	@Autowired
	AmazonS3Client amazonS3Client;
	
	@Autowired
	Environment env;
	
	public void processTraction() {
		ListObjectsRequest listObjectsRequest = null;
        ObjectListing objectListing = null;
		String bucket = env.getProperty("ilibrary.aws.bucketName");
        String prefix = "cdta/TransactionProcessing/Reprocessing";
        String url = "cdta/TransactionProcessing/ReprocessingComplete";
        listObjectsRequest = new ListObjectsRequest().withBucketName(bucket).withPrefix(prefix);
        objectListing = amazonS3Client.listObjects(listObjectsRequest);
        for (S3ObjectSummary key : objectListing.getObjectSummaries()) {
            String fileName = key.getKey();
            String fileKey = fileName.split("/")[fileName.split("/").length - 1];
            System.out.println("Filekey : " + fileKey);
            //S3Object object = amazonS3Client.getObject(new GetObjectRequest(bucket, fileName));
            //InputStream inputStream = new BufferedInputStream(object.getObjectContent());
            amazonS3Client.copyObject(new CopyObjectRequest(key.getBucketName(), fileName, key.getBucketName(), url));
            amazonS3Client.deleteObject(new DeleteObjectRequest(key.getBucketName(), fileName));
        }
	}
}
