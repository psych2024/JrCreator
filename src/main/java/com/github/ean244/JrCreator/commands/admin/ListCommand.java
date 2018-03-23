package com.github.ean244.jrcreator.commands.admin;

import com.github.ean244.jrcreator.commands.CommandMeta;
import com.github.ean244.jrcreator.commands.Commands;
import com.github.ean244.jrcreator.db.impl.PermissionsImpl;
import com.github.ean244.jrcreator.main.JrCreator;
import com.github.ean244.jrcreator.perms.PermissionLevel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta
(aliases = {},
name = "list",
permission = PermissionLevel.ADMIN)
public class ListCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (args.length != 1)
			return false;

		if (!(args[0].equalsIgnoreCase("user") || args[0].equalsIgnoreCase("dj") || args[0].equalsIgnoreCase("admin")))
			return false;

		channel.sendMessage("**" + args[0].toLowerCase() + "**:\n").queue();
		new PermissionsImpl().requestCategory(guild, PermissionLevel.of(args[0]))
				.forEach(id -> channel.sendMessage("- " + JrCreator.getJda().getUserById(id).getName()).queue());

		return true;
	}
}
