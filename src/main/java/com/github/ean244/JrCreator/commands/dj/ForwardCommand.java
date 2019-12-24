package com.github.ean244.JrCreator.commands.dj;

import java.util.concurrent.TimeUnit;

import com.github.ean244.JrCreator.commands.CommandMeta;
import com.github.ean244.JrCreator.commands.Commands;
import com.github.ean244.JrCreator.db.impl.PrefixImpl;
import com.github.ean244.JrCreator.music.GuildPlayer;
import com.github.ean244.JrCreator.music.GuildPlayerRegistry;
import com.github.ean244.JrCreator.perms.PermissionLevel;
import com.github.ean244.JrCreator.utils.PrimitiveUtils;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = { "forwards" }, name = "forward", permission = PermissionLevel.DJ)
public class ForwardCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (args.length > 2)
			return false;

		if (args.length == 0 || !PrimitiveUtils.isInteger(args[0])) {
			channel.sendMessage(String.format("Incorrect usage. Please specify a time%nEx. `%sforward <seconds>`",
					new PrefixImpl().request(guild))).queue();
			return true;
		}

		int time = Integer.parseInt(args[0]);

		if (time <= 0) {
			channel.sendMessage("Please specify a valid time").queue();
			return true;
		}

		GuildPlayer player = GuildPlayerRegistry.getGuildPlayer(guild);

		if (!player.isPlayingOrPaused()) {
			channel.sendMessage("No music playing currently!").queue();
			return true;
		}

		player.setCurrentTrackPosition(player.getCurrentTrackPosition() + TimeUnit.SECONDS.toMillis(time));
		channel.sendMessage("Forwarding current track by " + time + " seconds.\n Playing at `"
				+ player.currentTrackDuration() + "`").queue();
		return true;
	}

}
