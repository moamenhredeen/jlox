package org.iqraalabs.jlox;

import java.util.List;
import java.util.function.Consumer;

public class RunCommand implements Consumer<List<String>>
{
	@Override
	public void accept(List<String> args)
	{
		System.out.println(args);
	}
}
