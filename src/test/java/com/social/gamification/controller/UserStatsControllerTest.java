package com.social.gamification.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.gamification.domain.Badge;
import com.social.gamification.domain.GameStats;
import com.social.gamification.service.GameService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserStatsController.class)
public class UserStatsControllerTest {

	@MockBean
	private GameService gameService;

	@Autowired
	private MockMvc mvc;

	// this object will be magically initialized by the iniFields method below
	private JacksonTester<GameStats> json;

	@Before
	public void setUp() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void getStatsForUserTest() throws Exception {
		// given
		GameStats gameStats = new GameStats(1L, 2000, Collections.singletonList(Badge.GOLD_MULTIPLICATOR));
		given(gameService.retrieveStatsForUser(1L)).willReturn(gameStats);

		// when
		MockHttpServletResponse response = mvc.perform(get("/stats").accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// assert
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(json.write(gameStats).getJson());
	}
}
