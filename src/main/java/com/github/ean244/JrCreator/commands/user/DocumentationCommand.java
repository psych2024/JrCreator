package com.github.ean244.jrcreator.commands.user;

import com.github.ean244.jrcreator.commands.CommandMeta;
import com.github.ean244.jrcreator.commands.Commands;
import com.github.ean244.jrcreator.db.impl.PrefixImpl;
import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = { "docs", "doc" }, name = "documentation", permission = PermissionLevel.USER)
public class DocumentationCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (args.length > 0)
			return false;
		
		String prefix = new PrefixImpl().request(guild);
		
		Message message = new MessageBuilder()
				.append("```md\n")
				.append("#Basic Commands\n")
				.append(prefix + "[announcement, announce] - Displays recent announcements by admin\n")
				.append(prefix + "[documentation docs, doc] - Shows commands and permissions for this bot\n")
				.append(prefix + "[facebook ,fb] - Displays link to I-Creatorz facebook page\n")
				.append(prefix + "[gitlab] - Shows gitlab page for I-Creatorz\n")
				.append(prefix + "[github ,repo] - Directs to JrCreator's repository\n")
				.append(prefix + "[help] - shows help and info about this bot\n")
				.append("\n")
				.append("#Music Commands\n")
				.append(prefix + "[join] - Joins your voice channel\n")
				.append(prefix + "[leave] - Leaves currently joined voice channel\n")
				.append(prefix + "[yt, youtube] - searches for a track in youtube\n")
				.append(prefix + "[backward ,backwards] <seconds> - Backwards current track by n seconds\n")
				.append(prefix + "[forward ,forwards] <seconds> - Forwards current track by n seconds\n")
				.append(prefix + "[playlist] - Shows songs which are going to be played\n")
				.append(prefix + "[clearplayerlist, clearlist, clear] - clears the playlist and stops the player\n")
				.append(prefix + "[pause] - pauses the player\n")
				.append(prefix + "[unpause, resume] - unpauses the player\n")
				.append(prefix + "[repeat] - repeats current song\n")
				.append(prefix + "[rewind, restart] - restarts current song and sets the pointer to 0 seconds\n")
				.append(prefix + "[skip] - skips current song played\n")
				.append(prefix + "[stop] - stops the player\n")
				.append("\n")
				.append("#Admin commands\n")
				.append(prefix + "[list] <user|admin|dj> - list all users with that specific permissions\n")
				.append(prefix + "[setprefix] <prefix> - sets the prefix for this guild\n")
				.append("```")
				.build();
		
		channel.sendMessage(message).queue();
		return true;
	}

}
