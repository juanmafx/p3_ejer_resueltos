package io.github.juanmafx.p3exercises.cli.commands;

import io.github.juanmafx.p3exercises.cli.Arguments;
import io.github.juanmafx.p3exercises.cli.Command;
import io.github.juanmafx.p3exercises.cli.CommandContext;
import io.github.juanmafx.p3exercises.domain.validation.PositiveNumbers;
import java.util.List;
import java.util.OptionalDouble;

final class FirstPositiveCommand implements Command {

    private static final int MAX_VALUES = 1_000;

    @Override
    public String name() {
        return "first-positive";
    }

    @Override
    public String usage() {
        return "first-positive <number>...";
    }

    @Override
    public void execute(List<String> args, CommandContext context) {
        Arguments.requireCount(args, 1, MAX_VALUES);
        double[] values = args.stream().mapToDouble(token -> Arguments.parseDouble(token, "number")).toArray();
        OptionalDouble first = PositiveNumbers.firstStrictlyPositive(values);
        if (first.isEmpty()) {
            throw new IllegalArgumentException("no strictly positive number was provided");
        }
        context.out().println(first.getAsDouble() + " is positive");
    }
}
