package io.github.juanmafx.p3exercises.cli.commands;

import io.github.juanmafx.p3exercises.cli.Arguments;
import io.github.juanmafx.p3exercises.cli.Command;
import io.github.juanmafx.p3exercises.cli.CommandContext;
import io.github.juanmafx.p3exercises.domain.tournament.Fixture;
import io.github.juanmafx.p3exercises.domain.tournament.RoundRobinScheduler;
import java.util.List;

final class ScheduleCommand implements Command {

    @Override
    public String name() {
        return "schedule";
    }

    @Override
    public String usage() {
        return "schedule <team> <team>...";
    }

    @Override
    public void execute(List<String> args, CommandContext context) {
        Arguments.requireCount(args, 2, 64);
        List<List<Fixture>> rounds = RoundRobinScheduler.singleRoundRobin(args);
        for (int index = 0; index < rounds.size(); index++) {
            context.out().println("Round " + (index + 1));
            rounds.get(index).forEach(fixture ->
                    context.out().println("  " + fixture.home() + " vs " + fixture.away()));
        }
    }
}
