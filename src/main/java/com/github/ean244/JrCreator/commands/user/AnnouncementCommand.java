package com.github.ean244.JrCreator.commands.user;

import com.github.ean244.JrCreator.commands.CommandMeta;
import com.github.ean244.JrCreator.commands.Commands;
import com.github.ean244.JrCreator.announcement.AnnouncementHandler;
import com.github.ean244.JrCreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.*;

@CommandMeta(aliases = { "announce" }, name = "announcement", permission = PermissionLevel.USER)
public class AnnouncementCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (args.length > 0)
			return false;

		Message message = AnnouncementHandler.getInstance().getAnnouncementMessage(guild);

		if(message == null) {
			channel.sendMessage("This command is not supported for this guild. A channel with name `announcement` is required").queue();
			return true;
		}

		channel.sendMessage(":loudspeaker: **LATEST ANNOUNCEMENTS** :loudspeaker:").queue();
		channel.sendMessage("Announcement by: **" + message.getAuthor().getName() + "**").queue();
		channel.sendMessage("`" + message.getContentDisplay() + "`").queue();
		return true;
	}

}
