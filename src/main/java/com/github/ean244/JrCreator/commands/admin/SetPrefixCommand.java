package com.github.ean244.JrCreator.commands.admin;

import com.github.ean244.JrCreator.commands.CommandMeta;
import com.github.ean244.JrCreator.commands.Commands;
import com.github.ean244.JrCreator.db.impl.PrefixImpl;
import com.github.ean244.JrCreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = {}, name = "setprefix", permission = PermissionLevel.ADMIN)
public class SetPrefixCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (args.length == 1) {
			new PrefixImpl().update(guild, args[0]);
			channel.sendMessage(String.format("Updated **PREFIX** for this guild to `%s`", args[0])).queue();
			return true;
		}
		return false;
	}

}
