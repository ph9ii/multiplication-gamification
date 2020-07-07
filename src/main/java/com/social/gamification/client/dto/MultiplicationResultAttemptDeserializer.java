package com.social.gamification.client.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class MultiplicationResultAttemptDeserializer extends JsonDeserializer<MultiplicationResultAttempt> {

	@Override
	public MultiplicationResultAttempt deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

}
