package org.iqraalabs.jlox;

import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

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
