package com.social.gamification.client.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@JsonDeserialize(using = MultiplicationResultAttemptDeserializer.class)
public final class MultiplicationResultAttempt {

	private final String userAlias;

	private final int MultiplicationFactorA;
	private final int MultiplicationFactorB;
	private final int resultAttempt;

	private final boolean correct;
	
	// Empty constructor for JSON/JPA
	MultiplicationResultAttempt() {
		userAlias = null;
		MultiplicationFactorA = -1;
		MultiplicationFactorB = -1;
		resultAttempt = -1;
		correct = false;
	}

}
