package com.github.ean244.jrcreator.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;

public class MusicHandler {
	private final AudioPlayerManager manager;
	
	private static MusicHandler instance;
	
	private MusicHandler() {
		manager = new DefaultAudioPlayerManager();
		manager.registerSourceManager(new YoutubeAudioSourceManager(true));
	}
	
	public static MusicHandler getInstance() {
		if(instance == null)
			instance = new MusicHandler();
		
		return instance;
	}
	
	public AudioPlayerManager getManager() {
		return manager;
	}

	public AudioPlayer newPlayer() {
		return manager.createPlayer();
	}
}
