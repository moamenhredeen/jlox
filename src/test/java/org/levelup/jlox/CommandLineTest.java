package org.levelup.jlox;

import org.junit.jupiter.api.Test;

import java.io.StringWriter;

class CommandLineTest
{
	@Test
	void help_command(){
		var helpMessage = "help message";
		var commandLine = CommandLine.builder()
				.writer(new StringWriter())
				.help("help", helpMessage)
				.build();

		commandLine.run(new String[]{"help"});
	}
}
