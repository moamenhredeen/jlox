package org.iqraalabs.jlox;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class CommandLine
{

	private Map<String, Object> commands = new HashMap<>();
	private String helpMessage;
	private Writer writer;

	/**
	 * Initializes and returns a new instance of CommandLineBuilder.
	 *
	 * @return a new instance of CommandLineBuilder
	 */
	static CommandLineBuilder builder(){
		return new CommandLineBuilder();
	}

	/**
	 * Executes the command line application with the provided arguments.
	 *
	 * @param args the command line arguments to be processed
	 */
	public void run(String args[]) throws IOException
	{
		System.out.println(Arrays.asList(args));
		if (args.length == 0){
			System.out.println(this.helpMessage);
			return;
		}

		if(!this.commands.containsKey(args[0])){
			writer.write("command not found");
			writer.write(this.helpMessage);
		}

		this.runCommand(args);
	}

	private void runCommand(String[] args)
	{
		var command = this.commands.get(args[0]);
		switch (command){
			case Runnable runnable -> runnable.run();
			case Consumer consumer -> consumer.accept(Arrays.asList(args).subList(1, args.length));
			case Function function -> {
				this.commands.put(args[0], function.apply(Arrays.asList(args).subList(1, args.length)));
				this.runCommand(args);
			}
			case Class<?> clazz -> {
				try{
					var instance = clazz.getConstructor().newInstance();
					this.commands.put(args[0], instance);
					this.runCommand(args);
				}catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
					System.err.println(e.getMessage());
				}
			}
			default -> throw new IllegalStateException("Unexpected value: " + command);
		}
	}


	public static class CommandLineBuilder{

		private final CommandLine commandLine;

		CommandLineBuilder()
		{
			this.commandLine = new CommandLine();
		}

		/**
		 * Adds a command along with its handler to the command line.
		 *
		 * @param command the name of the command to add
		 * @param handler the handler for the command which accepts a list of strings as arguments
		 * @return the current instance of {@code CommandLineBuilder}
		 */
		CommandLineBuilder command(String command, Consumer<List<String>> handler)
		{
			this.commandLine.commands.put(command, handler);
			return this;
		}

		/**
		 * Adds a simple command along with its handler to the command line.
		 *
		 * @param command the name of the command to add
		 * @param handler the handler for the command which is a Runnable
		 * @return the current instance of {@code CommandLineBuilder}
		 */
		CommandLineBuilder command(String command, Runnable handler)
		{
			this.commandLine.commands.put(command, handler);
			return this;
		}

		/**
		 * Adds a command along with its handler class to the command line.
		 *
		 * @param command the name of the command to add
		 * @param handler the class of the handler for the command which is a Runnable
		 * @return the current instance of {@code CommandLineBuilder}
		 */
		CommandLineBuilder commandr(String command, Class<? extends Runnable> handler)
		{
			this.commandLine.commands.put(command, handler);
			return this;
		}

		/**
		 * Adds a command along with its handler class to the command line.
		 *
		 * @param command the name of the command to add
		 * @param handler the class of the handler for the command which accepts a list of strings as arguments
		 * @return the current instance of {@code CommandLineBuilder}
		 */
		CommandLineBuilder command(String command, Class<? extends Consumer<List<String>>> handler)
		{
			this.commandLine.commands.put(command, handler);
			return this;
		}

		/**
		 * Adds a command with a factory function to the command line.
		 *
		 * @param command the name of the command to add
		 * @param factory the factory function for the command which accepts a list of strings as arguments and returns an object*/
		CommandLineBuilder command(String command, Function<List<String>, Object> factory)
		{
			this.commandLine.commands.put(command, factory);
			return this;
		}

		/**
		 * Adds a help message to the command line for a specific command.
		 *
		 * @param command the name of the command for which help is to be added
		 * @param helpMessage the help message to be displayed when the command is invoked
		 * @return the current instance of {@code CommandLineBuilder}
		 */
		CommandLineBuilder help(String command, String helpMessage){
			this.commandLine.helpMessage = helpMessage;
			this.commandLine.commands.put(command, (Runnable)() -> System.out.println(helpMessage));
			return this;
		}

		/**
		 * Sets the writer for the command line output.
		 *
		 * @param writer the Writer to be used for command line output
		 * @return the current instance of {@code CommandLineBuilder}
		 */
		CommandLineBuilder writer(Writer writer)
		{
			this.commandLine.writer = writer;
			return this;
		}

		/**
		 * Builds and returns the configured CommandLine instance.
		 *
		 * This method ensures that the CommandLine instance has a valid help message before returning it.
		 * If the help message has not been set, it throws an IllegalStateException.
		 *
		 * @return the configured CommandLine instance
		 * @throws IllegalStateException if the help message is not set
		 */
		CommandLine build(){
			if(this.commandLine.helpMessage == null){
				throw new IllegalStateException("you can not build a command line without help message");
			}
			return this.commandLine;
		}

	}
}
