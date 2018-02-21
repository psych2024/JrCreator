package com.github.ean244.jrcreator.guild;

import java.util.HashSet;
import java.util.Set;

public class GuildPerms {
	private final Set<Long> djs;
	private final Set<Long> admins;
	
	public GuildPerms() {
		this.djs = new HashSet<>();
		this.admins = new HashSet<>();
	}
	
	public Set<Long> getDjs() {
		return djs;
	}
	
	public Set<Long> getAdmins() {
		return admins;
	}
}
