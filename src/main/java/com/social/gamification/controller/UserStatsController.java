package com.social.gamification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.social.gamification.domain.GameStats;
import com.social.gamification.service.GameService;

/**
 * This class implements REST API for Gamification User statistics.
 * 
 * @author mocha
 *
 */
@RestController
@RequestMapping("/stats")
public class UserStatsController {

	private final GameService gameService;

	public UserStatsController(GameService gameService) {
		this.gameService = gameService;
	}
	
	@GetMapping
	public GameStats getStatsForUser(@RequestParam("userId") final Long userId) {
		return gameService.retrieveStatsForUser(userId);
	}
}
