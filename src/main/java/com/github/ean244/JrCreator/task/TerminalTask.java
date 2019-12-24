package com.github.ean244.JrCreator.task;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;

public class TerminalTask implements Runnable {

	private final Terminal terminal;

	public TerminalTask(Terminal terminal) {
		this.terminal = terminal;
	}

	@Override
	public void run() {
		LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();

		while (true) {
			String input = reader.readLine(">");

			if ("stop".equalsIgnoreCase(input)) {
				
			}

			if ("reload".equalsIgnoreCase(input)) {
				
			}
		}
	}

}
