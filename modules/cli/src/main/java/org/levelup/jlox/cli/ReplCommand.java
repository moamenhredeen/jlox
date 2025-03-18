package org.levelup.jlox.cli;

import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(
        name = "repl",
        description = "starts jlox interactive repl"
)
public class ReplCommand implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
