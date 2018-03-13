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

@CommandMeta(aliases = { "unpause" }, name = "resume", permission = PermissionLevel.DJ)
public class ResumeCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (args.length != 0)
			return false;

		GuildPlayer player = GuildPlayerRegistry.getGuildPlayer(guild);

		if (player.getState() != PlayerState.PAUSED) {
			channel.sendMessage("Song is not paused!").queue();
			return true;
		}

		player.unpause();
		channel.sendMessage(String.format("Resuming song at %s", player.getScheduler().currentTrack().duration()))
				.queue();
		return true;
	}

}
