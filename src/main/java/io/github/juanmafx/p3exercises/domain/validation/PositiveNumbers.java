package io.github.juanmafx.p3exercises.domain.validation;

import java.util.Arrays;
import java.util.OptionalDouble;

public final class PositiveNumbers {

    private PositiveNumbers() {
    }

    public static boolean isStrictlyPositive(double value) {
        return value > 0 && Double.isFinite(value);
    }

    public static OptionalDouble firstStrictlyPositive(double... candidates) {
        return Arrays.stream(candidates).filter(PositiveNumbers::isStrictlyPositive).findFirst();
    }
}
