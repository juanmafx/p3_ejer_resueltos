package io.github.juanmafx.p3exercises.domain.tournament;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;

class RoundRobinSchedulerTest {

    private static List<String> teams(int size) {
        return IntStream.range(0, size).mapToObj(index -> "Team" + index).toList();
    }

    @Test
    void rejectsInvalidRosters() {
        assertThrows(IllegalArgumentException.class, () -> RoundRobinScheduler.singleRoundRobin(List.of("A")));
        assertThrows(IllegalArgumentException.class, () -> RoundRobinScheduler.singleRoundRobin(List.of("A", "A")));
        assertThrows(IllegalArgumentException.class, () -> RoundRobinScheduler.singleRoundRobin(teams(65)));
    }

    @Property(tries = 63, seed = "20260111")
    void everyPairMeetsExactlyOnce(@ForAll @IntRange(min = 2, max = 64) int size) {
        Set<Set<String>> pairs = new HashSet<>();
        int fixtures = 0;
        for (List<Fixture> round : RoundRobinScheduler.singleRoundRobin(teams(size))) {
            for (Fixture fixture : round) {
                pairs.add(Set.of(fixture.home(), fixture.away()));
                fixtures++;
            }
        }
        int expected = size * (size - 1) / 2;
        assertEquals(expected, fixtures);
        assertEquals(expected, pairs.size());
    }

    @Property(tries = 63, seed = "20260112")
    void noTeamPlaysTwiceInARound(@ForAll @IntRange(min = 2, max = 64) int size) {
        List<List<Fixture>> rounds = RoundRobinScheduler.singleRoundRobin(teams(size));
        assertEquals(size % 2 == 0 ? size - 1 : size, rounds.size());
        for (List<Fixture> round : rounds) {
            Set<String> seen = new HashSet<>();
            for (Fixture fixture : round) {
                assertTrue(seen.add(fixture.home()));
                assertTrue(seen.add(fixture.away()));
            }
        }
    }

    @Property(tries = 63, seed = "20260113")
    void doubleRoundRobinBalancesHomeAndAway(@ForAll @IntRange(min = 2, max = 64) int size) {
        Map<String, Integer> balance = new HashMap<>();
        Set<Fixture> fixtures = new HashSet<>();
        for (List<Fixture> round : RoundRobinScheduler.doubleRoundRobin(teams(size))) {
            for (Fixture fixture : round) {
                assertTrue(fixtures.add(fixture));
                balance.merge(fixture.home(), 1, Integer::sum);
                balance.merge(fixture.away(), -1, Integer::sum);
            }
        }
        assertEquals(size * (size - 1), fixtures.size());
        assertTrue(balance.values().stream().allMatch(value -> value == 0));
    }
}
