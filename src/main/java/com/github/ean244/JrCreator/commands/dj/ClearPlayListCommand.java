package com.github.ean244.jrcreator.commands.dj;

import com.github.ean244.jrcreator.commands.CommandMeta;
import com.github.ean244.jrcreator.commands.Commands;
import com.github.ean244.jrcreator.music.GuildPlayer;
import com.github.ean244.jrcreator.music.GuildPlayerRegistry;
import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = { "clearplaylist", "clearlist" }, name = "clear", permission = PermissionLevel.DJ)
public class ClearPlayListCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		GuildPlayer player = GuildPlayerRegistry.getGuildPlayer(guild);
		
		if(player.getScheduler().isPlaylistEmpty()) {
			channel.sendMessage("No tracks to clear!").queue();
			return true;
		}
		
		player.getScheduler().clearPlaylistSongs();
		player.leave();
		channel.sendMessage("Cleared all tracks!").queue();
		return true;
	}

}
