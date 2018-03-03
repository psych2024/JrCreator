package com.github.ean244.jrcreator.music;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;

public class YoutubeRequest {
	private final VoiceChannel channel;
	private final String query;
	private final Member member;

	public YoutubeRequest(VoiceChannel channel, String query, Member member) {
		this.channel = channel;
		this.query = query;
		this.member = member;
	}

	public VoiceChannel getChannel() {
		return channel;
	}

	public String getQuery() {
		return query;
	}

	public Member getMember() {
		return member;
	}
}
