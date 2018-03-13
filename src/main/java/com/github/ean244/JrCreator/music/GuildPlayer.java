package com.github.ean244.jrcreator.music;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

public class GuildPlayer {
	private final AudioPlayer player;
	private final Guild guild;
	private PlayerState state;
	private VoiceChannel joinedChannel;
	private final TrackScheduler scheduler;

	private static final Logger LOGGER = LoggerFactory.getLogger(GuildPlayer.class);

	public GuildPlayer(Guild guild) {
		this.player = MusicHandler.getInstance().newPlayer();
		this.guild = guild;
		this.state = PlayerState.NOT_JOINED;
		this.scheduler = new TrackScheduler();
		this.player.addListener(new GuildPlayerListener(this));
	}

	public void join(VoiceChannel channel) {
		leave();

		this.state = PlayerState.JOINED;
		logState();

		AudioManager audioManager = guild.getAudioManager();

		joinedChannel = channel;

		if (audioManager.isConnected())
			audioManager.closeAudioConnection();

		audioManager.setSendingHandler(new CustomSendHandler(player));
		audioManager.openAudioConnection(channel);
	}

	public void leave() {
		// stop current song
		stop();

		this.state = PlayerState.NOT_JOINED;
		logState();

		joinedChannel = null;
		
		scheduler.clearPlaylistSongs();

		guild.getAudioManager().closeAudioConnection();
	}

	public void play() {
		if(this.state == PlayerState.PLAYING) 
			throw new IllegalStateException("Play() called twice");
		
		this.state = PlayerState.PLAYING;
		logState();

		System.out.println("b4 stuck");
		player.playTrack(scheduler.currentTrack().getTrack());
		System.out.println("after stuck");
		LOGGER.info("Playing track {} in guild {}", scheduler.currentTrack().getTitle(), guild.getName());
	}

	public void unpause() {
		this.state = PlayerState.PLAYING;
		logState();

		player.setPaused(false);
	}

	public void pause() {
		this.state = PlayerState.PAUSED;
		logState();

		player.setPaused(true);
	}

	public void next() {
		LOGGER.info("Skipping track...");

		stop();

		play();
	}

	public void stop() {
		this.state = PlayerState.STOPPED;
		logState();

		player.stopTrack();

		if (scheduler.hasNext()) {
			scheduler.next();
			return;
		}
	}

	public AudioPlayer getPlayer() {
		return player;
	}

	public VoiceChannel getJoinedChannel() {
		return joinedChannel;
	}

	public boolean isInChannel() {
		return joinedChannel != null;
	}

	public Guild getGuild() {
		return guild;
	}

	public TrackScheduler getScheduler() {
		return scheduler;
	}

	public PlayerState getState() {
		return state;
	}

	private void logState() {
		LOGGER.info("Changed PlayerState for Guild {}: {}", guild.getName(), state);
	}
}
