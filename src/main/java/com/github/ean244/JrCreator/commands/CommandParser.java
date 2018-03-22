package com.github.ean244.jrcreator.commands;

import java.util.Arrays;

import com.github.ean244.jrcreator.main.JrCreator;

public class CommandParser {
	private final String[] rawArgs;
	private final String prefix;
	private boolean isPrefixCall;
	private Commands commands;

	public CommandParser(String msg, String prefix) {
		this.prefix = prefix;
		this.rawArgs = msg.split(" ");
	}

	public boolean isValid() {
		// check for prefix call
		if ((commands = CommandRegistry.getInstance().getCommand(rawArgs[0].replaceFirst(prefix, ""))) != null)
			return true;

		// check for mention
		if (isMention() && ((commands = CommandRegistry.getInstance().getCommand(rawArgs[1])) != null)) {
			isPrefixCall = true;
			return true;
		}

		return false;
	}

	private boolean isMention() {
		return rawArgs[0].equals(JrCreator.getJda().getSelfUser().getAsMention());
	}

	public Commands getCommandCalled() {
		return commands;
	}
	
	public String[] getArgs() {
		return Arrays.copyOfRange(rawArgs, isPrefixCall ? 2 : 1, rawArgs.length);
	}
}
