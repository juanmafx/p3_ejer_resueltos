package io.github.juanmafx.p3exercises.domain.tournament;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;

class MatchResultTest {

    @Test
    void awayTeamCanWin() {
        assertEquals(Optional.of("Away"), new MatchResult("Home", "Away", 0, 1).winner());
    }

    @Test
    void homeTeamCanWin() {
        assertEquals(Optional.of("Home"), new MatchResult("Home", "Away", 2, 1).winner());
    }

    @Test
    void drawHasNoWinner() {
        assertTrue(new MatchResult("Home", "Away", 1, 1).winner().isEmpty());
    }

    @Test
    void rejectsInvalidMatches() {
        assertThrows(IllegalArgumentException.class, () -> new MatchResult("Home", "Home", 1, 1));
        assertThrows(IllegalArgumentException.class, () -> new MatchResult("Home", "Away", -1, 1));
    }

    @Property(seed = "20260110")
    void swappingSidesPreservesWinner(
            @ForAll @IntRange(min = 0, max = 50) int homeGoals,
            @ForAll @IntRange(min = 0, max = 50) int awayGoals) {
        assertEquals(
                new MatchResult("A", "B", homeGoals, awayGoals).winner(),
                new MatchResult("B", "A", awayGoals, homeGoals).winner());
    }
}
