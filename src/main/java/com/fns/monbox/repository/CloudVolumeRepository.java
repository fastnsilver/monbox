package com.fns.monbox.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fns.monbox.model.CloudVolumeStorage;
import com.fns.monbox.model.NameValue;

public interface CloudVolumeRepository extends CrudRepository<CloudVolumeStorage, String> {

    CloudVolumeStorage findByVolumeId(String volumeId);

    Collection<CloudVolumeStorage> findByTags(List<NameValue> tags);

    Collection<CloudVolumeStorage> findByVolumeIdIn(List<String> volumeId);

    Collection<CloudVolumeStorage> findByAttachInstancesIn(List<String> attachInstances);

    Collection<CloudVolumeStorage> findByAccountNumber(String accountNumber);

}
