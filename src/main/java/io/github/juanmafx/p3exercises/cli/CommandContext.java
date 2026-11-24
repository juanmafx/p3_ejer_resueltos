package io.github.juanmafx.p3exercises.cli;

import java.io.PrintStream;
import java.time.Year;

public record CommandContext(PrintStream out, Year currentYear) {
}
