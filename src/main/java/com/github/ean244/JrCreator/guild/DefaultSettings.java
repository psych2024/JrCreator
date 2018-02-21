package com.github.ean244.jrcreator.guild;

import java.util.HashSet;
import java.util.Set;

import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

public class DefaultSettings {
	public static final String PREFIX = "/";

	private DefaultSettings() {
	}

	public static Set<Long> defaultAdmins(Guild guild) {
		Set<Long> admins = new HashSet<>();
		admins.add(guild.getOwner().getUser().getIdLong());
		guild.getMemberCache().stream().filter(m -> m.hasPermission(Permission.ADMINISTRATOR))
				.forEach(m -> admins.add(m.getUser().getIdLong()));

		return admins;
	}

	public static Set<Long> defaultDjsAndUsers(Guild guild) {
		Set<Long> djs = new HashSet<>();
		guild.getMemberCache().stream().forEach(m -> djs.add(m.getUser().getIdLong()));
		return djs;
	}

	public static Set<Long> defaultCategory(Guild guild, PermissionLevel level) {
		if (level == PermissionLevel.ADMIN) {
			return defaultAdmins(guild);
		}

		return defaultDjsAndUsers(guild);
	}

	public static PermissionLevel defaultPermission(Member member) {
		return member.getPermissions().contains(Permission.ADMINISTRATOR) || member.isOwner() ? PermissionLevel.ADMIN : PermissionLevel.DJ;
	}
}
