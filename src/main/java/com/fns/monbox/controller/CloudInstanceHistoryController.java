package com.fns.monbox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fns.monbox.repository.CloudInstanceHistoryRepository;

@RestController
public class CloudInstanceHistoryController {

	private CloudInstanceHistoryRepository cloudInstanceHistoryRepository;
	
	@Autowired
	public CloudInstanceHistoryController(CloudInstanceHistoryRepository cloudInstanceHistoryRepository) {
		this.cloudInstanceHistoryRepository = cloudInstanceHistoryRepository;
	}
	
	@GetMapping("/cloudInstance/history")
	public ResponseEntity<?> cloudInstances(@RequestParam(name = "all", required = true) String all) {
		return ResponseEntity.ok(cloudInstanceHistoryRepository.findAll());
	}
}
