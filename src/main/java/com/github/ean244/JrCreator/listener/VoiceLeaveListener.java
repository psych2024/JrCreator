package com.github.ean244.jrcreator.listener;

import com.github.ean244.jrcreator.music.GuildPlayer;
import com.github.ean244.jrcreator.music.GuildPlayerRegistry;

import net.dv8tion.jda.core.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class VoiceLeaveListener extends ListenerAdapter {

	@Override
	public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
		GuildPlayer guildPlayer = GuildPlayerRegistry.getGuildPlayer(event.getGuild());

		if (event.getChannelLeft().getMembers().stream().filter(m -> !m.getUser().isBot()).count() != 0)
			return;
		
		if(!event.getChannelLeft().equals(guildPlayer.getJoinedChannel()))
			return;
		
		if (!guildPlayer.isInChannel())
			return;

		guildPlayer.getRequestChannel().sendMessage("Leaving voice channel as no one is inside").queue();
		guildPlayer.leave();
	}
}
