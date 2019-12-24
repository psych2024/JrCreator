package com.github.ean244.JrCreator.db.api;

import java.util.Set;

import com.github.ean244.JrCreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

public interface PermRequest {
	public Set<Long> request(Guild guild, PermissionLevel level);
	
	public PermissionLevel requestIndividual(Guild guild, Member member);
}
