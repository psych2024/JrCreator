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
	private VoiceChannel joinedChannel;
	private final TrackScheduler scheduler;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GuildPlayer.class);
	
	public GuildPlayer(Guild guild) {
		this.player = MusicHandler.getInstance().createPlayer();
		this.guild = guild;
		this.scheduler = new TrackScheduler(this);
		this.player.addListener(new GuildPlayerListener(this));
	}
	
	public void join(VoiceChannel channel) {
		LOGGER.info("join");
		
		AudioManager audioManager = guild.getAudioManager();
		
		joinedChannel = channel;
		
		if(audioManager.isConnected())
			audioManager.closeAudioConnection();
		
		audioManager.setSendingHandler(new CustomSendHandler(player));
		audioManager.openAudioConnection(channel);
	}
	
	public void leave() {
		LOGGER.info("leave");
		
		stop();
		
		joinedChannel = null;
		
		guild.getAudioManager().closeAudioConnection();
	}
	
	public void play() {
		LOGGER.info("play");
		
		if(player.isPaused())
			player.setPaused(false);
		
		if(scheduler.currentLoadedTrack() == null) {
			leave();
			return;
		}
		
		player.playTrack(scheduler.currentLoadedTrack());
	}
	
	public void pause() {
		LOGGER.info("pause");
		
		player.setPaused(true);
	}
	
	public void next() {
		LOGGER.info("next");
		
		stop();
		
		play();
	}
	
	public void stop() {
		LOGGER.info("stop");
		
		player.stopTrack();
		
		scheduler.next();
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
}
