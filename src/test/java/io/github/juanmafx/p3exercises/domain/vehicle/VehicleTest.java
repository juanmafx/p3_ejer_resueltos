package io.github.juanmafx.p3exercises.domain.vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Year;
import org.junit.jupiter.api.Test;

class VehicleTest {

    @Test
    void keepsConstructorMileage() {
        assertEquals(120_000, new Vehicle("Acme", 2010, BigDecimal.TEN, 120_000).mileageKm());
    }

    @Test
    void computesAgeFromInjectedYear() {
        assertEquals(16, new Vehicle("Acme", 2010, BigDecimal.ONE, 0).ageInYears(Year.of(2026)));
    }

    @Test
    void futureModelYearHasZeroAge() {
        assertEquals(0, new Vehicle("Acme", 2027, BigDecimal.ONE, 0).ageInYears(Year.of(2026)));
    }

    @Test
    void rejectsInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("Acme", 1800, BigDecimal.ONE, 0));
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("Acme", 2000, BigDecimal.valueOf(-1), 0));
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("Acme", 2000, BigDecimal.ONE, -1));
        assertThrows(IllegalArgumentException.class, () -> new Vehicle(" ", 2000, BigDecimal.ONE, 0));
    }
}
