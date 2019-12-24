package com.github.ean244.JrCreator.commands.dj;

import com.github.ean244.JrCreator.commands.CommandMeta;
import com.github.ean244.JrCreator.commands.Commands;
import com.github.ean244.JrCreator.music.GuildPlayer;
import com.github.ean244.JrCreator.music.GuildPlayerRegistry;
import com.github.ean244.JrCreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = {}, name = "join", permission = PermissionLevel.DJ)
public class JoinCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (args.length != 0)
			return false;

		GuildPlayer player = GuildPlayerRegistry.getGuildPlayer(guild);

		if (!member.getVoiceState().inVoiceChannel()) {
			channel.sendMessage("You are not in a voice channel!").queue();
			return true;
		}

		channel.sendMessage("Joining your voice channel").queue();
		player.join(member.getVoiceState().getChannel(), channel);
		return true;
	}

}
