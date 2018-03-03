package com.github.ean244.jrcreator.commands;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.ean244.jrcreator.db.impl.PermissionsImpl;
import com.github.ean244.jrcreator.db.impl.PrefixImpl;
import com.github.ean244.jrcreator.main.JrCreator;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	public static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(4);
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		THREAD_POOL.submit(() -> handle(event));
	}
	
	private void handle(MessageReceivedEvent event) {
		TextChannel channel = event.getTextChannel();
		Guild guild = event.getGuild();
		Member member = event.getMember();
		String msg = event.getMessage().getContentDisplay();
		String prefix = new PrefixImpl().request(guild);
		
		if(event.getAuthor().isBot())
			return;
		
		if (msg.startsWith(prefix) || msg.split(" ")[0].equals(JrCreator.getJda().getSelfUser().getAsMention())) {
			String[] args = msg.split(" ");
			
			if(msg.startsWith(prefix)) {
				args[0] = args[0].substring(1);
			}
			
			Commands commands = CommandRegistry.getInstance().getCommand(args[0]);

			if (commands == null)
				return;
			
			if (commands.meta().permission().level() > new PermissionsImpl().requestIndividual(guild, member).level()) {
				channel.sendMessage(String.format("%s You don't have enough permissions to perform that command. ", member.getUser().getAsMention())).queue();
				return;
			}

			if (!commands.onExecute(channel, guild, member, Arrays.copyOfRange(args, 1, args.length))) {
				channel.sendMessage(String.format("%s Incorrect command usage. Do `%shelp` to see a list of commands.", member.getAsMention(), new PrefixImpl().request(guild))).queue();
			}
		}
	}
}