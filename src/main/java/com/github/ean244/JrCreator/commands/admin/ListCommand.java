package com.github.ean244.jrcreator.commands.admin;

import java.util.Set;

import com.github.ean244.jrcreator.commands.CommandMeta;
import com.github.ean244.jrcreator.commands.Commands;
import com.github.ean244.jrcreator.db.impl.PermissionsImpl;
import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = {}, name = "list", permission = PermissionLevel.ADMIN)
public class ListCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (args.length != 1)
			return false;

		if (!(args[0].equalsIgnoreCase("user") || args[0].equalsIgnoreCase("dj") || args[0].equalsIgnoreCase("admin")))
			return false;

		Set<Long> users = new PermissionsImpl().request(guild, PermissionLevel.of(args[0]));
		
		MessageBuilder builder = new MessageBuilder("**" + args[0].toLowerCase() + "**:\n");
		
		if(users.isEmpty()) {
			builder.append("none").sendTo(channel).queue();
			return true;
		}
		
		users.forEach(id -> builder.append("- " + guild.getMemberById(id).getEffectiveName() + "\n"));
		builder.sendTo(channel).queue();
		return true;
	}
}
