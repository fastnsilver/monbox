package com.fns.monbox.repository;

import com.fns.monbox.model.CloudInstance;
import com.fns.monbox.model.CloudVirtualNetwork;
import com.fns.monbox.model.NameValue;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface CloudVirtualNetworkRepository extends
        CrudRepository<CloudVirtualNetwork, ObjectId>,
        QueryDslPredicateExecutor<CloudInstance> {

    @Query(value = "{ collectorItemId:  ?0 }")
    CloudVirtualNetwork findByCollectorItemId(ObjectId collectorItemId);

    @Query(value= "{ tags: ?0 }")
    Collection<CloudVirtualNetwork> findByTags(List<NameValue> tags);


    @Query(value = "{virtualNetorkId : ?0}")
    CloudVirtualNetwork findByVirtualNetworkId(String virtualNetworkId);

    @Query(value = "{ 'tags.name' : ?0, 'tags.value' : ?1 }")
    Collection<CloudVirtualNetwork> findByTagNameAndValue(String name, String value);

    Collection<CloudVirtualNetwork> findByvirtualNetworkIdIn(List<String> virtualNetworkId);

    @Query(value = "{accountNumber : ?0}")
    Collection<CloudVirtualNetwork> findByAccountNumber(String accountNumber);

}
