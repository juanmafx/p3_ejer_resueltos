package io.github.juanmafx.p3exercises.domain.tournament;

public record StandingRow(
        int position,
        String team,
        int played,
        int won,
        int drawn,
        int lost,
        int goalsFor,
        int goalsAgainst,
        int points) {

    public int goalDifference() {
        return goalsFor - goalsAgainst;
    }
}
