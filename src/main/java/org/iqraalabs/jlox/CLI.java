package org.iqraalabs.jlox;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CLI
{
	Writer _writer;
	private final String[] _args;
	private final Set<String> _options;
	private Optional<String> script;

	public CLI(String[] args, Writer writer){
		this._args = args;
		this._writer = writer;

		this._options = Arrays.stream(args)
				.filter(el -> el.startsWith("-") || el.startsWith("--"))
				.collect(Collectors.toSet());
		this.script = Arrays.stream(args)
				.filter(el -> !el.startsWith("-") && !el.startsWith("--"))
				.findFirst();

	}


	public void run() throws IOException
	{
		// these options have high priority
		if (option("-h") || option("--help")){
			help();
			return;
		}else if(option("-v") || option("--version")){
			version();
			return;
		}

		if (this.script.isEmpty()){
			help();
			return;
		}else{
			runScripts();
			return;
		}

	}

	private void runScripts() throws IOException
	{
		_writer.write("running scripts");
		_writer.flush();
	}

	private boolean option(String option){
		return this._options.contains(option);
	}

	private void help() throws IOException
	{
		_writer.write("""
				print help message
				""");
		_writer.flush();
	}

	private void version() throws IOException
	{
		_writer.write("""
				print app version
				""");
		_writer.flush();
	}
}
