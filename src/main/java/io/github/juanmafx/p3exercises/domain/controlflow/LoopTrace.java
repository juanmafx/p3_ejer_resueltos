package io.github.juanmafx.p3exercises.domain.controlflow;

import java.util.ArrayList;
import java.util.List;

public final class LoopTrace {

    public static final int MAX_UPPER_BOUND = 10_000;

    private LoopTrace() {
    }

    public static List<String> trace(int upperExclusive, int continueAt, int breakAt) {
        if (upperExclusive < 1 || upperExclusive > MAX_UPPER_BOUND) {
            throw new IllegalArgumentException("upperExclusive must be between 1 and " + MAX_UPPER_BOUND);
        }
        List<String> events = new ArrayList<>();
        for (int i = 1; i < upperExclusive; i++) {
            events.add("before " + i);
            if (i == continueAt) {
                continue;
            }
            if (i == breakAt) {
                break;
            }
            events.add("after " + i);
        }
        return List.copyOf(events);
    }
}
