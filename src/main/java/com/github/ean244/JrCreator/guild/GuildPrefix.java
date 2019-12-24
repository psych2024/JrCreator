package com.github.ean244.JrCreator.guild;

import net.dv8tion.jda.core.entities.Guild;

public class GuildPrefix {
	private final long id;
	private String prefix;
	
	public static final String DEFAULT_PREFIX = "/";
	
	public GuildPrefix(Guild guild) {
		this(guild, DEFAULT_PREFIX);
	}
	
	public GuildPrefix(Guild guild, String prefix) {
		this.id = guild.getIdLong();
		this.prefix = prefix;
	}
	
	public long getId() {
		return id;
	}
	
	public String getPrefix() {
		return prefix;
	}
}
