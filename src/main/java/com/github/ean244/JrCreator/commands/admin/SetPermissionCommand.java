package com.github.ean244.jrcreator.commands.admin;

import java.util.HashSet;
import java.util.Set;

import com.github.ean244.jrcreator.commands.CommandMeta;
import com.github.ean244.jrcreator.commands.Commands;
import com.github.ean244.jrcreator.db.impl.PermissionsImpl;
import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = { "setperms", "setpermissions" }, name = "setpermission", permission = PermissionLevel.DJ)
public class SetPermissionCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (args.length != 2)
			return false;

		if (!(args[1].equalsIgnoreCase("user") || args[1].equalsIgnoreCase("dj") || args[1].equalsIgnoreCase("admin")))
			return false;

		Set<Member> members = new HashSet<>();

		guild.getMembersByName(args[0], true).forEach(members::add);
		guild.getMembersByNickname(args[0], true).forEach(members::add);

		if (members.size() > 1) {
			channel.sendMessage("More than one members found. Please type a specific name.").queue();
			return true;
		}

		if (members.isEmpty()) {
			channel.sendMessage("No user with name `" + args[0] + "` found").queue();
			return true;
		}

		PermissionLevel level = PermissionLevel.of(args[1]);
		new PermissionsImpl().update(guild, members.iterator().next(), level);
		channel.sendMessage("Successfully set permissions for `" + members.iterator().next().getEffectiveName()
				+ "` to `" + level.toString().toLowerCase() + "`").queue();
		return true;
	}

}
