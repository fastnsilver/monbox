package com.fns.monbox.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.core.region.RegionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.amazonaws.services.autoscaling.AmazonAutoScalingClient;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;

@Configuration
public class AwsConfig {

	@Configuration
	protected static class AWSClientsConfiguration {
		
		@Autowired
		private AWSCredentialsProvider credentialsProvider;
		
		@Autowired
		private RegionProvider regionProvider;
		
		@Bean
		public AmazonEC2 ec2Client() {
			AmazonEC2Client ec2Client = new AmazonEC2Client(credentialsProvider);
			ec2Client.setRegion(regionProvider.getRegion());
			return ec2Client;
		}
		
		@Bean
		public AmazonCloudWatch cloudWatchClient() {
			AmazonCloudWatchClient cloudWatchClient = new AmazonCloudWatchClient(credentialsProvider);
			cloudWatchClient.setRegion(regionProvider.getRegion());
			return cloudWatchClient;
		}
		
		@Bean
		public AmazonAutoScaling autoscalingClient() {
			AmazonAutoScalingClient autoScalingClient = new AmazonAutoScalingClient(credentialsProvider);
			autoScalingClient.setRegion(regionProvider.getRegion());
			return autoScalingClient;
		}
		
	}
	
}
