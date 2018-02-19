package com.github.ean244.jrcreator.commands;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		MessageChannel channel = event.getChannel();
		Guild guild = event.getGuild();
		String msg = event.getMessage().getContentDisplay();
		
		if(msg.startsWith("/")) {
		}
		
		
	}
}
