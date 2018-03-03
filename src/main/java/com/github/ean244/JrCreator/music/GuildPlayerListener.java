package com.github.ean244.jrcreator.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

public class GuildPlayerListener extends AudioEventAdapter {
	
	private final GuildPlayer player;
	
	public GuildPlayerListener(GuildPlayer player) {
		this.player = player;
	}
	
	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		this.player.next();
	}
}
