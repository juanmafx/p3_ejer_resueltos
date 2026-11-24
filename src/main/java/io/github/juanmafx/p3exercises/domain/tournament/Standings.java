package io.github.juanmafx.p3exercises.domain.tournament;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Standings {

    public static final int POINTS_FOR_WIN = 3;
    public static final int POINTS_FOR_DRAW = 1;

    public static final Comparator<StandingRow> SPORTING_ORDER = Comparator
            .comparingInt(StandingRow::points).reversed()
            .thenComparing(Comparator.comparingInt(StandingRow::goalDifference).reversed())
            .thenComparing(Comparator.comparingInt(StandingRow::goalsFor).reversed());

    private Standings() {
    }

    public static List<StandingRow> compute(List<String> teams, Collection<MatchResult> results) {
        Map<String, Tally> tallies = new LinkedHashMap<>();
        TeamRoster.validated(teams).forEach(team -> tallies.put(team, new Tally(team)));
        for (MatchResult result : results) {
            Tally home = tallies.get(result.home());
            Tally away = tallies.get(result.away());
            if (home == null || away == null) {
                throw new IllegalArgumentException("result references a team outside the roster");
            }
            home.record(result.homeGoals(), result.awayGoals());
            away.record(result.awayGoals(), result.homeGoals());
        }
        List<StandingRow> unranked = new ArrayList<>(tallies.size());
        tallies.values().forEach(tally -> unranked.add(tally.toRow(0)));
        unranked.sort(SPORTING_ORDER.thenComparing(StandingRow::team));
        List<StandingRow> ranked = new ArrayList<>(unranked.size());
        for (int index = 0; index < unranked.size(); index++) {
            ranked.add(tallies.get(unranked.get(index).team()).toRow(index + 1));
        }
        return List.copyOf(ranked);
    }

    private static final class Tally {
        private final String team;
        private int won;
        private int drawn;
        private int lost;
        private int goalsFor;
        private int goalsAgainst;

        private Tally(String team) {
            this.team = team;
        }

        private void record(int scored, int conceded) {
            goalsFor += scored;
            goalsAgainst += conceded;
            if (scored > conceded) {
                won++;
            } else if (scored == conceded) {
                drawn++;
            } else {
                lost++;
            }
        }

        private StandingRow toRow(int position) {
            return new StandingRow(
                    position,
                    team,
                    won + drawn + lost,
                    won,
                    drawn,
                    lost,
                    goalsFor,
                    goalsAgainst,
                    won * POINTS_FOR_WIN + drawn * POINTS_FOR_DRAW);
        }
    }
}
