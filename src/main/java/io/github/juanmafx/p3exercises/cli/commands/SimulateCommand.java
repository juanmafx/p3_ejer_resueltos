package io.github.juanmafx.p3exercises.cli.commands;

import io.github.juanmafx.p3exercises.cli.Arguments;
import io.github.juanmafx.p3exercises.cli.Command;
import io.github.juanmafx.p3exercises.cli.CommandContext;
import io.github.juanmafx.p3exercises.domain.tournament.SimulationConfig;
import io.github.juanmafx.p3exercises.domain.tournament.TeamStrength;
import io.github.juanmafx.p3exercises.domain.tournament.TitleOdds;
import io.github.juanmafx.p3exercises.domain.tournament.TournamentSimulator;
import java.util.List;
import java.util.Locale;

final class SimulateCommand implements Command {

    private static final int FIXED_ARGUMENTS = 3;
    private static final int MAX_TEAMS = 64;
    private static final int MAX_CLI_RUNS = 100_000;
    private static final long MAX_SIMULATED_MATCHES = 20_000_000L;

    @Override
    public String name() {
        return "simulate";
    }

    @Override
    public String usage() {
        return "simulate <seed> <runs> <homeAdvantage> <team:goalRate> <team:goalRate>...";
    }

    @Override
    public void execute(List<String> args, CommandContext context) {
        Arguments.requireCount(args, FIXED_ARGUMENTS + 2, FIXED_ARGUMENTS + MAX_TEAMS);
        int runs = Arguments.parseInt(args.get(1), "runs");
        if (runs > MAX_CLI_RUNS) {
            throw new IllegalArgumentException("runs must be at most " + MAX_CLI_RUNS);
        }
        List<TeamStrength> teams = args.subList(FIXED_ARGUMENTS, args.size()).stream()
                .map(SimulateCommand::parseTeam)
                .toList();
        if ((long) runs * teams.size() * (teams.size() - 1) > MAX_SIMULATED_MATCHES) {
            throw new IllegalArgumentException("simulation exceeds " + MAX_SIMULATED_MATCHES + " matches");
        }
        SimulationConfig config = new SimulationConfig(
                teams,
                Arguments.parseDouble(args.get(2), "homeAdvantage"),
                runs,
                Arguments.parseLong(args.get(0), "seed"));
        context.out().println("team,titles,probability,ci95_lower,ci95_upper");
        for (TitleOdds odds : TournamentSimulator.estimateTitleOdds(config)) {
            context.out().println(String.format(Locale.ROOT, "%s,%d,%.4f,%.4f,%.4f",
                    csvCell(odds.team()), odds.titles(), odds.probability(),
                    odds.interval95().lower(), odds.interval95().upper()));
        }
    }

    private static TeamStrength parseTeam(String token) {
        String[] pair = Arguments.splitPair(token, "team");
        return new TeamStrength(pair[0], Arguments.parseDouble(pair[1], "goalRate"));
    }

    private static String csvCell(String value) {
        boolean formulaLike = "=+-@".indexOf(value.charAt(0)) >= 0;
        String guarded = formulaLike ? "'" + value : value;
        return guarded.contains(",") || guarded.contains("\"")
                ? "\"" + guarded.replace("\"", "\"\"") + "\""
                : guarded;
    }
}
