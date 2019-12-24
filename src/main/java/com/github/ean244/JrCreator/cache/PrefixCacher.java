package com.github.ean244.JrCreator.cache;

public class PrefixCacher extends Cacher<String> {

	private static PrefixCacher instance;
	
	public static PrefixCacher getInstance() {
		if(instance == null)
			instance = new PrefixCacher();
		
		return instance;
	}
}
