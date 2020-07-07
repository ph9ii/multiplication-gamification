package com.social.gamification.event;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Event that models the fact that a
 * {@link com.social.multiplication.domain.Multiplication} has been solved in
 * the system. Provide some context information about the multiplication.
 * 
 * @author mocha
 *
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class MultiplicationSolvedEvent implements Serializable {
	private final Long MultiplicationResultAttemptId;
	private final Long userId;
	private final boolean correct;
}
