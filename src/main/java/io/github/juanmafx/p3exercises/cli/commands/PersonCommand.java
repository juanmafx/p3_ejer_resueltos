package io.github.juanmafx.p3exercises.cli.commands;

import io.github.juanmafx.p3exercises.cli.Arguments;
import io.github.juanmafx.p3exercises.cli.Command;
import io.github.juanmafx.p3exercises.cli.CommandContext;
import io.github.juanmafx.p3exercises.domain.person.BodyMetrics;
import io.github.juanmafx.p3exercises.domain.person.Person;
import java.util.List;
import java.util.Locale;

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
        context.out().println(person.fullName() + ", age reached in " + context.currentYear() + ": "
                + person.ageReachedIn(context.currentYear()));
        if (args.size() == 5) {
            BodyMetrics metrics = new BodyMetrics(
                    Arguments.parseDouble(args.get(3), "heightMeters"),
                    Arguments.parseDouble(args.get(4), "weightKilograms"));
            context.out().println(String.format(Locale.ROOT, "Body mass index: %.2f", metrics.bodyMassIndex()));
        }
    }
}
