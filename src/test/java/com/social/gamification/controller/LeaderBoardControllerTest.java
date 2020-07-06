package com.social.gamification.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import com.social.gamification.domain.LeaderBoardRow;
import com.social.gamification.service.LeaderBoardService;

@RunWith(SpringRunner.class)
@WebMvcTest(LeaderBoardController.class)
public class LeaderBoardControllerTest {

	@MockBean
	private LeaderBoardService leaderBoardService;

	@Autowired
	private MockMvc mvc;

	// this object will be magically initialized by the iniFields method below
	private JacksonTester<List<LeaderBoardRow>> json;

	@Before
	public void setUp() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void getLeaderBoardTest() throws Exception {
		// given
		LeaderBoardRow leaderRow1 = new LeaderBoardRow(1L, 100L);
		LeaderBoardRow leaderRow2 = new LeaderBoardRow(1L, 200L);
		List<LeaderBoardRow> leaderBoardList = new ArrayList<>();
		Collections.addAll(leaderBoardList, leaderRow1, leaderRow2);
		given(leaderBoardService.getCurrentLeaderBoard()).willReturn(leaderBoardList);
		
		// when
		MockHttpServletResponse response = mvc.perform(
				get("/leaders")
				.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		// then (assert)
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(json.write(leaderBoardList).getJson());
	}
}
