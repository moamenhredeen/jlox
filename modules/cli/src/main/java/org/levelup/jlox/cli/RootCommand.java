package org.levelup.jlox.cli;

import picocli.CommandLine.Command;

@Command(
        name = "jlox",
        version = "0.1.0",
        helpCommand = true,
        description = "jlox is general purpose programming language",
        subcommands = {
                RunCommand.class,
                EvalCommand.class,
                ReplCommand.class,
        }
)
class RootCommand { }
