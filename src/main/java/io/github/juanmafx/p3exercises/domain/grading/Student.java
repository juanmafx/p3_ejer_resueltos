package io.github.juanmafx.p3exercises.domain.grading;

import io.github.juanmafx.p3exercises.support.Validation;
import java.util.List;

public record Student(String name, List<Integer> grades) {

    public static final int MIN_GRADE = 0;
    public static final int MAX_GRADE = 10;
    public static final int PASSING_AVERAGE = 4;
    public static final int MAX_GRADES = 64;

    public Student {
        name = Validation.requireLabel(name, "name");
        grades = List.copyOf(grades);
        if (grades.isEmpty() || grades.size() > MAX_GRADES) {
            throw new IllegalArgumentException("grades must contain between 1 and " + MAX_GRADES + " values");
        }
        grades.forEach(grade -> Validation.requireRange(grade, MIN_GRADE, MAX_GRADE, "grade"));
    }

    public double average() {
        return gradeTotal() / (double) grades.size();
    }

    public AcademicStanding standing() {
        return gradeTotal() >= (long) PASSING_AVERAGE * grades.size()
                ? AcademicStanding.REGULAR
                : AcademicStanding.NOT_REGULAR;
    }

    private long gradeTotal() {
        return grades.stream().mapToLong(Integer::longValue).sum();
    }
}
