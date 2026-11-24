package io.github.juanmafx.p3exercises.domain.grading;

import io.github.juanmafx.p3exercises.domain.statistics.RunningStatistics;
import java.util.List;

public final class GradeBook {

    private GradeBook() {
    }

    public static CohortReport summarize(List<Student> students) {
        if (students == null || students.isEmpty()) {
            throw new IllegalArgumentException("students must not be empty");
        }
        RunningStatistics statistics = new RunningStatistics();
        Student best = students.get(0);
        int regular = 0;
        for (Student student : students) {
            statistics.add(student.average());
            if (student.average() > best.average()) {
                best = student;
            }
            if (student.standing() == AcademicStanding.REGULAR) {
                regular++;
            }
        }
        return new CohortReport(
                students.size(),
                statistics.mean().orElseThrow(),
                statistics.sampleStandardDeviation(),
                regular,
                students.size() - regular,
                best);
    }
}
