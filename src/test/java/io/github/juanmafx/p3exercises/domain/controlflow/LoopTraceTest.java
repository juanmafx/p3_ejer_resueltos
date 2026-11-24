package io.github.juanmafx.p3exercises.domain.controlflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class LoopTraceTest {

    @Test
    void reproducesContinueAndBreakSequence() {
        assertEquals(List.of("before 1", "after 1", "before 2", "before 3"), LoopTrace.trace(5, 2, 3));
    }

    @Test
    void runsToBoundWithoutJumps() {
        assertEquals(List.of("before 1", "after 1"), LoopTrace.trace(2, -1, -1));
    }

    @Test
    void rejectsOutOfRangeBound() {
        assertThrows(IllegalArgumentException.class, () -> LoopTrace.trace(0, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> LoopTrace.trace(LoopTrace.MAX_UPPER_BOUND + 1, 1, 1));
    }
}
