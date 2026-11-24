package io.github.juanmafx.p3exercises.domain.tournament;

import io.github.juanmafx.p3exercises.support.Validation;

public record TeamStrength(String team, double goalRate) {

    public static final double MAX_GOAL_RATE = 10.0;

    public TeamStrength {
        team = Validation.requireLabel(team, "team");
        Validation.requireOpenClosedRange(goalRate, 0, MAX_GOAL_RATE, "goalRate");
    }
}
