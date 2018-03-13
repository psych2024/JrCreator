package com.github.ean244.jrcreator.music;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

public class GuildPlayerListener extends AudioEventAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(GuildPlayerListener.class);
	private final GuildPlayer player;

	public GuildPlayerListener(GuildPlayer player) {
		this.player = player;
	}

	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if (endReason == AudioTrackEndReason.FINISHED)
			this.player.next();
	}

	@Override
	public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
		LOGGER.error("Track stuck for guild {}", this.player.getGuild().getName());
		this.player.getGuild().getDefaultChannel().sendMessage("**ERROR** Track Stuck! Skipping track...").queue();
	}

	@Override
	public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
		LOGGER.error("Track exception for guild {}", this.player.getGuild().getName(), exception);
		this.player.getGuild().getDefaultChannel()
				.sendMessage("**ERROR** There was some problems with the current playing track %nStopping player...")
				.queue();
	}
}
