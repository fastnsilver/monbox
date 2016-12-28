package com.fns.monbox.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fns.monbox.model.CloudVirtualNetwork;
import com.fns.monbox.model.NameValue;

public interface CloudVirtualNetworkRepository extends CrudRepository<CloudVirtualNetwork, String> {

    CloudVirtualNetwork findByCollectorItemId(String collectorItemId);

    Collection<CloudVirtualNetwork> findByTags(List<NameValue> tags);

    CloudVirtualNetwork findByVirtualNetworkId(String virtualNetworkId);

    Collection<CloudVirtualNetwork> findByvirtualNetworkIdIn(List<String> virtualNetworkId);

    Collection<CloudVirtualNetwork> findByAccountNumber(String accountNumber);

}
