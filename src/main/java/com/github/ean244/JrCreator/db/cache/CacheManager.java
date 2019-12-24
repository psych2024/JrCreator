package com.github.ean244.JrCreator.db.cache;

import java.util.HashMap;
import java.util.Map;

import com.github.ean244.JrCreator.guild.GuildConfig;

public class CacheManager {
	private static CacheManager instance;
	private final Map<Long, GuildConfig> configs;
	
	private CacheManager() {
		this.configs = new HashMap<>();
	}
	
	public void cache(GuildConfig config) {
		configs.put(config.getId(), config);
	}
	
	public GuildConfig getCache(GuildConfig config) {
		return configs.get(config);
	}
	
	public boolean isCached(long id) {
		return configs.containsKey(id);
	}
	
	public static CacheManager getInstance() {
		if(instance == null) {
			instance = new CacheManager();
		}
		
		return instance;
	}
}