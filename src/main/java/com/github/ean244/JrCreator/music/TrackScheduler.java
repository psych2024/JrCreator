package com.github.ean244.jrcreator.music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.entities.Member;

public class TrackScheduler {
	private final GuildPlayer player;
	private final List<AudioTrack> tracks;
	private final Map<Member, AudioPlaylist> memberSelectedTrack;
	
	public TrackScheduler(GuildPlayer player) {
		this.player = player;
		this.tracks = new ArrayList<>();
		this.memberSelectedTrack = new HashMap<>();
	}
	
	public void loadSelectedTrack(Member member, int index) {
		loadTrack(memberSelectedTrack.get(member).getTracks().get(index));
	}
	
	public void assignTrack(Member member, AudioPlaylist list) {
		memberSelectedTrack.put(member, list);
	}
	
	public boolean hasSelected(Member member) {
		return memberSelectedTrack.containsKey(member);
	}
	
	public void loadTrack(AudioTrack audioTrack) {
		this.tracks.add(audioTrack);
	}
	
	public AudioTrack currentLoadedTrack() {
		return tracks.get(0);
	}
	
	public void next() {
		tracks.remove(0);
	}
	
	public GuildPlayer getPlayer() {
		return player;
	}
}
