package com.social.gamification.event;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.social.gamification.service.GameService;

import lombok.extern.slf4j.Slf4j;

/**
 * This class receives the event and triggers the associated business logic.
 * 
 * @author mocha
 *
 */
@Slf4j
@Component
public class EventHandler {

	private GameService gameService;

	public EventHandler(final GameService gameService) {
		this.gameService = gameService;
	}

	@RabbitListener(queues = "${multiplication.queue}")
	void handleMultiplicationSolved(final MultiplicationSolvedEvent event) {
		log.info("Multiplication Solved Event recieved: {}", event.getMultiplicationResultAttemptId());

		try {
			gameService.newAttemptForUser(event.getUserId(), event.getMultiplicationResultAttemptId(),
					event.isCorrect());

		} catch (final Exception e) {
			log.error("Error while trying to process MultiplicationSolvedEvent", e);
			// Avoids the event to be re-queues and reprocessed.
			throw new AmqpRejectAndDontRequeueException(e);
		}
	}
}
