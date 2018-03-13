package com.github.ean244.jrcreator.music;

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
		GuildPlayer guildPlayer = GuildPlayerRegistry.getGuildPlayer(guild);
		guildPlayer.getScheduler().loadTrack(new TrackWrapper(track, member));
		guildPlayer.play();
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist) {
		AudioPlaylistWrapper wrapper = new AudioPlaylistWrapper(playlist, member);
		
		MessageBuilder builder = new MessageBuilder().append("Please select from one of these songs:\n");
		builder.append(wrapper.toString());
		builder.append(String.format("%nDo `%splay 1-5` to start playing", new PrefixImpl().request(channel.getGuild())));
		channel.sendMessage(builder.build()).queue();
		GuildPlayerRegistry.getGuildPlayer(guild).getScheduler().assignTrack(member, playlist);
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
}
