package io.github.juanmafx.p3exercises.domain.tournament;

import io.github.juanmafx.p3exercises.support.Validation;

public record Fixture(String home, String away) {

    public Fixture {
        home = Validation.requireLabel(home, "home");
        away = Validation.requireLabel(away, "away");
        if (home.equals(away)) {
            throw new IllegalArgumentException("a team cannot play itself");
        }
    }

    public Fixture reversed() {
        return new Fixture(away, home);
    }
}
