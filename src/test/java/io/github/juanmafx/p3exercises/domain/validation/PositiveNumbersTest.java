package io.github.juanmafx.p3exercises.domain.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.OptionalDouble;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PositiveNumbersTest {

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -0.0, -1.5, Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void rejectsNonPositiveAndNonFiniteValues(double value) {
        assertFalse(PositiveNumbers.isStrictlyPositive(value));
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.MIN_VALUE, 1.0, Double.MAX_VALUE})
    void acceptsPositiveFiniteValues(double value) {
        assertTrue(PositiveNumbers.isStrictlyPositive(value));
    }

    @Test
    void returnsFirstPositiveCandidate() {
        assertEquals(OptionalDouble.of(2.5), PositiveNumbers.firstStrictlyPositive(-3, 0, 2.5, 7));
    }

    @Test
    void returnsEmptyWhenNoCandidateQualifies() {
        assertEquals(OptionalDouble.empty(), PositiveNumbers.firstStrictlyPositive(-3, 0));
    }
}
