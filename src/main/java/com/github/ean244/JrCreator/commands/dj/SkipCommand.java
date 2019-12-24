package com.github.ean244.JrCreator.commands.dj;

import com.github.ean244.JrCreator.commands.CommandMeta;
import com.github.ean244.JrCreator.commands.Commands;
import com.github.ean244.JrCreator.music.GuildPlayer;
import com.github.ean244.JrCreator.music.GuildPlayerRegistry;
import com.github.ean244.JrCreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = { "next" }, name = "skip", permission = PermissionLevel.DJ)
public class SkipCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		GuildPlayer player = GuildPlayerRegistry.getGuildPlayer(guild);
		
		if(!player.getScheduler().hasNext()) {
			channel.sendMessage("No more tracks loaded!").queue();
			return true;
		}
		
		if(!player.isPlayingOrPaused()) {
			channel.sendMessage("No songs are played now!").queue();
			return true;
		}
		
		player.next();
		channel.sendMessage("Skipping to next track...").queue();
		return true;
	}

}
