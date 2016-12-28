package com.fns.monbox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fns.monbox.repository.CloudSubNetworkRepository;

@RestController
public class CloudSubNetworkController {

	private CloudSubNetworkRepository subNetworkRepository;
	
	@Autowired
	public CloudSubNetworkController(CloudSubNetworkRepository subNetworkRepository) {
		this.subNetworkRepository = subNetworkRepository;
	}
	
	@GetMapping("/subNetwork")
	public ResponseEntity<?> subNetworks(@RequestParam(name = "all", required = true) String all) {
		return ResponseEntity.ok(subNetworkRepository.findAll());
	}
}
