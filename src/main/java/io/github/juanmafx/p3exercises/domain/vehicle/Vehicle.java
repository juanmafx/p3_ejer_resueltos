package io.github.juanmafx.p3exercises.domain.vehicle;

import io.github.juanmafx.p3exercises.support.Validation;
import java.math.BigDecimal;
import java.time.Year;
import java.util.Objects;

public record Vehicle(String make, int modelYear, BigDecimal price, int mileageKm) {

    public static final int FIRST_MODEL_YEAR = 1886;
    public static final int LAST_MODEL_YEAR = 9999;

    public Vehicle {
        make = Validation.requireLabel(make, "make");
        Validation.requireRange(modelYear, FIRST_MODEL_YEAR, LAST_MODEL_YEAR, "modelYear");
        Objects.requireNonNull(price, "price");
        if (price.signum() < 0) {
            throw new IllegalArgumentException("price must not be negative");
        }
        Validation.requireRange(mileageKm, 0, Integer.MAX_VALUE, "mileageKm");
    }

    public int ageInYears(Year currentYear) {
        return Math.max(0, currentYear.getValue() - modelYear);
    }
}
