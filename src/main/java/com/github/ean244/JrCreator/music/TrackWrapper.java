package com.github.ean244.jrcreator.music;

import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.entities.Member;

public class TrackWrapper {
	private final AudioTrack track;
	private final Member requester;
	
	public TrackWrapper(AudioTrack track, Member requester) {
		this.track = track.makeClone();
		this.requester = requester;
	}
	
	public AudioTrack getTrack() {
		return track;
	}
	
	public Member getRequester() {
		return requester;
	}
	
	public String getTitle() {
		return track.getInfo().title;
	}
	
	@Override
	public String toString() {
		return track.getInfo().title + " " + (track.getPosition() == 0 ? totalDuration() : currentDuration());
	}
	
	public String totalDuration() {
		return format(track.getDuration());
	}
	
	public String currentDuration() {
		return format(track.getPosition());
	}
	
	private String format(long mills) {
		long minutes = TimeUnit.MILLISECONDS.toMinutes(mills);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(mills - minutes * 60 * 1000);
		return String.format("(%02d:%02d)", minutes, seconds);
	}
}
