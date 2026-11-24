package io.github.juanmafx.p3exercises.domain.tournament;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.SplittableRandom;
import org.junit.jupiter.api.Test;

class PoissonSamplerTest {

    private static final double CHI_SQUARE_CRITICAL_DF10_P001 = 29.588;

    @Test
    void passesChiSquareGoodnessOfFit() {
        double rate = 3.0;
        int samples = 100_000;
        int bins = 11;
        long[] observed = new long[bins];
        SplittableRandom random = new SplittableRandom(20260115L);
        for (int index = 0; index < samples; index++) {
            observed[Math.min(PoissonSampler.sample(rate, random), bins - 1)]++;
        }
        double probability = Math.exp(-rate);
        double cumulative = 0;
        double statistic = 0;
        for (int bin = 0; bin < bins - 1; bin++) {
            cumulative += probability;
            double expected = probability * samples;
            statistic += Math.pow(observed[bin] - expected, 2) / expected;
            probability *= rate / (bin + 1);
        }
        double tailExpected = (1 - cumulative) * samples;
        statistic += Math.pow(observed[bins - 1] - tailExpected, 2) / tailExpected;
        assertTrue(statistic < CHI_SQUARE_CRITICAL_DF10_P001, "chi-square statistic was " + statistic);
    }

    @Test
    void sampleMeanApproximatesRate() {
        double rate = 1.7;
        int samples = 200_000;
        SplittableRandom random = new SplittableRandom(20260116L);
        long total = 0;
        for (int index = 0; index < samples; index++) {
            total += PoissonSampler.sample(rate, random);
        }
        double standardError = Math.sqrt(rate / samples);
        assertEquals(rate, total / (double) samples, 4 * standardError);
    }

    @Test
    void isDeterministicForSeed() {
        SplittableRandom first = new SplittableRandom(7);
        SplittableRandom second = new SplittableRandom(7);
        for (int index = 0; index < 1000; index++) {
            assertEquals(PoissonSampler.sample(2.5, first), PoissonSampler.sample(2.5, second));
        }
    }

    @Test
    void rejectsInvalidRates() {
        SplittableRandom random = new SplittableRandom(1);
        assertThrows(IllegalArgumentException.class, () -> PoissonSampler.sample(0, random));
        assertThrows(IllegalArgumentException.class, () -> PoissonSampler.sample(Double.NaN, random));
        assertThrows(IllegalArgumentException.class, () -> PoissonSampler.sample(31, random));
    }
}
