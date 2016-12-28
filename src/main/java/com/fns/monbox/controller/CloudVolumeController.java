package com.fns.monbox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fns.monbox.repository.CloudVolumeRepository;

@RestController
public class CloudVolumeController {

	private CloudVolumeRepository volumeRepository;
	
	@Autowired
	public CloudVolumeController(CloudVolumeRepository volumeRepository) {
		this.volumeRepository = volumeRepository;
	}
	
	@GetMapping("/volume")
	public ResponseEntity<?> volumes(@RequestParam(name = "all", required = true) String all) {
		return ResponseEntity.ok(volumeRepository.findAll());
	}
}
