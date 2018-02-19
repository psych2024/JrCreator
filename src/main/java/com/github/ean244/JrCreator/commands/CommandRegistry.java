package com.github.ean244.jrcreator.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
	private final Map<String, Commands> commands;
	
	public CommandRegistry() {
		commands = new HashMap<>();
	}
	
	public void register(Commands command) {
		CommandMeta meta = command.getClass().getAnnotation(CommandMeta.class);
		commands.put(meta.name() ,command);
	}
	
	public Commands getCommand(String name) {
		return commands.get(name);
	}
}
