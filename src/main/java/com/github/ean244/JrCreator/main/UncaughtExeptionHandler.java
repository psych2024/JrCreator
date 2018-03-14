package com.github.ean244.jrcreator.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UncaughtExeptionHandler implements Thread.UncaughtExceptionHandler{

	private static final Logger LOGGER = LoggerFactory.getLogger(UncaughtExeptionHandler.class);
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		LOGGER.error("Thread {} threw an error: ",t.getName() , e);
	}

}
