package com.social.gamification.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.social.gamification.domain.Badge;
import com.social.gamification.domain.BadgeCard;
import com.social.gamification.domain.GameStats;
import com.social.gamification.domain.ScoreCard;
import com.social.gamification.repository.BadgeCardRepository;
import com.social.gamification.repository.ScoreCardRepository;

/**
 * 
 * @author mocha
 *
 */
public class GameServiceImplTest {

	private GameServiceImpl gameService;

	@Mock
	private ScoreCardRepository scoreCardRepository;

	@Mock
	private BadgeCardRepository badgeCardRepository;

	@Before
	public void setUp() {
		// With this call, we tell Mockito to process the annotaions
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void processFirstCorrectAttemptTest() {
		// given
		Long userId = 1L;
		Long attemptId = 8L;
		int totalScore = 10;
		ScoreCard scoreCard = new ScoreCard(userId, attemptId);
		given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.willReturn(Collections.singletonList(scoreCard));
		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(Collections.emptyList());

		// when
		GameStats iteration = gameService.newAttemptForUser(userId, attemptId, true);

		// then (assert) - should score one card and win the FIRST_WON badge
		assertThat(iteration.getScore()).isEqualTo(scoreCard.getScore());
		assertThat(iteration.getBadges()).containsOnly(Badge.FIRST_WON);
	}

	@Test
	public void processCorrectAttemptForScoreBadgeTest() {
		// given
		Long userId = 1L;
		Long attemptId = 29L;
		int totalScore = 100;

		BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
		given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
		// this repository will return the just-won score card
		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.willReturn(createNScoreCards(10, userId));
		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
				.willReturn(Collections.singletonList(firstWonBadge));

		// when
		GameStats iteration = gameService.newAttemptForUser(userId, attemptId, true);

		// assert - should score one card and win the badge bronze
		assertThat(iteration.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
		assertThat(iteration.getBadges()).containsOnly(Badge.BRONZE_MULTIPLICATOR);
	}

	@Test
	public void processWrongAttemptTest() {
		// given
		Long userId = 1L;
		Long attemptId = 8L;
		int totalScore = 10;
		ScoreCard scoreCard = new ScoreCard(userId, attemptId);
		given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.willReturn(Collections.singletonList(scoreCard));
		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(Collections.emptyList());

		// when
		GameStats iteration = gameService.newAttemptForUser(userId, attemptId, false);

		// then (assert) - shouldn't score anything
		assertThat(iteration.getScore()).isEqualTo(0);
		assertThat(iteration.getBadges()).isEmpty();
		;
	}

	private List<ScoreCard> createNScoreCards(int n, Long userId) {
		return IntStream.range(0, n).mapToObj(i -> new ScoreCard(userId, (long) i)).collect(Collectors.toList());
	}
}
