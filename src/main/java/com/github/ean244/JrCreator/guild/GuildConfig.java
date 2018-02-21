package com.github.ean244.jrcreator.guild;

public class GuildConfig {
	private final GuildPerms guildPerms;
	private final GuildPrefix guildPrefix;
	private final long id;
	
	public GuildConfig(long id, GuildPerms guildPerms, GuildPrefix guildPrefix) {
		this.guildPerms = guildPerms;
		this.guildPrefix = guildPrefix;
		this.id = id;
	}

	public GuildPerms getGuildPerms() {
		return guildPerms;
	}

	public String getPrefix() {
		return guildPrefix.getPrefix();
	}
	
	public long getId() {
		return id;
	}
}
