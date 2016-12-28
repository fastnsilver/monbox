package com.fns.monbox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fns.monbox.repository.CloudVirtualNetworkRepository;

@RestController
public class CloudVirtualNetworkController {

	private CloudVirtualNetworkRepository virtualNetworkRepository;
	
	@Autowired
	public CloudVirtualNetworkController(CloudVirtualNetworkRepository virtualNetworkRepository) {
		this.virtualNetworkRepository = virtualNetworkRepository;
	}
	
	@GetMapping("/virtualNetwork")
	public ResponseEntity<?> virtualNetworks(@RequestParam(name = "all", required = true) String all) {
		return ResponseEntity.ok(virtualNetworkRepository.findAll());
	}
}
