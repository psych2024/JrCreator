package com.github.ean244.jrcreator.announcement;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AnnouncementHandler {
    private static AnnouncementHandler instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnouncementHandler.class);

    public static AnnouncementHandler getInstance() {
        if (instance == null)
            instance = new AnnouncementHandler();

        return instance;
    }

    public Message getAnnouncementMessage(Guild guild) {
        List<TextChannel> channels = guild.getTextChannelsByName("announcement", false);

        if(!channels.isEmpty()) {
            return channels.get(0).getMessageById(channels.get(0).getLatestMessageIdLong()).complete();
        }
        return null;
    }
}
