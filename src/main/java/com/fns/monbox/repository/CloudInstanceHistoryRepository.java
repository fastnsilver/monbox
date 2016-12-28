package com.fns.monbox.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import com.fns.monbox.model.CloudInstanceHistory;

public interface CloudInstanceHistoryRepository extends CrudRepository<CloudInstanceHistory, String> {

    Collection<CloudInstanceHistory> findByAccountNumber(String accountNumber);

}
