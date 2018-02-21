package com.github.ean244.jrcreator.commands;

import java.util.Arrays;

import com.github.ean244.jrcreator.db.impl.PermissionsImpl;
import com.github.ean244.jrcreator.db.impl.PrefixImpl;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		TextChannel channel = event.getTextChannel();
		Guild guild = event.getGuild();
		Member member = event.getMember();
		String msg = event.getMessage().getContentDisplay();
		String prefix = new PrefixImpl().request(guild);

		if (msg.startsWith(prefix)) {
			String[] args = msg.substring(1).split(" ");
			Commands commands = CommandRegistry.getInstance().getCommand(args[0]);

			if (commands == null)
				return;
			
			if (commands.meta().permission().level() > new PermissionsImpl().requestIndividual(guild, member).level()) {
				channel.sendMessage(String.format("%s You don't have enough permissions to perform that command.", member.getUser().getAsMention()));
			}

			if (!commands.onExecute(channel, guild, member, Arrays.copyOfRange(args, 1, args.length))) {
				// Wrong usage
			}
		}
	}
}
