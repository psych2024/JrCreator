package com.github.ean244.jrcreator.commands.admin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.github.ean244.jrcreator.commands.CommandMeta;
import com.github.ean244.jrcreator.commands.Commands;
import com.github.ean244.jrcreator.db.impl.PermissionsImpl;
import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = { "setperms", "setpermissions" }, name = "setpermission", permission = PermissionLevel.ADMIN)
public class SetPermissionCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (!(args[args.length - 1].equalsIgnoreCase("user") || args[args.length - 1].equalsIgnoreCase("dj") || args[args.length - 1].equalsIgnoreCase("admin")))
			return false;

		Set<Member> members = new HashSet<>();

		StringBuilder builder = new StringBuilder();
		Arrays.stream(Arrays.copyOfRange(args, 0, args.length - 1)).forEach(s -> builder.append(s + " "));
		String search = builder.toString().trim();

		members.addAll(guild.getMembersByName(search, true));
		members.addAll(guild.getMembersByNickname(search, true));

		if (members.size() > 1) {
			channel.sendMessage("More than one members found. Please type a specific name.").queue();
			return true;
		}


		if (members.isEmpty()) {
			channel.sendMessage("No user with name `" + search + "` found").queue();
			return true;
		}

		PermissionLevel level = PermissionLevel.of(args[args.length - 1]);
		new PermissionsImpl().update(guild, members.iterator().next(), level);
		channel.sendMessage("Successfully set permissions for `" + members.iterator().next().getEffectiveName()
				+ "` to `" + level.toString().toLowerCase() + "`").queue();
		return true;
	}

}
