package com.github.ean244.jrcreator.commands;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public interface Commands {
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args);
	
	public default CommandMeta meta() {
		return Commands.class.getAnnotation(CommandMeta.class);
	}
}
