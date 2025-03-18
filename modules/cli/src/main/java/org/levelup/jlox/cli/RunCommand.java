package org.levelup.jlox.cli;

import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(
        name = "run",
        description = "run jlox file"
)
public class RunCommand implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
