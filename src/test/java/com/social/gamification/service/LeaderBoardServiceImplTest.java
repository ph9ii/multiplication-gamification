package com.social.gamification.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.social.gamification.domain.LeaderBoardRow;
import com.social.gamification.repository.ScoreCardRepository;

/**
 * 
 * @author mocha
 *
 */
public class LeaderBoardServiceImplTest {

	private LeaderBoardServiceImpl leaderBoardService;

	@Mock
	ScoreCardRepository scoreCardRepository;

	@Before
	public void setUp() {
		// With this call, we tell Mockito to process the annotaions
		MockitoAnnotations.initMocks(this);
		leaderBoardService = new LeaderBoardServiceImpl(scoreCardRepository);
	}

	@Test
	public void retrieveLeaderBoardTest() {
		// given
		Long userId = 1L;
		LeaderBoardRow leaderRow1 = new LeaderBoardRow(userId, 300L);
		List<LeaderBoardRow> expectedLeaderBoard = Collections.singletonList(leaderRow1);
		given(scoreCardRepository.findFirst10()).willReturn(expectedLeaderBoard);

		// when
		List<LeaderBoardRow> leaderBoard = leaderBoardService.getCurrentLeaderBoard();

		// assert(Then)
		assertThat(leaderBoard).isEqualTo(expectedLeaderBoard);
	}
}
