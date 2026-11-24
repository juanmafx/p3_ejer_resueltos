package io.github.juanmafx.p3exercises.domain.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Year;
import java.util.stream.IntStream;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;

class GregorianCalendarMathTest {

    @Test
    void appliesCenturyRules() {
        assertTrue(GregorianCalendarMath.isLeapYear(2000));
        assertTrue(GregorianCalendarMath.isLeapYear(2024));
        assertFalse(GregorianCalendarMath.isLeapYear(1900));
        assertFalse(GregorianCalendarMath.isLeapYear(2023));
        assertTrue(GregorianCalendarMath.isLeapYear(0));
    }

    @Test
    void rejectsInvertedRange() {
        assertThrows(IllegalArgumentException.class, () -> GregorianCalendarMath.countLeapYears(2, 1));
    }

    @Test
    void handlesIntegerExtremesWithoutOverflow() {
        assertEquals(
                IntStream.of(Integer.MIN_VALUE).filter(GregorianCalendarMath::isLeapYear).count(),
                GregorianCalendarMath.countLeapYears(Integer.MIN_VALUE, Integer.MIN_VALUE));
    }

    @Property(seed = "20260101")
    void agreesWithJavaTime(@ForAll @IntRange(min = -999_999_999, max = 999_999_999) int year) {
        assertEquals(Year.isLeap(year), GregorianCalendarMath.isLeapYear(year));
    }

    @Property(seed = "20260102")
    void everyFourHundredYearCycleHas97LeapYears(@ForAll @IntRange(min = -1_000_000, max = 1_000_000) int start) {
        assertEquals(97, GregorianCalendarMath.countLeapYears(start, start + 399));
    }

    @Property(tries = 300, seed = "20260103")
    void closedFormMatchesBruteForce(
            @ForAll @IntRange(min = -3000, max = 3000) int first,
            @ForAll @IntRange(min = 0, max = 1200) int span) {
        long expected = IntStream.rangeClosed(first, first + span)
                .filter(GregorianCalendarMath::isLeapYear)
                .count();
        assertEquals(expected, GregorianCalendarMath.countLeapYears(first, first + span));
    }
}
