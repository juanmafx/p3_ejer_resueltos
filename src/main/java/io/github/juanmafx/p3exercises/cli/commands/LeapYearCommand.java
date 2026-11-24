package io.github.juanmafx.p3exercises.cli.commands;

import io.github.juanmafx.p3exercises.cli.Arguments;
import io.github.juanmafx.p3exercises.cli.Command;
import io.github.juanmafx.p3exercises.cli.CommandContext;
import io.github.juanmafx.p3exercises.domain.calendar.GregorianCalendarMath;
import java.util.List;

final class LeapYearCommand implements Command {

    private static final int MAX_YEARS = 1_000;

    @Override
    public String name() {
        return "leap-year";
    }

    @Override
    public String usage() {
        return "leap-year <year>...";
    }

    @Override
    public void execute(List<String> args, CommandContext context) {
        Arguments.requireCount(args, 1, MAX_YEARS);
        int[] years = args.stream().mapToInt(token -> Arguments.parseInt(token, "year")).toArray();
        for (int year : years) {
            String verdict = GregorianCalendarMath.isLeapYear(year) ? "is" : "is not";
            context.out().println(year + " " + verdict + " a leap year");
        }
    }
}
