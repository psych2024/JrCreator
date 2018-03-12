package com.github.ean244.jrcreator.music;

import java.util.ArrayList;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;

import net.dv8tion.jda.core.entities.Member;

public class AudioPlaylistWrapper {
	private final List<TrackWrapper> tracks;
	private final Member requester;
	
	public AudioPlaylistWrapper(AudioPlaylist playlist, Member requester) {
		this.tracks = new ArrayList<>();
		playlist.getTracks().forEach(t -> tracks.add(new TrackWrapper(t, requester)));
		this.requester = requester;
	}

	public List<TrackWrapper> getPlaylist() {
		return tracks;
	}

	public Member getRequester() {
		return requester;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(int i = 1; i < 6; i++) {
			TrackWrapper track = tracks.get(i);
			builder.append(String.format("**%d.** %s%n", i, track.toString()));
		}
		return builder.toString();
	}
}
