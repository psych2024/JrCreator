package com.github.ean244.jrcreator.main;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ean244.jrcreator.commands.CommandListener;
import com.github.ean244.jrcreator.commands.CommandRegistry;
import com.github.ean244.jrcreator.commands.admin.ListCommand;
import com.github.ean244.jrcreator.commands.admin.SetPrefixCommand;
import com.github.ean244.jrcreator.db.DbHandler;
import com.github.ean244.jrcreator.listener.TagListener;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class JrCreator {
	private static final Logger LOGGER = LoggerFactory.getLogger(JrCreator.class);
	private static JDA jda;

	private JrCreator() {
	}
	
	public static void main(String[] args) throws LoginException {
		if (args.length != 1) {
			LOGGER.error("Failed to setup: Token required!");
			return;
		}
		
		jda = new JDABuilder(AccountType.BOT).setToken(args[0]).buildAsync();

		onEnable();
	}
	
	private static void onEnable() {
		// connect to database
		DbHandler.getInstance().connDb();

		LOGGER.info("Initializing listeners...");

		addListener();

		LOGGER.info("Listeners successfully initialized!");

		LOGGER.info("Registering commands...");
		
		registerCommands();

		LOGGER.info("Commands successfully Registered!");
	}
	
	private static void onDisable() {
		DbHandler.getInstance().closeConn();
		
		LOGGER.info("Shutting down listener thread pool...");
		
		CommandListener.THREAD_POOL.shutdown();
		
		LOGGER.info("Thread pool has shut down!");
		
		LOGGER.info("Shutting down JDA...");
		
		jda.shutdown();
		
		LOGGER.info("JDA has shut down!");
	}

	private static void addListener() {
		jda.addEventListener(new CommandListener());
		jda.addEventListener(new TagListener());
	}

	private static void registerCommands() {
		CommandRegistry registry = CommandRegistry.getInstance();

		registry.register(new ListCommand());
		registry.register(new SetPrefixCommand());
	}

	public static JDA getJda() {
		return jda;
	}
}
