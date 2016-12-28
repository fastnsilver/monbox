package com.fns.monbox.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fns.monbox.model.CloudConfig;
import com.google.common.collect.ImmutableMap;

@Repository
public class AWSConfigRepository {

	private BaseCollectorItemRepository<CloudConfig> baseCollectorItemRepository;
	
	@Autowired
	public AWSConfigRepository(BaseCollectorItemRepository<CloudConfig> baseCollectorItemRepository) {
		this.baseCollectorItemRepository = baseCollectorItemRepository;
	}
	
    public CloudConfig findCloudConfig(String collectorId, String accessKey, String secretKey) {
    	Map<String, Object> options = ImmutableMap.of("accessKey", accessKey, "secretKey", secretKey);
    	return baseCollectorItemRepository.findByCollectorIdAndOptions(collectorId, options);
    }

    public List<CloudConfig> findEnabledCloudConfig(String collectorId) {
    	return baseCollectorItemRepository.findByCollectorIdAndEnabled(collectorId, true);
    }
}
