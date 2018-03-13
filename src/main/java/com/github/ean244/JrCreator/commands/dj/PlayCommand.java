package com.github.ean244.jrcreator.commands.dj;

import com.github.ean244.jrcreator.commands.CommandMeta;
import com.github.ean244.jrcreator.commands.Commands;
import com.github.ean244.jrcreator.music.GuildPlayer;
import com.github.ean244.jrcreator.music.GuildPlayerRegistry;
import com.github.ean244.jrcreator.music.PlayerState;
import com.github.ean244.jrcreator.music.TrackWrapper;
import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = { }, name = "play", permission = PermissionLevel.DJ)
public class PlayCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (args.length != 1)
			return false;

		if (!isInteger(args[0]))
			return false;

		int id = Integer.parseInt(args[0]);

		if (id > 5 || id < 0) {
			channel.sendMessage("Number must be in range `1-5`").queue();
			return true;
		}

		GuildPlayer guildPlayer = GuildPlayerRegistry.getGuildPlayer(guild);

		if (!guildPlayer.getScheduler().hasSelected(member)) {
			channel.sendMessage("You have not load a selection list yet").queue();
			return true;
		}

		if (!member.getVoiceState().inVoiceChannel()) {
			channel.sendMessage("You must be in a voice channel to do that").queue();
			return true;
		}

		TrackWrapper wrapper = guildPlayer.getScheduler().loadSelectedTrack(member, id);

		// check if currently a song is played
		if (guildPlayer.getState() == PlayerState.PLAYING) {
			// track already scheduled
			channel.sendMessage("Added **" + wrapper.toString() + "** to playlist").queue();
			return true;
		}

		// no song join and play
		guildPlayer.join(member.getVoiceState().getChannel());
		guildPlayer.play();
		channel.sendMessage("Playing **" + guildPlayer.getScheduler().currentTrack().getTitle() + "**").queue();
		return true;
	}

	private boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}

}
