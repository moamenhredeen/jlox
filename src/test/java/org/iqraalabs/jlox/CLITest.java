package org.iqraalabs.jlox;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

class CLITest
{

	@Test
	void provide_no_argument() throws IOException
	{
		var writer = new StringWriter();
		var cli = new CLI(new String[]{}, writer);
		cli.run();

		var message = writer.toString();
		message.equals("""
				print help message
				""");
	}


	@Test
	void help_option() throws IOException
	{
		var writer = new StringWriter();
		var cli = new CLI(new String[]{"-h"}, writer);
		cli.run();

		var message = writer.getBuffer().toString();
		message.equals("""
				print help message
				""");
	}

	@Test
	void print_cli_version() throws IOException
	{
		var writer = new StringWriter();
		var cli = new CLI(new String[]{"-v"}, writer);
		cli.run();

		var message = writer.getBuffer().toString();
		message.equals("""
				print app version
				""");
	}
}
