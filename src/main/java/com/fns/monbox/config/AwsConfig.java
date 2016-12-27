package com.fns.monbox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.amazonaws.services.autoscaling.AmazonAutoScalingClient;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;

@Configuration
public class AwsConfig {

	// @see https://github.com/spring-cloud/spring-cloud-aws/issues/28 to understand the purpose of this configuration
	@Profile("local")
	@Configuration
	protected static class LocalBoostrap {
		
		@Bean
		public AmazonEC2 ec2Client(AWSCredentialsProvider awsCredentialsProvider) {
			return new AmazonEC2Client(awsCredentialsProvider);
		}
		
		@Bean
		public AmazonCloudWatch cloudWatchClient(AWSCredentialsProvider awsCredentialsProvider) {
			return new AmazonCloudWatchClient(awsCredentialsProvider);
		}
		
		@Bean
		public AmazonAutoScaling autoscalingClient(AWSCredentialsProvider awsCredentialsProvider) {
			return new AmazonAutoScalingClient(awsCredentialsProvider);
		}
		
	}
	
}
