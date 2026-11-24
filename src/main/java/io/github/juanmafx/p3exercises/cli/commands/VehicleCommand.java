package io.github.juanmafx.p3exercises.cli.commands;

import io.github.juanmafx.p3exercises.cli.Arguments;
import io.github.juanmafx.p3exercises.cli.Command;
import io.github.juanmafx.p3exercises.cli.CommandContext;
import io.github.juanmafx.p3exercises.domain.vehicle.Vehicle;
import java.math.BigDecimal;
import java.util.List;

final class VehicleCommand implements Command {

    @Override
    public String name() {
        return "vehicle";
    }

    @Override
    public String usage() {
        return "vehicle <make> <modelYear> <mileageKm> [price]";
    }

    @Override
    public void execute(List<String> args, CommandContext context) {
        Arguments.requireCount(args, 3, 4);
        BigDecimal price = args.size() == 4 ? Arguments.parseDecimal(args.get(3), "price") : BigDecimal.ZERO;
        Vehicle vehicle = new Vehicle(
                args.get(0),
                Arguments.parseInt(args.get(1), "modelYear"),
                price,
                Arguments.parseInt(args.get(2), "mileageKm"));
        context.out().println(vehicle.make() + " " + vehicle.modelYear()
                + ", " + vehicle.mileageKm() + " km, price " + vehicle.price().toPlainString());
        context.out().println("Age: " + vehicle.ageInYears(context.currentYear()) + " years");
    }
}
