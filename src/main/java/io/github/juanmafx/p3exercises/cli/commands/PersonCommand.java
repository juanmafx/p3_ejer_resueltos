package io.github.juanmafx.p3exercises.cli.commands;

import io.github.juanmafx.p3exercises.cli.Arguments;
import io.github.juanmafx.p3exercises.cli.Command;
import io.github.juanmafx.p3exercises.cli.CommandContext;
import io.github.juanmafx.p3exercises.domain.person.BodyMetrics;
import io.github.juanmafx.p3exercises.domain.person.Person;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

final class PersonCommand implements Command {

    @Override
    public String name() {
        return "person";
    }

    @Override
    public String usage() {
        return "person <firstName> <lastName> <birthYear> [heightMeters weightKilograms]";
    }

    @Override
    public void execute(List<String> args, CommandContext context) {
        if (args.size() != 3 && args.size() != 5) {
            throw new IllegalArgumentException("wrong number of arguments");
        }
        Person person = new Person(args.get(0), args.get(1), Arguments.parseInt(args.get(2), "birthYear"));
        int age = person.ageReachedIn(context.currentYear());
        Optional<BodyMetrics> metrics = args.size() == 5
                ? Optional.of(new BodyMetrics(
                        Arguments.parseDouble(args.get(3), "heightMeters"),
                        Arguments.parseDouble(args.get(4), "weightKilograms")))
                : Optional.empty();
        context.out().println(person.fullName() + ", age reached in " + context.currentYear() + ": " + age);
        metrics.ifPresent(value -> context.out().println(
                String.format(Locale.ROOT, "Body mass index: %.2f", value.bodyMassIndex())));
    }
}
