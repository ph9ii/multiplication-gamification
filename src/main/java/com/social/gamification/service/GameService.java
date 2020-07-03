package com.social.gamification.service;

import com.social.gamification.domain.GameStats;

/**
 * This service includes the main login for gamifying the system.
 * @author mocha
 *
 */
public interface GameService {
	
	/**
	 * Process a new attempt from a given user.
	 * 
	 * @param userId the user's unique id
	 * @param attemptId the attempt id, used to retrieve extra data if needed
	 * @param correct  indicates if the attempt was correct
	 * 
	 * @return a {@link GameStats} object containing the new score and the badge cards obtained.
	 */
	GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct);
	
	/**
	 * Get the game statistics for a given user.
	 * 
	 * @param userId the user id
	 * @return the total statistics for a given user
	 */
	GameStats retrieveStatsForUser(Long userId);
}
