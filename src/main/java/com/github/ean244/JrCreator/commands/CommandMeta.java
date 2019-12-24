package com.github.ean244.JrCreator.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.github.ean244.JrCreator.perms.PermissionLevel;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface CommandMeta {
	public String name();

	public PermissionLevel permission();

	public String[] aliases();
}
