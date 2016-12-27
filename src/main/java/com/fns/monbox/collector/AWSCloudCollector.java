package com.fns.monbox.collector;

import com.fns.monbox.model.Collector;
import com.fns.monbox.model.CollectorType;

/**
 * Collector implementation for Feature that stores system configuration
 * settings required for source system data connection (e.g., API tokens, etc.)
 */
public class AWSCloudCollector extends Collector {
	/**
	 * Creates a static prototype of the Cloud Collector, which includes any
	 * specific settings or configuration required for the use of this
	 * collector, including settings for connecting to any source systems.
	 *
	 * @return A configured Cloud Collector prototype
	 */
	public static AWSCloudCollector prototype() {
		AWSCloudCollector protoType = new AWSCloudCollector();
		protoType.setName("AWSCloud");
		protoType.setOnline(true);
		protoType.setEnabled(true);
		protoType.setCollectorType(CollectorType.Cloud);
		protoType.setLastExecuted(System.currentTimeMillis());
		return protoType;
	}
}
