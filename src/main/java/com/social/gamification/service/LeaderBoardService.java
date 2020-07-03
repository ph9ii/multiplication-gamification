package com.social.gamification.service;

import java.util.List;

import com.social.gamification.domain.LeaderBoardRow;

/**
 * Provides method to access the LeaderBoard with Users and Scores.
 * @author mocha
 *
 */
public interface LeaderBoardService {
	
	/**
	 * Retrieves the current LeaderBoard with the top score users.
	 * @return the users with the highest score
	 */
	List<LeaderBoardRow> getCurrentLeaderBoard();

}
