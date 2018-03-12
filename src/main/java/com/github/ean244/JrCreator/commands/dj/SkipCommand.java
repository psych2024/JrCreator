package com.github.ean244.jrcreator.commands.dj;

import com.github.ean244.jrcreator.commands.CommandMeta;
import com.github.ean244.jrcreator.commands.Commands;
import com.github.ean244.jrcreator.music.GuildPlayer;
import com.github.ean244.jrcreator.music.GuildPlayerRegistry;
import com.github.ean244.jrcreator.music.PlayerState;
import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = { "next" }, name = "skip", permission = PermissionLevel.DJ)
public class SkipCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		GuildPlayer player = GuildPlayerRegistry.getGuildPlayer(guild);
		
		if(!player.getScheduler().hasNext() || player.getState() != PlayerState.STOPPED) {
			channel.sendMessage("No more tracks loaded!").queue();
			return true;
		}
		
		player.next();
		channel.sendMessage("Skipping to next track...").queue();
		return true;
	}

}
