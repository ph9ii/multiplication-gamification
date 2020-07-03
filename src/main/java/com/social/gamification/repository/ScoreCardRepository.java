package com.social.gamification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.social.gamification.domain.ScoreCard;
import com.social.gamification.domain.LeaderBoardRow;

/**
 * Handles CRUD operations with ScoreCards
 * 
 * @author mocha
 *
 */
public interface ScoreCardRepository extends CrudRepository<ScoreCard, Long> {

	/**
	 * Gets the total score of a given User, being the sum of the scores of all his
	 * ScoreCards
	 * 
	 * @param userId the id of the user for which total score should be retrieved
	 * @return the total score for the given user
	 */
	@Query("SELECT SUM(s.score) FROM com.social.gamification.domain.ScoreCard s WHERE s.userId = :userId GROUP BY s.userId")
	int getTotalScoreForUser(@Param("userId") final Long userId);

	/**
	 * Retrieves a list of {@link LeaderBoardRow} representing the Leader Board of
	 * users and their total score.
	 * 
	 * @return the leader board, sorted by the highest score first.
	 */
	@Query("SELECT NEW com.social.gamification.domain.LeaderBoardRow(s.userId, SUM(s.score)) "
			+ "FROM com.social.gamification.ScoreCard s " + "GROUP BY s.userId ORDER BY sum(s.score) DESC")
	List<LeaderBoardRow> findFirst10();
	
	/**
	 * Retrieve all ScoreCards for a given users,
	 * identified by his user id.
	 * 
	 * @param userId the id of the user
	 * @return a list containing all of the ScoreCards for the given user, sorted by most recent
	 */
	List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);
}
