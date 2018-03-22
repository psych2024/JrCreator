package com.github.ean244.jrcreator.dialogflow;

import ai.api.model.AIResponse;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public interface ResponseHandler {
	public void handle(AIResponse response, Member member, TextChannel channel);
}
