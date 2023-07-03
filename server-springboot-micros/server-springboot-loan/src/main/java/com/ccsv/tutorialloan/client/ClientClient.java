package com.ccsv.tutorialloan.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.ccsv.tutorialloan.client.model.ClientDto;

/**
 * @author emlloren
 *
 */
@FeignClient(value = "SPRING-CLOUD-EUREKA-CLIENT-CLIENT", url = "http://localhost:8080")
public interface ClientClient {

	@GetMapping(value = "/client")
	List<ClientDto> findAll();

}
