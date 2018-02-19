package com.github.ean244.jrcreator.perms;

public enum PermissionLevel {
	ADMIN(2), DJ(1), USER(0);
	
	private final int i;
	
	private PermissionLevel(int i) {
		this.i = i;
	}
	
	public int getLevel() {
		return i;
	}
}
