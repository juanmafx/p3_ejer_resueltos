package io.github.juanmafx.p3exercises.cli.commands;

import io.github.juanmafx.p3exercises.cli.Arguments;
import io.github.juanmafx.p3exercises.cli.Command;
import io.github.juanmafx.p3exercises.cli.CommandContext;
import io.github.juanmafx.p3exercises.domain.controlflow.LoopTrace;
import java.util.List;

final class LoopTraceCommand implements Command {

    @Override
    public String name() {
        return "loop-trace";
    }

    @Override
    public String usage() {
        return "loop-trace [upperExclusive continueAt breakAt]";
    }

    @Override
    public void execute(List<String> args, CommandContext context) {
        if (!args.isEmpty() && args.size() != 3) {
            throw new IllegalArgumentException("wrong number of arguments");
        }
        List<String> events = args.isEmpty()
                ? LoopTrace.trace(5, 2, 3)
                : LoopTrace.trace(
                        Arguments.parseInt(args.get(0), "upperExclusive"),
                        Arguments.parseInt(args.get(1), "continueAt"),
                        Arguments.parseInt(args.get(2), "breakAt"));
        events.forEach(context.out()::println);
    }
}
