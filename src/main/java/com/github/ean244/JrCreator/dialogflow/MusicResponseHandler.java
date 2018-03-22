package com.github.ean244.jrcreator.dialogflow;

import com.github.ean244.jrcreator.commands.CommandRegistry;

import ai.api.model.AIResponse;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class MusicResponseHandler implements ResponseHandler {

	@Override
	public void handle(AIResponse response, Member member, TextChannel channel) {
		String title = response.getResult().getParameters().get("title").getAsString();
		String artist = response.getResult().getParameters().get("artist").getAsString();

		String search = title + (artist == null ? "" : "by " + artist);

		CommandRegistry.getInstance().getCommand("youtube").onExecute(channel, channel.getGuild(), member,
				new String[] { search });
	}

}
