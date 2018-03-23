package com.github.ean244.jrcreator.dialogflow;

import com.github.ean244.jrcreator.commands.CommandRegistry;
import com.google.gson.JsonElement;

import ai.api.model.AIResponse;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class MusicResponseHandler implements ResponseHandler {

	@Override
	public void handle(AIResponse response, Member member, TextChannel channel) {
		JsonElement title = response.getResult().getParameters().get("title");
		
		if(title == null) {
			new DefaultResponseHandler().handle(response, member, channel);
			return;
		}
		
		CommandRegistry.getInstance().getCommand("youtube").onExecute(channel, channel.getGuild(), member,
				new String[] { title.getAsString() });
	}

}
