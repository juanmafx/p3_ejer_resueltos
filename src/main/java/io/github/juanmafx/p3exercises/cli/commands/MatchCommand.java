package io.github.juanmafx.p3exercises.cli.commands;

import io.github.juanmafx.p3exercises.cli.Arguments;
import io.github.juanmafx.p3exercises.cli.Command;
import io.github.juanmafx.p3exercises.cli.CommandContext;
import io.github.juanmafx.p3exercises.domain.tournament.MatchResult;
import java.util.List;

final class MatchCommand implements Command {

    @Override
    public String name() {
        return "match";
    }

    @Override
    public String usage() {
        return "match <homeTeam> <homeGoals> <awayTeam> <awayGoals>";
    }

    @Override
    public void execute(List<String> args, CommandContext context) {
        Arguments.requireCount(args, 4, 4);
        MatchResult result = new MatchResult(
                args.get(0),
                args.get(2),
                Arguments.parseInt(args.get(1), "homeGoals"),
                Arguments.parseInt(args.get(3), "awayGoals"));
        context.out().println(result.winner().map(team -> "Winner: " + team).orElse("Draw"));
    }
}
