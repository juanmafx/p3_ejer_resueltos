package io.github.juanmafx.p3exercises.domain.grading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Size;
import org.junit.jupiter.api.Test;

class GradeBookTest {

    @Test
    void averageKeepsFractionalPart() {
        assertEquals(23 / 3.0, new Student("Alice", List.of(7, 8, 8)).average(), 1e-12);
    }

    @Test
    void standingBoundaryIsInclusive() {
        assertEquals(AcademicStanding.REGULAR, new Student("Alice", List.of(4, 4, 4)).standing());
        assertEquals(AcademicStanding.NOT_REGULAR, new Student("Bob", List.of(4, 4, 3)).standing());
    }

    @Test
    void allZeroCohortStillHasBestStudent() {
        CohortReport report = GradeBook.summarize(List.of(new Student("Alice", List.of(0, 0, 0))));
        assertEquals("Alice", report.bestStudent().name());
        assertTrue(report.sampleStandardDeviation().isEmpty());
    }

    @Test
    void summarizesCohort() {
        CohortReport report = GradeBook.summarize(List.of(
                new Student("Alice", List.of(10, 9, 8)),
                new Student("Bob", List.of(2, 3, 4)),
                new Student("Carol", List.of(6, 6, 6))));
        assertEquals(3, report.studentCount());
        assertEquals(6.0, report.meanOfAverages(), 1e-12);
        assertEquals(3.0, report.sampleStandardDeviation().orElseThrow(), 1e-12);
        assertEquals(2, report.regularCount());
        assertEquals(1, report.notRegularCount());
        assertEquals("Alice", report.bestStudent().name());
    }

    @Test
    void rejectsInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> GradeBook.summarize(List.of()));
        assertThrows(IllegalArgumentException.class, () -> new Student("Alice", List.of(11)));
        assertThrows(IllegalArgumentException.class, () -> new Student("Alice", List.of(-1)));
        assertThrows(IllegalArgumentException.class, () -> new Student("Alice", List.of()));
    }

    @Property(seed = "20260105")
    void averageStaysWithinGradeBounds(
            @ForAll @Size(min = 1, max = 20) List<@IntRange(min = 0, max = 10) Integer> grades) {
        Student student = new Student("Alice", grades);
        int min = grades.stream().mapToInt(Integer::intValue).min().orElseThrow();
        int max = grades.stream().mapToInt(Integer::intValue).max().orElseThrow();
        assertTrue(student.average() >= min && student.average() <= max);
    }

    @Property(seed = "20260106")
    void standingCountsPartitionCohort(
            @ForAll @Size(min = 1, max = 30) List<@IntRange(min = 0, max = 10) Integer> firstGrades) {
        List<Student> students = firstGrades.stream()
                .map(grade -> new Student("Student", List.of(grade, 10 - grade, grade)))
                .toList();
        CohortReport report = GradeBook.summarize(students);
        assertEquals(students.size(), report.regularCount() + report.notRegularCount());
        assertFalse(students.stream().anyMatch(s -> s.average() > report.bestStudent().average()));
    }
}
