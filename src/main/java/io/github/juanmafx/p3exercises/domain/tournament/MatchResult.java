package io.github.juanmafx.p3exercises.domain.tournament;

import io.github.juanmafx.p3exercises.support.Validation;
import java.util.Optional;

public record MatchResult(String home, String away, int homeGoals, int awayGoals) {

    public static final int MAX_GOALS = 999;

    public MatchResult {
        Fixture fixture = new Fixture(home, away);
        home = fixture.home();
        away = fixture.away();
        Validation.requireRange(homeGoals, 0, MAX_GOALS, "homeGoals");
        Validation.requireRange(awayGoals, 0, MAX_GOALS, "awayGoals");
    }

    public MatchOutcome outcome() {
        int comparison = Integer.compare(homeGoals, awayGoals);
        if (comparison > 0) {
            return MatchOutcome.HOME_WIN;
        }
        return comparison < 0 ? MatchOutcome.AWAY_WIN : MatchOutcome.DRAW;
    }

    public Optional<String> winner() {
        return switch (outcome()) {
            case HOME_WIN -> Optional.of(home);
            case AWAY_WIN -> Optional.of(away);
            case DRAW -> Optional.empty();
        };
    }
}
