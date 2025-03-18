package org.levelup.jlox.cli;

import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        var commandline = new CommandLine(RootCommand.class);
        commandline.execute(args);
    }
}
