package com.github.ean244.jrcreator.commands.user;

import com.github.ean244.jrcreator.commands.CommandMeta;
import com.github.ean244.jrcreator.commands.Commands;
import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = {  }, name = "gitlab", permission = PermissionLevel.USER)
public class GitlabCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if(args.length != 0)
			return false;
		
		channel.sendMessage("https://gitlab.com/icreatorz").queue();
		return true;
	}

}
