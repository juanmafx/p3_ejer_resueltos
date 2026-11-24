package io.github.juanmafx.p3exercises.cli.commands;

import io.github.juanmafx.p3exercises.cli.Command;
import java.util.List;
import java.util.Optional;

public final class CommandRegistry {

    private static final List<Command> COMMANDS = List.of(
            new LeapYearCommand(),
            new FirstPositiveCommand(),
            new VehicleCommand(),
            new PersonCommand(),
            new GradesCommand(),
            new MatchCommand(),
            new ScheduleCommand(),
            new SimulateCommand(),
            new LoopTraceCommand());

    private CommandRegistry() {
    }

    public static List<Command> all() {
        return COMMANDS;
    }

    public static Optional<Command> find(String name) {
        return COMMANDS.stream().filter(command -> command.name().equals(name)).findFirst();
    }
}
