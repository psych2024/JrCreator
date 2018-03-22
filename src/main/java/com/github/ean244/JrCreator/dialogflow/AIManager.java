package com.github.ean244.jrcreator.dialogflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class AIManager {
	private final AIDataService service;
	private final AIConfiguration configuration;
	private static final Logger LOGGER = LoggerFactory.getLogger(AIManager.class);
	private static AIManager instance = null;
	public static String CLIENT_TOKEN = null;
	
	private AIManager(String token) {
		configuration = new AIConfiguration(token);
		service = new AIDataService(configuration);
	}

	public void handleResponse(String query, Member member, TextChannel channel) {
		AIRequest request = new AIRequest(query);
		AIResponse response = null;
		try {
			response = service.request(request);
			
			if(response.getResult().getAction().equals("play-song")) {
				new MusicResponseHandler().handle(response, member, channel);
				return;
			}
			
			new DefaultResponseHandler().handle(response, member, channel);
		} catch (AIServiceException e) {
			LOGGER.error("Failed to execute query", e);
		}
	}
	
	public static AIManager getInstance() {
		if(instance == null)
			instance = new AIManager(CLIENT_TOKEN);
		
		return instance;
	}
}
