package io.github.juanmafx.p3exercises.domain.statistics;

import java.util.OptionalDouble;

public final class RunningStatistics {

    private long count;
    private double mean;
    private double sumOfSquaredDeviations;

    public void add(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("value must be finite");
        }
        count++;
        double deltaBefore = value - mean;
        mean += deltaBefore / count;
        sumOfSquaredDeviations += deltaBefore * (value - mean);
    }

    public long count() {
        return count;
    }

    public OptionalDouble mean() {
        return count == 0 ? OptionalDouble.empty() : OptionalDouble.of(mean);
    }

    public OptionalDouble sampleVariance() {
        return count < 2 ? OptionalDouble.empty() : OptionalDouble.of(sumOfSquaredDeviations / (count - 1));
    }

    public OptionalDouble sampleStandardDeviation() {
        OptionalDouble variance = sampleVariance();
        return variance.isPresent() ? OptionalDouble.of(Math.sqrt(variance.getAsDouble())) : OptionalDouble.empty();
    }
}
