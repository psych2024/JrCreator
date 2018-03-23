package com.github.ean244.jrcreator.dialogflow;

import ai.api.model.AIResponse;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class DefaultResponseHandler implements ResponseHandler {

	@Override
	public void handle(AIResponse response, Member member, TextChannel channel) {
		channel.sendMessage(member.getAsMention() + " " + response.getResult().getFulfillment().getSpeech()).queue();
	}
}
