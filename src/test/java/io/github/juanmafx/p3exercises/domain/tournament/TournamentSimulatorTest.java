package io.github.juanmafx.p3exercises.domain.tournament;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.IntStream;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;

class TournamentSimulatorTest {

    private static final List<TeamStrength> EQUAL_TEAMS = List.of(
            new TeamStrength("A", 1.5),
            new TeamStrength("B", 1.5),
            new TeamStrength("C", 1.5),
            new TeamStrength("D", 1.5));

    @Test
    void sameSeedReproducesExactly() {
        SimulationConfig config = new SimulationConfig(EQUAL_TEAMS, 1.2, 500, 42L);
        assertEquals(
                TournamentSimulator.estimateTitleOdds(config),
                TournamentSimulator.estimateTitleOdds(config));
    }

    @Test
    void differentSeedsDiverge() {
        assertNotEquals(
                TournamentSimulator.estimateTitleOdds(new SimulationConfig(EQUAL_TEAMS, 1.2, 2000, 1L)),
                TournamentSimulator.estimateTitleOdds(new SimulationConfig(EQUAL_TEAMS, 1.2, 2000, 2L)));
    }

    @Test
    void equalTeamsHaveEqualOddsWithinBinomialError() {
        int runs = 20_000;
        double expected = 0.25;
        double tolerance = 4 * Math.sqrt(expected * (1 - expected) / runs);
        List<TitleOdds> odds = TournamentSimulator.estimateTitleOdds(
                new SimulationConfig(EQUAL_TEAMS, 1.2, runs, 20260117L));
        odds.forEach(entry -> assertEquals(expected, entry.probability(), tolerance, entry.team()));
    }

    @Test
    void strongerTeamWinsMoreOften() {
        List<TeamStrength> teams = List.of(
                new TeamStrength("Strong", 3.0),
                new TeamStrength("Weak1", 1.0),
                new TeamStrength("Weak2", 1.0));
        List<TitleOdds> odds = TournamentSimulator.estimateTitleOdds(
                new SimulationConfig(teams, 1.0, 3000, 20260118L));
        assertEquals("Strong", odds.get(0).team());
        assertTrue(odds.get(0).interval95().lower() > 0.5);
    }

    @Test
    void rejectsInvalidConfiguration() {
        assertThrows(IllegalArgumentException.class, () -> new SimulationConfig(EQUAL_TEAMS, 0, 10, 1));
        assertThrows(IllegalArgumentException.class, () -> new SimulationConfig(EQUAL_TEAMS, 1, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> new SimulationConfig(
                List.of(new TeamStrength("A", 1), new TeamStrength("A", 2)), 1, 10, 1));
        assertThrows(IllegalArgumentException.class, () -> new TeamStrength("A", 0));
    }

    @Test
    void configurationEnforcesTotalMatchBudget() {
        List<TeamStrength> teams = IntStream.range(0, 64)
                .mapToObj(index -> new TeamStrength("Team" + index, 1))
                .toList();
        assertThrows(IllegalArgumentException.class, () -> new SimulationConfig(teams, 1, 1_000_000, 1));
    }

    @Property(tries = 25, seed = "20260119")
    void titlesSumToRuns(@ForAll @IntRange(min = 1, max = 300) int runs, @ForAll long seed) {
        List<TitleOdds> odds = TournamentSimulator.estimateTitleOdds(
                new SimulationConfig(EQUAL_TEAMS, 1.1, runs, seed));
        assertEquals(runs, odds.stream().mapToInt(TitleOdds::titles).sum());
        assertEquals(1.0, odds.stream().mapToDouble(TitleOdds::probability).sum(), 1e-9);
        odds.forEach(entry -> assertTrue(
                entry.interval95().lower() <= entry.probability()
                        && entry.probability() <= entry.interval95().upper()));
    }
}
