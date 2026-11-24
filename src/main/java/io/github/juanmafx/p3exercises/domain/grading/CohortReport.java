package io.github.juanmafx.p3exercises.domain.grading;

import java.util.OptionalDouble;

public record CohortReport(
        int studentCount,
        double meanOfAverages,
        OptionalDouble sampleStandardDeviation,
        int regularCount,
        int notRegularCount,
        Student bestStudent) {
}
