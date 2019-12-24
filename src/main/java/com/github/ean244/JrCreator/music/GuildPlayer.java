package com.github.ean244.JrCreator.music;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

public class GuildPlayer {
	private final AudioPlayer player;
	private final Guild guild;
	private PlayerState state;
	private TextChannel requestChannel;
	private final TrackScheduler scheduler;

	private static final Logger LOGGER = LoggerFactory.getLogger(GuildPlayer.class);

	public GuildPlayer(Guild guild) {
		this.player = MusicHandler.getInstance().newPlayer();
		this.guild = guild;
		this.state = PlayerState.NOT_JOINED;
		this.scheduler = new TrackScheduler();
		this.player.addListener(new GuildPlayerListener(this));
	}

	public void join(VoiceChannel channel, TextChannel request) {
		leave();

		this.state = PlayerState.JOINED;
		logState();

		AudioManager audioManager = guild.getAudioManager();

		requestChannel = request;

		if (audioManager.isConnected())
			audioManager.closeAudioConnection();

		audioManager.setSendingHandler(new CustomSendHandler(player));
		audioManager.openAudioConnection(channel);
	}

	public void leave() {
		// stop current song
		stop();
		
		guild.getAudioManager().closeAudioConnection();

		this.state = PlayerState.NOT_JOINED;
		logState();

		requestChannel = null;
	}

	public void play() {
		if (this.state == PlayerState.PLAYING)
			throw new IllegalStateException("Play() called twice");

		this.state = PlayerState.PLAYING;
		logState();

		player.playTrack(scheduler.currentTrack().getTrack());
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
		player.stopTrack();
		
		this.state = PlayerState.SKIPPING;
		logState();

		if (scheduler.hasNext()) {
			scheduler.next();
			play();
		}
	}

	public void stop() {
		this.state = PlayerState.STOPPED;
		logState();

		player.stopTrack();
		
		scheduler.clearPlaylistSongs();
	}
	
	public long getCurrentTrackPosition() {
		return player.getPlayingTrack().getPosition();
	}
	
	public void setCurrentTrackPosition(long position) {
		player.getPlayingTrack().setPosition(position);
	}
	
	public String currentTrackDuration() {
		return TrackWrapper.format(getCurrentTrackPosition());
	}

	public AudioPlayer getPlayer() {
		return player;
	}

	public VoiceChannel getJoinedChannel() {
		return guild.getAudioManager().getConnectedChannel();
	}

	public boolean isInChannel() {
		return guild.getAudioManager().isConnected();
	}
	
	public boolean isPlayingOrPaused() {
		return state == PlayerState.PLAYING || state == PlayerState.PAUSED;
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

	public TextChannel getRequestChannel() {
		return requestChannel;
	}

	private void logState() {
		LOGGER.info("Changed PlayerState for Guild {}: {}", guild.getName(), state);
	}
}
