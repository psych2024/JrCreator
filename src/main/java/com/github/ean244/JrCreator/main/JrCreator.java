package com.github.ean244.jrcreator.main;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ean244.jrcreator.db.DbHandler;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class JrCreator {
	private static final Logger LOGGER = LoggerFactory.getLogger(JrCreator.class);
	private static JDA jda;

	public static void main(String[] args) throws LoginException {
		if (args.length != 1) {
			LOGGER.error("Failed to setup: Token required!");
			return;
		}

		jda = new JDABuilder(AccountType.BOT).setToken(args[0]).buildAsync();

		// connect to database
		DbHandler.getInstance().connDb();
	}

	private static void addListener() {

	}

	private void addCommands() {

	}
}
