package com.github.ean244.jrcreator.db.api;

import net.dv8tion.jda.core.entities.Guild;

public interface PrefixUpdate {
	public void update(Guild guild, String prefix);
}
