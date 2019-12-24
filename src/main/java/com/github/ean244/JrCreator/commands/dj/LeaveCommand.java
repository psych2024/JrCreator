package com.github.ean244.JrCreator.commands.dj;

import com.github.ean244.JrCreator.commands.CommandMeta;
import com.github.ean244.JrCreator.commands.Commands;
import com.github.ean244.JrCreator.music.GuildPlayer;
import com.github.ean244.JrCreator.music.GuildPlayerRegistry;
import com.github.ean244.JrCreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = {}, name = "leave", permission = PermissionLevel.DJ)
public class LeaveCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (args.length != 0)
			return false;

		GuildPlayer player = GuildPlayerRegistry.getGuildPlayer(guild);

		if(!player.isInChannel()) {
			channel.sendMessage("I'm not in a channel!").queue();
			return true;
		}
		
		channel.sendMessage("Leaving from voice channel").queue();
		player.leave();
		return true;
	}

}
