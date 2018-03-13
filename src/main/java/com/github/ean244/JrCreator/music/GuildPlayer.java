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

		guild.getAudioManager().closeAudioConnection();
	}

	public void play() {
		this.state = PlayerState.PLAYING;
		logState();

		if (player.isPaused())
			player.setPaused(false);

		player.playTrack(scheduler.currentLoadedTrack().getTrack());
	}

	public void pause() {
		this.state = PlayerState.PAUSED;
		logState();

		player.setPaused(true);
	}

	public void next() {
		LOGGER.info("next");

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
