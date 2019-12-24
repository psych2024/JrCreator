package com.github.ean244.JrCreator.listener;

import java.util.Arrays;

import com.github.ean244.JrCreator.db.impl.PrefixImpl;
import com.github.ean244.JrCreator.dialogflow.AIManager;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ConversationListener extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String[] rawArgs = event.getMessage().getContentDisplay().split(" ");
		
		if(rawArgs.length == 0)
			return;
		
		String prefix = new PrefixImpl().request(event.getGuild());
		
		if("@JrCreator".equals(rawArgs[0])) {
			if(rawArgs.length == 1) {
				event.getChannel().sendMessage("Prefix for this guild is `" + prefix + "`"
						+ "\nDo `" + prefix + "help` for more info").queue();
				return;
			}
			
			StringBuilder builder = new StringBuilder();
			Arrays.stream(Arrays.copyOfRange(rawArgs, 1, rawArgs.length)).forEach(s -> builder.append(" " + s));
			AIManager.getInstance().handleResponse(builder.toString().trim(), event.getMember(), event.getTextChannel());
		}
	}
	
}
