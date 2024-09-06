package org.iqraalabs.jlox;

import java.io.*;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		var writer = new BufferedWriter(new OutputStreamWriter(System.out));
		var cli = new CLI(args, writer);
		cli.run();
	}
}
