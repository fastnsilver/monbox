package com.fns.monbox.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fns.monbox.model.CloudSubNetwork;
import com.fns.monbox.model.NameValue;

public interface CloudSubNetworkRepository extends CrudRepository<CloudSubNetwork, String> {

    Collection<CloudSubNetwork> findByTags(List<NameValue> tags);

    CloudSubNetwork findBySubnetId(String subnetId);

    Collection<CloudSubNetwork> findBySubnetIdIn(List<String> subnetId);

    Collection<CloudSubNetwork> findByAccountNumber(String accountNumber);

}
