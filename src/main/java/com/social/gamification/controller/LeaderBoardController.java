package com.social.gamification.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.gamification.domain.LeaderBoardRow;
import com.social.gamification.service.LeaderBoardService;

/**
 * This class implements REST API for the Gamification LeaderBoard service.
 * 
 * @author mocha
 *
 */
@RestController
@RequestMapping("/leaders")
public class LeaderBoardController {

	private final LeaderBoardService leaderBoardService;

	public LeaderBoardController(LeaderBoardService leaderBoardService) {
		this.leaderBoardService = leaderBoardService;
	}

	@GetMapping
	public List<LeaderBoardRow> getLeaderBoard() {
		return leaderBoardService.getCurrentLeaderBoard();
	}
}
