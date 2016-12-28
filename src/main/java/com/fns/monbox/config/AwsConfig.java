package com.fns.monbox.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
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
	protected static class LocalBootstrap {
		
		@Value("${cloud.aws.region.static}") 
		private String region;
		
		@Bean
		public AmazonEC2 ec2Client(AWSCredentialsProvider awsCredentialsProvider) {
			AmazonEC2Client ec2Client = new AmazonEC2Client(awsCredentialsProvider);
			ec2Client.configureRegion(Regions.fromName(region));
			return ec2Client;
		}
		
		@Bean
		public AmazonCloudWatch cloudWatchClient(AWSCredentialsProvider awsCredentialsProvider) {
			AmazonCloudWatchClient cloudWatchClient = new AmazonCloudWatchClient(awsCredentialsProvider);
			cloudWatchClient.configureRegion(Regions.fromName(region));
			return cloudWatchClient;
		}
		
		@Bean
		public AmazonAutoScaling autoscalingClient(AWSCredentialsProvider awsCredentialsProvider) {
			AmazonAutoScalingClient autoScalingClient = new AmazonAutoScalingClient(awsCredentialsProvider);
			autoScalingClient.configureRegion(Regions.fromName(region));
			return autoScalingClient;
		}
		
	}
	
}
