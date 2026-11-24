package io.github.juanmafx.p3exercises.domain.person;

import io.github.juanmafx.p3exercises.support.Validation;

public record BodyMetrics(double heightMeters, double weightKilograms) {

    public static final double MAX_HEIGHT_METERS = 3.0;
    public static final double MAX_WEIGHT_KILOGRAMS = 700.0;

    public BodyMetrics {
        Validation.requireOpenClosedRange(heightMeters, 0, MAX_HEIGHT_METERS, "heightMeters");
        Validation.requireOpenClosedRange(weightKilograms, 0, MAX_WEIGHT_KILOGRAMS, "weightKilograms");
    }

    public double bodyMassIndex() {
        return weightKilograms / (heightMeters * heightMeters);
    }
}
