package com.github.ean244.jrcreator.music;

import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MusicListener extends ListenerAdapter {
	
	@Override
	public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
		if(event.getChannelLeft().getMembers().stream().filter(m -> !m.getUser().isBot()).count() != 0)
			return;
		if(event.getMember().getUser().isBot())
			return;
		
		
		GuildPlayer guildPlayer = GuildPlayerRegistry.getGuildPlayer(event.getGuild());
		event.getGuild().getDefaultChannel().sendMessage("Leaving voice channel as no one is inside").queue();
		guildPlayer.leave();
	}
}
