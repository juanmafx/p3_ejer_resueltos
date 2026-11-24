package io.github.juanmafx.p3exercises.cli;

import io.github.juanmafx.p3exercises.cli.commands.CommandRegistry;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class Application {

    public static final int EXIT_SUCCESS = 0;
    public static final int EXIT_USAGE = 2;

    private Application() {
    }

    public static void main(String[] args) {
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        PrintStream err = new PrintStream(System.err, true, StandardCharsets.UTF_8);
        System.exit(run(args, out, err, Clock.systemUTC()));
    }

    public static int run(String[] args, PrintStream out, PrintStream err, Clock clock) {
        if (args.length == 0 || args[0].equals("help") || args[0].equals("--help")) {
            printHelp(args.length == 0 ? err : out);
            return args.length == 0 ? EXIT_USAGE : EXIT_SUCCESS;
        }
        Optional<Command> command = CommandRegistry.find(args[0]);
        if (command.isEmpty()) {
            err.println("error: unknown command");
            printHelp(err);
            return EXIT_USAGE;
        }
        List<String> commandArgs = Arrays.asList(args).subList(1, args.length);
        try {
            command.get().execute(commandArgs, new CommandContext(out, Year.now(clock)));
            return EXIT_SUCCESS;
        } catch (IllegalArgumentException exception) {
            err.println("error: " + exception.getMessage());
            err.println("usage: " + command.get().usage());
            return EXIT_USAGE;
        }
    }

    private static void printHelp(PrintStream stream) {
        stream.println("usage: p3-exercises <command> [arguments]");
        CommandRegistry.all().forEach(command -> stream.println("  " + command.usage()));
    }
}
