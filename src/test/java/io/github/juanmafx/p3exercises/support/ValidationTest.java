package io.github.juanmafx.p3exercises.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ValidationTest {

    @Test
    void stripsSurroundingWhitespace() {
        assertEquals("Alice", Validation.requireLabel("  Alice ", "name"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "a\u001b[31m", "a\nb", "a\u202Eb", "a\u200Bb", "a\u2028b"})
    void rejectsUnsafeLabels(String value) {
        assertThrows(IllegalArgumentException.class, () -> Validation.requireLabel(value, "name"));
    }

    @Test
    void rejectsOverlongLabel() {
        String value = "x".repeat(Validation.MAX_LABEL_CODE_POINTS + 1);
        assertThrows(IllegalArgumentException.class, () -> Validation.requireLabel(value, "name"));
    }
}
