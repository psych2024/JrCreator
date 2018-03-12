package com.github.ean244.jrcreator.music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;

import net.dv8tion.jda.core.entities.Member;

public class TrackScheduler {
	private final List<TrackWrapper> playlist;
	private final Map<Member, AudioPlaylist> memberSelectedTrack;
	
	public TrackScheduler() {
		this.playlist = new ArrayList<>();
		this.memberSelectedTrack = new HashMap<>();
	}
	
	public void loadSelectedTrack(Member member, int index) {
		TrackWrapper wrapper = new TrackWrapper(memberSelectedTrack.get(member).getTracks().get(0), member);
		loadTrack(wrapper);
	}
	
	public void assignTrack(Member member, AudioPlaylist list) {
		memberSelectedTrack.put(member, list);
	}
	
	public boolean hasSelected(Member member) {
		return memberSelectedTrack.containsKey(member);
	}
	
	public void loadTrack(TrackWrapper track) {
		this.playlist.add(track);
	}
	
	public TrackWrapper currentLoadedTrack() {
		return playlist.get(0);
	}
	
	public void next() {
		playlist.remove(0);
	}
	
	public boolean hasNext() {
		return !playlist.isEmpty();
	}
	
	public List<TrackWrapper> getPlaylist() {
		return playlist;
	}
}
