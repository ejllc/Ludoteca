package com.ccsv.tutorialloan.game;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.ccsv.tutorialloan.game.model.GameDto;

/**
 * @author emlloren
 *
 */
@FeignClient(value = "SPRING-CLOUD-EUREKA-CLIENT-GAME", url = "http://localhost:8080")
public interface GameClient {

	@GetMapping(value = "/game")
	List<GameDto> findAll();

}
