package com.social.gamification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.social.gamification.domain.Badge;
import com.social.gamification.domain.BadgeCard;
import com.social.gamification.domain.GameStats;
import com.social.gamification.domain.ScoreCard;
import com.social.gamification.repository.BadgeCardRepository;
import com.social.gamification.repository.ScoreCardRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GameServiceImpl implements GameService {

	private ScoreCardRepository scoreCardRepository;
	private BadgeCardRepository badgeCardRepository;

	public GameServiceImpl(ScoreCardRepository scoreCardRepository, BadgeCardRepository badgeCardRepository) {
		this.scoreCardRepository = scoreCardRepository;
		this.badgeCardRepository = badgeCardRepository;
	}

	@Override
	public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) {

		// First version we will give points only if correct
		if (correct) {
			ScoreCard scoreCard = new ScoreCard(userId, attemptId);
			scoreCardRepository.save(scoreCard);
			log.info("User with id {} scored {} points for attempt id {}", userId, scoreCard.getScore(), attemptId);
			List<BadgeCard> badgeCards = processForBadges(userId, attemptId);
			return new GameStats(userId, scoreCard.getScore(),
					badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
		}
		return GameStats.emptyStats(userId);
	}

	@Override
	public GameStats retrieveStatsForUser(Long userId) {
		int score = scoreCardRepository.getTotalScoreForUser(userId);
		List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
		return new GameStats(userId, score, badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
	}

	/**
	 * Check the total score and the different ScoreCards obtained to give new
	 * badges in case their conditions are met.
	 * 
	 * @param userId
	 * @param attemptId
	 * @return
	 */
	private List<BadgeCard> processForBadges(final Long userId, final Long attemptId) {
		List<BadgeCard> badgeCards = new ArrayList<>();
		int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
		log.info("New score for User {} is {}", userId, totalScore);
		List<ScoreCard> scoreCardList = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
		List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);

		// Badges depending on score
		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.BRONZE_MULTIPLICATOR, totalScore, 100, userId)
				.ifPresent(badgeCards::add);
		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.SILVER_MULTIPLICATOR, totalScore, 500, userId)
				.ifPresent(badgeCards::add);
		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.GOLD_MULTIPLICATOR, totalScore, 999, userId)
				.ifPresent(badgeCards::add);

		// First won badge
		if (scoreCardList.size() == 1 && !containsBadge(badgeCards, Badge.FIRST_WON)) {
			BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, userId);
			log.info("User of id {} won his first badge", userId);
			badgeCards.add(firstWonBadge);
		}
		return badgeCards;
	}

	/**
	 * Convenience method to check the current score against the different
	 * thresholds to get badges. Also assigns badge to user if conditions are met.
	 * 
	 * @param badgeCards
	 * @param badge
	 * @param score
	 * @param scoreThreshold
	 * @param userId
	 * @return
	 */
	private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(final List<BadgeCard> badgeCards, final Badge badge,
			final int score, final int scoreThreshold, final Long userId) {
		if (score >= scoreThreshold && !containsBadge(badgeCards, badge)) {
			return Optional.of(giveBadgeToUser(badge, userId));
		}
		return Optional.empty();
	}

	/**
	 * Assigns a new badge for the given user
	 * 
	 * @param badge
	 * @param userId
	 * @return
	 */
	private BadgeCard giveBadgeToUser(Badge badge, Long userId) {
		BadgeCard badgeCard = new BadgeCard(userId, badge);
		badgeCardRepository.save(badgeCard);
		log.info("User with id {} win a new badge: {}", userId, badge);
		return badgeCard;
	}

	/**
	 * Checks if the based list of badges already includes the one being checked.
	 * 
	 * @param badgeCards
	 * @param badge
	 * @return
	 */
	private boolean containsBadge(List<BadgeCard> badgeCards, Badge badge) {
		return badgeCards.stream().anyMatch(b -> b.getBadge().equals(badge));
	}

}
