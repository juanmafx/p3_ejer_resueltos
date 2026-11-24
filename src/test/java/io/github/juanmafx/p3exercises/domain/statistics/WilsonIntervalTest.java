package io.github.juanmafx.p3exercises.domain.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.SplittableRandom;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;

class WilsonIntervalTest {

    @Test
    void matchesReferenceValue() {
        WilsonInterval interval = WilsonInterval.of(50, 100, WilsonInterval.Z_95_PERCENT);
        assertEquals(0.4038, interval.lower(), 0.0001);
        assertEquals(0.5962, interval.upper(), 0.0001);
    }

    @Test
    void boundaryProportionsTouchUnitInterval() {
        assertEquals(0, WilsonInterval.of(0, 10, WilsonInterval.Z_95_PERCENT).lower());
        assertEquals(1, WilsonInterval.of(10, 10, WilsonInterval.Z_95_PERCENT).upper());
    }

    @Test
    void rejectsInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> WilsonInterval.of(1, 0, 1.96));
        assertThrows(IllegalArgumentException.class, () -> WilsonInterval.of(5, 4, 1.96));
        assertThrows(IllegalArgumentException.class, () -> WilsonInterval.of(1, 4, 0));
    }

    @Test
    void empiricalCoverageIsNearNominal() {
        SplittableRandom random = new SplittableRandom(20260108L);
        double trueProportion = 0.3;
        int trials = 200;
        int experiments = 4000;
        int covered = 0;
        for (int experiment = 0; experiment < experiments; experiment++) {
            int successes = 0;
            for (int trial = 0; trial < trials; trial++) {
                successes += random.nextDouble() < trueProportion ? 1 : 0;
            }
            WilsonInterval interval = WilsonInterval.of(successes, trials, WilsonInterval.Z_95_PERCENT);
            covered += interval.lower() <= trueProportion && trueProportion <= interval.upper() ? 1 : 0;
        }
        double coverage = covered / (double) experiments;
        assertTrue(coverage > 0.93 && coverage < 0.97, "coverage was " + coverage);
    }

    @Property(seed = "20260109")
    void intervalContainsObservedProportion(
            @ForAll @IntRange(min = 1, max = 100_000) int trials,
            @ForAll @IntRange(min = 0, max = 100_000) int rawSuccesses) {
        int successes = rawSuccesses % (trials + 1);
        WilsonInterval interval = WilsonInterval.of(successes, trials, WilsonInterval.Z_95_PERCENT);
        double proportion = successes / (double) trials;
        assertTrue(0 <= interval.lower() && interval.lower() <= proportion);
        assertTrue(proportion <= interval.upper() && interval.upper() <= 1);
    }
}
