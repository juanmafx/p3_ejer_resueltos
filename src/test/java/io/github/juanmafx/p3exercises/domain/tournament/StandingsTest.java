package io.github.juanmafx.p3exercises.domain.tournament;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;

class StandingsTest {

    private static final List<String> TEAMS = List.of("A", "B", "C", "D");

    @Test
    void ranksByPointsThenGoalDifferenceThenGoalsFor() {
        List<StandingRow> table = Standings.compute(List.of("A", "B", "C"), List.of(
                new MatchResult("A", "B", 3, 0),
                new MatchResult("B", "C", 1, 0),
                new MatchResult("C", "A", 2, 0)));
        assertEquals(List.of("A", "C", "B"), table.stream().map(StandingRow::team).toList());
        assertEquals(1, table.get(0).position());
        assertEquals(3, table.get(0).points());
        assertEquals(1, table.get(0).goalDifference());
    }

    @Test
    void rejectsUnknownTeam() {
        assertThrows(IllegalArgumentException.class, () ->
                Standings.compute(List.of("A", "B"), List.of(new MatchResult("A", "Z", 1, 0))));
    }

    @Property(tries = 300, seed = "20260114")
    void tableInvariantsHold(@ForAll @IntRange(min = 0, max = 1_000_000) int seed) {
        SplittableRandom random = new SplittableRandom(seed);
        List<MatchResult> results = new ArrayList<>();
        int draws = 0;
        for (List<Fixture> round : RoundRobinScheduler.doubleRoundRobin(TEAMS)) {
            for (Fixture fixture : round) {
                MatchResult result = new MatchResult(
                        fixture.home(), fixture.away(), random.nextInt(6), random.nextInt(6));
                draws += result.outcome() == MatchOutcome.DRAW ? 1 : 0;
                results.add(result);
            }
        }
        List<StandingRow> table = Standings.compute(TEAMS, results);
        assertEquals(0, table.stream().mapToInt(StandingRow::goalDifference).sum());
        assertEquals(2 * results.size(), table.stream().mapToInt(StandingRow::played).sum());
        assertEquals(
                3 * (results.size() - draws) + 2 * draws,
                table.stream().mapToInt(StandingRow::points).sum());
        for (int index = 1; index < table.size(); index++) {
            assertTrue(Standings.SPORTING_ORDER.compare(table.get(index - 1), table.get(index)) <= 0);
            assertEquals(index + 1, table.get(index).position());
        }
    }
}
