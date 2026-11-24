package io.github.juanmafx.p3exercises.cli.commands;

import io.github.juanmafx.p3exercises.cli.Arguments;
import io.github.juanmafx.p3exercises.cli.Command;
import io.github.juanmafx.p3exercises.cli.CommandContext;
import io.github.juanmafx.p3exercises.domain.grading.CohortReport;
import io.github.juanmafx.p3exercises.domain.grading.GradeBook;
import io.github.juanmafx.p3exercises.domain.grading.Student;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

final class GradesCommand implements Command {

    private static final int MAX_STUDENTS = 1_000;

    @Override
    public String name() {
        return "grades";
    }

    @Override
    public String usage() {
        return "grades <name:grade,grade,...>...";
    }

    @Override
    public void execute(List<String> args, CommandContext context) {
        Arguments.requireCount(args, 1, MAX_STUDENTS);
        List<Student> students = args.stream().map(GradesCommand::parseStudent).toList();
        CohortReport report = GradeBook.summarize(students);
        context.out().println("Students: " + report.studentCount());
        context.out().println(String.format(Locale.ROOT, "Mean of averages: %.4f", report.meanOfAverages()));
        context.out().println(report.sampleStandardDeviation().isPresent()
                ? String.format(Locale.ROOT, "Sample standard deviation: %.4f",
                        report.sampleStandardDeviation().getAsDouble())
                : "Sample standard deviation: undefined for one student");
        context.out().println(String.format(Locale.ROOT, "Best student: %s (%.4f)",
                report.bestStudent().name(), report.bestStudent().average()));
        context.out().println("Regular: " + report.regularCount());
        context.out().println("Not regular: " + report.notRegularCount());
    }

    private static Student parseStudent(String token) {
        String[] pair = Arguments.splitPair(token, "student");
        List<Integer> grades = Arrays.stream(pair[1].split(",", -1))
                .limit(Student.MAX_GRADES + 1L)
                .map(grade -> Arguments.parseInt(grade, "grade"))
                .toList();
        return new Student(pair[0], grades);
    }
}
