package com.social.gamification.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.social.gamification.domain.BadgeCard;

/**
 * Handles data operations with BadgeCards
 * @author mocha
 *
 */
public interface BadgeCardRepository extends CrudRepository<BadgeCard, Long> {
	
	/**
	 * Retrieve all BadgeCards for a given user.
	 * @param userId the id of the user to look for BadgeCards
	 * @return the list of BadgeCards, sorted by the most recent
	 */
	List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(final Long userId);
}
