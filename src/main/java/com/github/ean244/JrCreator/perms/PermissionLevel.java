package com.github.ean244.jrcreator.perms;

public enum PermissionLevel {
	ADMIN(2), DJ(1), USER(0);
	
	private final int i;
	
	private PermissionLevel(int i) {
		this.i = i;
	}
	
	public int level() {
		return i;
	}
	
	public static PermissionLevel of(int i) {
		for(PermissionLevel level : PermissionLevel.values()) {
			if(level.i == i)  return level;
		}
		
		throw new IllegalArgumentException("Invalid level!");
	}
	
	public static PermissionLevel of(String s) {
		for(PermissionLevel level : PermissionLevel.values()) {
			if(level.toString().equalsIgnoreCase(s))  return level;
		}
		
		throw new IllegalArgumentException("Invalid level!");
	}
}
