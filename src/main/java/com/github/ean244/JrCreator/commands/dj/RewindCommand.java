package com.github.ean244.jrcreator.commands.dj;

import com.github.ean244.jrcreator.commands.CommandMeta;
import com.github.ean244.jrcreator.commands.Commands;
import com.github.ean244.jrcreator.music.GuildPlayer;
import com.github.ean244.jrcreator.music.GuildPlayerRegistry;
import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = { "" }, name = "rewind", permission = PermissionLevel.DJ)
public class RewindCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (args.length != 0)
			return false;
		
		GuildPlayer player = GuildPlayerRegistry.getGuildPlayer(guild);
		
		if(!player.isPlayingOrPaused()) {
			channel.sendMessage("No music playing currently!").queue();
			return true;
		}
		
		player.setCurrentTrackPosition(0);
		channel.sendMessage("Rewinding song...").queue();
		return true;
	}

}
