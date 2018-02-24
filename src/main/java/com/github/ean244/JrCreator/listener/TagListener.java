package com.github.ean244.jrcreator.listener;

import com.github.ean244.jrcreator.main.JrCreator;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class TagListener extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String[] msg = event.getMessage().getContentRaw().split(" ");
		TextChannel channel = event.getTextChannel();
		
		if(msg.length > 1)
			return;
		
		if(msg[0].equals(JrCreator.getJda().getSelfUser().getAsMention())) {
			channel.sendMessage("Received " + event.getAuthor().getAsMention()).queue();
		}
	}
}
