package com.fns.monbox.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fns.monbox.model.CloudInstance;
import com.fns.monbox.model.NameValue;

public interface CloudInstanceRepository extends CrudRepository<CloudInstance, String> {

    CloudInstance findByInstanceId(String instanceId);

    Collection<CloudInstance> findByTags(List<NameValue> tags);

    Collection<CloudInstance> findByInstanceIdIn(List<String> instanceId);

    Collection<CloudInstance> findByAccountNumber(String accountNumber);

}
