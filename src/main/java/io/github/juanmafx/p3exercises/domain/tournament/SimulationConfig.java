package io.github.juanmafx.p3exercises.domain.tournament;

import io.github.juanmafx.p3exercises.support.Validation;
import java.util.List;

public record SimulationConfig(List<TeamStrength> teams, double homeAdvantage, int runs, long seed) {

    public static final int MAX_RUNS = 1_000_000;
    public static final double MAX_HOME_ADVANTAGE = 3.0;

    public SimulationConfig {
        teams = List.copyOf(teams);
        TeamRoster.validated(teams.stream().map(TeamStrength::team).toList());
        Validation.requireOpenClosedRange(homeAdvantage, 0, MAX_HOME_ADVANTAGE, "homeAdvantage");
        Validation.requireRange(runs, 1, MAX_RUNS, "runs");
    }
}
