package com.github.ean244.jrcreator.db.api;

import java.util.Set;

import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

public interface PermRequest {
	public Set<Long> requestCategory(Guild guild, PermissionLevel level);
	
	public PermissionLevel requestIndividual(Guild guild, Member member);
}
