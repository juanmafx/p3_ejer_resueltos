package io.github.juanmafx.p3exercises.domain.tournament;

import io.github.juanmafx.p3exercises.support.Validation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

final class TeamRoster {

    static final int MIN_TEAMS = 2;
    static final int MAX_TEAMS = 64;

    private TeamRoster() {
    }

    static List<String> validated(List<String> teams) {
        if (teams == null || teams.size() < MIN_TEAMS || teams.size() > MAX_TEAMS) {
            throw new IllegalArgumentException("teams must contain between " + MIN_TEAMS + " and " + MAX_TEAMS + " names");
        }
        Set<String> unique = new LinkedHashSet<>();
        for (String team : teams) {
            if (!unique.add(Validation.requireLabel(team, "team"))) {
                throw new IllegalArgumentException("team names must be unique");
            }
        }
        return List.copyOf(unique);
    }
}
