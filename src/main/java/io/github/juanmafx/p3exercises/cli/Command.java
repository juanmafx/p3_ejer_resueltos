package io.github.juanmafx.p3exercises.cli;

import java.util.List;

public interface Command {

    String name();

    String usage();

    void execute(List<String> args, CommandContext context);
}
