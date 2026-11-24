package io.github.juanmafx.p3exercises.domain.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.DoubleRange;
import net.jqwik.api.constraints.Size;
import org.junit.jupiter.api.Test;

class RunningStatisticsTest {

    @Test
    void emptyAndSingleValueStatesAreUndefined() {
        RunningStatistics statistics = new RunningStatistics();
        assertTrue(statistics.mean().isEmpty());
        statistics.add(5);
        assertEquals(5, statistics.mean().orElseThrow());
        assertTrue(statistics.sampleVariance().isEmpty());
    }

    @Test
    void rejectsNonFiniteValues() {
        RunningStatistics statistics = new RunningStatistics();
        assertThrows(IllegalArgumentException.class, () -> statistics.add(Double.NaN));
    }

    @Property(seed = "20260107")
    void matchesDirectFormulas(
            @ForAll @Size(min = 2, max = 200) List<@DoubleRange(min = -1000, max = 1000) Double> values) {
        RunningStatistics statistics = new RunningStatistics();
        values.forEach(statistics::add);
        double mean = values.stream().mapToDouble(Double::doubleValue).average().orElseThrow();
        double variance = values.stream().mapToDouble(v -> (v - mean) * (v - mean)).sum() / (values.size() - 1);
        assertEquals(mean, statistics.mean().orElseThrow(), 1e-7);
        assertEquals(variance, statistics.sampleVariance().orElseThrow(), 1e-5);
    }
}
