package com.github.ean244.jrcreator.listener;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ean244.jrcreator.commands.CommandRegistry;
import com.github.ean244.jrcreator.commands.Commands;
import com.github.ean244.jrcreator.db.impl.PermissionsImpl;
import com.github.ean244.jrcreator.db.impl.PrefixImpl;
import com.github.ean244.jrcreator.main.UncaughtExeptionHandler;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	public static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(4,
			new ExceptionCatchingThreadFactory());
	private static final Logger LOGGER = LoggerFactory.getLogger(CommandListener.class);

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		THREAD_POOL.execute(() -> handle(event));
	}

	private void handle(MessageReceivedEvent event) {
		TextChannel channel = event.getTextChannel();
		Guild guild = event.getGuild();
		Member member = event.getMember();
		String msg = event.getMessage().getContentDisplay();

		String prefix = new PrefixImpl().request(guild);

		String[] rawArgs = msg.split(" ");
		
		if(!rawArgs[0].startsWith(prefix))
			return;
		
		if (event.getAuthor().isBot())
			return;
		
		Commands commands = CommandRegistry.getInstance().getCommand(rawArgs[0].replaceFirst(prefix, ""));
		
		LOGGER.info("User {} executed command /{} in guild {}", member.getUser().getName(), commands.meta().name(),
				guild.getName());

		if (commands.meta().permission().level() > new PermissionsImpl().requestIndividual(guild, member).level()) {
			channel.sendMessage(String.format("%s You don't have enough permissions to perform that command. ",
					member.getUser().getAsMention())).queue();
			return;
		}

		if (!commands.onExecute(channel, guild, member, Arrays.copyOfRange(rawArgs, 1, rawArgs.length))) {
			channel.sendMessage(String.format("%s Incorrect command usage. Do `%shelp` to see a list of commands.",
					member.getAsMention(), new PrefixImpl().request(guild))).queue();
		}
	}
	
	private static class ExceptionCatchingThreadFactory implements ThreadFactory {
		public Thread newThread(final Runnable r) {
			Thread thread = new Thread(r);
			thread.setUncaughtExceptionHandler(new UncaughtExeptionHandler());
			return thread;
		}
	}
}