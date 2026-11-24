package io.github.juanmafx.p3exercises.domain.tournament;

import io.github.juanmafx.p3exercises.support.Validation;
import java.util.SplittableRandom;

public final class PoissonSampler {

    public static final double MAX_RATE = 30.0;

    private PoissonSampler() {
    }

    public static int sample(double rate, SplittableRandom random) {
        Validation.requireOpenClosedRange(rate, 0, MAX_RATE, "rate");
        double threshold = Math.exp(-rate);
        double product = random.nextDouble();
        int events = 0;
        while (product > threshold) {
            events++;
            product *= random.nextDouble();
        }
        return events;
    }
}
