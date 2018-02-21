package com.github.ean244.jrcreator.db.api;

import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

public interface PermUpdate {
	public void update(Guild guild, Member member, PermissionLevel level);
}
