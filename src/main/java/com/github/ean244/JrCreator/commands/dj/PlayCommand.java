package com.github.ean244.JrCreator.commands.dj;

import com.github.ean244.JrCreator.commands.CommandMeta;
import com.github.ean244.JrCreator.commands.Commands;
import com.github.ean244.JrCreator.music.GuildPlayer;
import com.github.ean244.JrCreator.music.GuildPlayerRegistry;
import com.github.ean244.JrCreator.music.TrackWrapper;
import com.github.ean244.JrCreator.perms.PermissionLevel;
import com.github.ean244.JrCreator.utils.PrimitiveUtils;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = {}, name = "play", permission = PermissionLevel.DJ)
public class PlayCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (args.length != 1)
			return false;

		if (!PrimitiveUtils.isInteger(args[0]))
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

		// check if currently a song is played
		if (!guildPlayer.getScheduler().isPlaylistEmpty()) {
			TrackWrapper wrapper = guildPlayer.getScheduler().loadSelectedTrack(member, id);
			channel.sendMessage("Added **" + wrapper.toString() + "** to playlist").queue();
			return true;
		}

		// no songs playing, join the channel
		if (!member.getVoiceState().getChannel().equals(guildPlayer.getJoinedChannel())) {
			guildPlayer.join(member.getVoiceState().getChannel(), channel);
		}

		// load track after join, as joining clears the list
		guildPlayer.getScheduler().loadSelectedTrack(member, id);
		guildPlayer.play();
		channel.sendMessage("Playing **" + guildPlayer.getScheduler().currentTrack().getTitle() + "**").queue();
		return true;
	}
}
