package com.github.ean244.jrcreator.commands.dj;

import java.util.List;

import com.github.ean244.jrcreator.commands.CommandMeta;
import com.github.ean244.jrcreator.commands.Commands;
import com.github.ean244.jrcreator.music.GuildPlayer;
import com.github.ean244.jrcreator.music.GuildPlayerRegistry;
import com.github.ean244.jrcreator.music.TrackWrapper;
import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = {}, name = "playlist", permission = PermissionLevel.DJ)
public class PlaylistCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		GuildPlayer player = GuildPlayerRegistry.getGuildPlayer(guild);
		
		if(player.getScheduler().isPlaylistEmpty()) {
			channel.sendMessage("No songs available!").queue();
			return true;
		}
		
		List<TrackWrapper> playlist = player.getScheduler().getPlaylist();
		StringBuilder builder = new StringBuilder(String.format("**Currently playing:** %s @ %s%n", playlist.get(0).getTitle(), playlist.get(0).currentDuration()));
	
		if(playlist.size() > 1) {
			builder.append("\n**Coming Up:**\n");
		}
		
		for(int i = 1; i < playlist.size(); i++) {
			builder.append(String.format("**%d** %s%n", i, playlist.get(i).toString()));
		}
		
		channel.sendMessage(builder.toString()).queue();
		return true;
	}

}
