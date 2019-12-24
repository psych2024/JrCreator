package com.github.ean244.JrCreator.commands.user;

import com.github.ean244.JrCreator.commands.CommandMeta;
import com.github.ean244.JrCreator.commands.Commands;
import com.github.ean244.JrCreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta
(aliases = { },
name = "github",
permission = PermissionLevel.USER)
public class GithubCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if(args.length != 0)
			return false;
		
		channel.sendMessage("https://github.com/Ean244/JrCreator").queue();
		return true;
	}

}
