package com.github.ean244.jrcreator.commands.dj;

import java.util.concurrent.TimeUnit;

import com.github.ean244.jrcreator.commands.CommandMeta;
import com.github.ean244.jrcreator.commands.Commands;
import com.github.ean244.jrcreator.db.impl.PrefixImpl;
import com.github.ean244.jrcreator.music.GuildPlayer;
import com.github.ean244.jrcreator.music.GuildPlayerRegistry;
import com.github.ean244.jrcreator.perms.PermissionLevel;
import com.github.ean244.jrcreator.utils.PrimitiveUtils;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = { "backwards" }, name = "backward", permission = PermissionLevel.DJ)
public class BackwardCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (args.length > 2)
			return false;

		if (args.length == 0 || !PrimitiveUtils.isInteger(args[0])) {
			channel.sendMessage(String.format("Incorrect usage. Please specify a time%nEx. `%sbackward <seconds>`",
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
		
		player.setCurrentTrackPosition(player.getCurrentTrackPosition() - TimeUnit.SECONDS.toMillis(time));
		channel.sendMessage("Backwarding current track by " + time + "seconds.\n Playing at `" + player.currentTrackDuration() + "`").queue();
		return true;
	}

}
