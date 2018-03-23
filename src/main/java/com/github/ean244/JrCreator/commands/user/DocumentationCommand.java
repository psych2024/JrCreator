package com.github.ean244.jrcreator.commands.user;

import com.github.ean244.jrcreator.commands.CommandMeta;
import com.github.ean244.jrcreator.commands.Commands;
import com.github.ean244.jrcreator.db.impl.PrefixImpl;
import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = { "docs", "doc" }, name = "documentation", permission = PermissionLevel.USER)
public class DocumentationCommand implements Commands {

	private static final StringBuilder BUILDER = new StringBuilder()
			.append("**Commands:**\n")
			.append("```md\n")
			.append("#Basic Commands\n")
			.append("%s[announcement, announce] - Displays recent announcements by admin\n")
			.append("%s[documentation docs, doc] - Shows commands and permissions for this bot\n")
			.append("%s[facebook ,fb] - Displays link to I-Creatorz facebook page\n")
			.append("%s[gitlab] - Shows gitlab page for I-Creatorz\n")
			.append("%s[github ,repo] - Directs to JrCreator's repository\n")
			.append("%s[help] - shows help and info about this bot\n")
			.append("\n")
			.append("#Music Commands\n")
			.append("%s[join] - Joins your voice channel\n")
			.append("%s[leave] - Leaves currently joined voice channel\n")
			.append("%s[yt, youtube] - searches for a track in youtube\n")
			.append("%s[backward ,backwards] <seconds> - Backwards current track by n seconds\n")
			.append("%s[forward ,forwards] <seconds> - Forwards current track by n seconds\n")
			.append("%s[playlist] - Shows songs which are going to be played\n")
			.append("%s[clearplayerlist, clearlist, clear] - clears the playlist and stops the player\n")
			.append("%s[pause] - pauses the player\n")
			.append("%s[unpause, resume] - unpauses the player\n")
			.append("%s[repeat] - repeats current song\n")
			.append("%s[rewind, restart] - restarts current song and sets the pointer to 0 seconds\n")
			.append("%s[skip] - skips current song played\n")
			.append("%s[stop] - stops the player\n")
			.append("\n")
			.append("#Admin commands\n")
			.append("%s[list] <user|admin|dj> - list all users with that specific permissions\n")
			.append("%s[setprefix] <prefix> - sets the prefix for this guild\n")
			.append("%s[setpermissions, setpermission, setperms] <user> <permission> - changes the permission for that specific members\n")
			.append("```\n")
			.append("\n")
			.append("**Permissions:**\n")
			.append("```md\n")
			.append("#Admin\n")
			.append("Able to perform all commands including admin category commands\n")
			.append("#DJ\n")
			.append("Able to perform basic user commands and also music commands\n")
			.append("#User\n")
			.append("Lowest permission level. Able to perform user category commands only\n")
			.append("```\n");
	
	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (args.length > 0)
			return false;
		
		String prefix = new PrefixImpl().request(guild);
		
		String message = BUILDER.toString().replaceAll("%s", prefix);
		
		channel.sendMessage(message).queue();
		return true;
	}

}
