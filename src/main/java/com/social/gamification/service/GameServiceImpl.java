package com.social.gamification.service;

import org.springframework.stereotype.Service;

import com.social.gamification.domain.GameStats;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameStats retrieveStatsForUser(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
