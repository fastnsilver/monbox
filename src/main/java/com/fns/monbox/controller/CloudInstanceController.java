package com.fns.monbox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fns.monbox.repository.CloudInstanceRepository;

@RestController
public class CloudInstanceController {

	private CloudInstanceRepository cloudInstanceRepository;
	
	@Autowired
	public CloudInstanceController(CloudInstanceRepository cloudInstanceRepository) {
		this.cloudInstanceRepository = cloudInstanceRepository;
	}
	
	@GetMapping("/cloudInstance")
	public ResponseEntity<?> cloudInstances(@RequestParam(name = "all", required = true) String all) {
		return ResponseEntity.ok(cloudInstanceRepository.findAll());
	}
}
