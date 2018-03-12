package com.github.ean244.jrcreator.music;

import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.core.entities.Guild;

public class GuildPlayerRegistry {
	private static GuildPlayerRegistry instance;
	private final Map<Guild, GuildPlayer> guildPlayers;

	private GuildPlayerRegistry() {
		this.guildPlayers = new HashMap<>();
	}

	public static GuildPlayer getGuildPlayer(Guild guild) {
		if(instance.guildPlayers.get(guild) == null)
			instance.guildPlayers.put(guild, new GuildPlayer(guild));
		
		return instance.guildPlayers.get(guild);
	}
	
	public static GuildPlayerRegistry getInstance() {
		if(instance == null)
			instance = new GuildPlayerRegistry();
		
		return instance;
	}
}