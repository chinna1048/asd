package com.genfare.reprocess.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

@Configuration
public class AmazonConfig {
	
	@Autowired
	Environment env;
	
	@SuppressWarnings("deprecation")
	@Bean
	public AmazonS3 amazonS3() {
		return new AmazonS3Client(awsCredentials());
	}
	
	@Bean
	public AWSCredentials awsCredentials() {
		String accessKeyId = env.getProperty("ilibrary.aws.accessKeyId");
		String secretKey = env.getProperty("ilibrary.aws.secretKey");
		AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretKey);
		return credentials;
	}
	
	
	public static void amazonSQS() {
		AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
		SendMessageRequest send_msg_request = new SendMessageRequest()
		        .withQueueUrl("")
		        .withMessageBody("hello world")
		        .withDelaySeconds(5);
		sqs.sendMessage(send_msg_request);
	}
}
