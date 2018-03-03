package com.github.ean244.jrcreator.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnouncementHandler {
	private final File announceFile;
	private final List<String> content;
	
	private static AnnouncementHandler instance;
	private static final Logger LOGGER = LoggerFactory.getLogger(AnnouncementHandler.class);
	
	private AnnouncementHandler() {
		announceFile = new File("src/main/resources/announcement.txt");
		content = new ArrayList<>();
	}
	
	public static AnnouncementHandler getInstance() {
		if(instance == null)
			instance = new AnnouncementHandler();
		
		return instance;
	}
	
	public void readContent() {
		LOGGER.info("Reading file announcement.txt");
		
		if(!announceFile.exists()) {
			LOGGER.warn("announcement.txt cannot be found!");
			return;
		}
		
		try(BufferedReader reader = new BufferedReader(new FileReader(announceFile))) {
			
			String line;
			while((line = reader.readLine()) != null) {
				content.add(line);
			}
			LOGGER.info("Successfully read file!");
			
		} catch (IOException e) {
			LOGGER.error("Failed to read file", e);
		}
		
	}
	
	public List<String> getContent() {
		return Collections.unmodifiableList(content);
	}
}
