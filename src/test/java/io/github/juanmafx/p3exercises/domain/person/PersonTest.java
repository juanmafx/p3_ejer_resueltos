package io.github.juanmafx.p3exercises.domain.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Year;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.DoubleRange;
import org.junit.jupiter.api.Test;

class PersonTest {

    @Test
    void keepsFirstAndLastNameDistinct() {
        assertEquals("Ada Lovelace", new Person("Ada", "Lovelace", 1815).fullName());
    }

    @Test
    void computesAgeReachedInYear() {
        assertEquals(36, new Person("Ada", "Lovelace", 1815).ageReachedIn(Year.of(1851)));
    }

    @Test
    void rejectsYearBeforeBirth() {
        Person person = new Person("Ada", "Lovelace", 1815);
        assertThrows(IllegalArgumentException.class, () -> person.ageReachedIn(Year.of(1814)));
    }

    @Test
    void computesBodyMassIndex() {
        assertEquals(22.857, new BodyMetrics(1.75, 70).bodyMassIndex(), 0.001);
    }

    @Test
    void rejectsNonPhysicalMetrics() {
        assertThrows(IllegalArgumentException.class, () -> new BodyMetrics(0, 70));
        assertThrows(IllegalArgumentException.class, () -> new BodyMetrics(1.7, Double.NaN));
    }

    @Property(seed = "20260104")
    void bodyMassIndexScalesLinearlyWithWeight(
            @ForAll @DoubleRange(min = 0.5, max = 2.5) double height,
            @ForAll @DoubleRange(min = 1, max = 300) double weight) {
        double single = new BodyMetrics(height, weight).bodyMassIndex();
        double doubled = new BodyMetrics(height, weight * 2).bodyMassIndex();
        assertEquals(2 * single, doubled, 1e-9);
    }
}
