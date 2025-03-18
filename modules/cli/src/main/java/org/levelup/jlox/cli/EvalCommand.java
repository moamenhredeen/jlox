package org.levelup.jlox.cli;

import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(
        name = "eval",
        description = "evaluate jlox script"
)
public class EvalCommand implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
