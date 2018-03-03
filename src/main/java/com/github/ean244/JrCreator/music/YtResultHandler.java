package com.github.ean244.jrcreator.music;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ean244.jrcreator.db.impl.PrefixImpl;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class YtResultHandler implements AudioLoadResultHandler {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(YtResultHandler.class);
	private final TextChannel channel;
	private final Guild guild;
	private final String query;
	private final Member member;
	
	public YtResultHandler(TextChannel channel, String query, Member member) {
		this.channel = channel;
		this.query = query;
		this.guild = channel.getGuild();
		this.member = member;
	}
	
	@Override
	public void trackLoaded(AudioTrack track) {
		GuildPlayer guildPlayer = GuildPlayerRegistry.getInstance().getGuildPlayer(guild);
		guildPlayer.getScheduler().loadTrack(track);
		guildPlayer.play();
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist) {
		MessageBuilder builder = new MessageBuilder().append("Please select from one of these songs:\n");
		
		for(int i = 1; i < 6; i++) {
			AudioTrack track = playlist.getTracks().get(i);
			builder.append(String.format("**%d.** %s %s%n", i, track.getInfo().title, formatMills(track.getDuration())));
		}
		
		builder.append(String.format("%nDo `%splay 1-5` to start playing", new PrefixImpl().request(channel.getGuild())));
		channel.sendMessage(builder.build()).queue();
		GuildPlayerRegistry.getInstance().getGuildPlayer(guild).getScheduler().assignTrack(member, playlist);
	}

	@Override
	public void noMatches() {
		channel.sendMessage(String.format("No results were found for `%s`", query)).queue();
	}

	@Override
	public void loadFailed(FriendlyException exception) {
		channel.sendMessage("**WARNING** There was a problem while loading your search").queue();
		LOGGER.error("Failed to execute search, error level {}", exception, exception.severity);
	}
	
	private String formatMills(long mills) {
		long minutes = TimeUnit.MILLISECONDS.toMinutes(mills);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(mills - minutes * 60 * 1000);
		return String.format("(%02d:%02d)", minutes, seconds);
	}
}
