package org.levelup.jlox;

import java.io.*;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		var writer = new BufferedWriter(new OutputStreamWriter(System.out));

		var helpMessage = """
						this a help message for jlox cli
						""";

		var commandLine = CommandLine.builder()
				.help("help", helpMessage)
				.command("version", () -> System.out.println("Jlox 1.0"))
				.command("list", () -> System.out.println("list command"))
				.command("run", RunCommand.class)
				.build();

		commandLine.run(args);
	}
}
