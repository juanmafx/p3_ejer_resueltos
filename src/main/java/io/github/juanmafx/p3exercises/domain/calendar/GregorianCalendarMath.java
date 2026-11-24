package io.github.juanmafx.p3exercises.domain.calendar;

public final class GregorianCalendarMath {

    private GregorianCalendarMath() {
    }

    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    public static long countLeapYears(int firstYear, int lastYear) {
        if (firstYear > lastYear) {
            throw new IllegalArgumentException("firstYear must not be after lastYear");
        }
        return leapYearsThrough(lastYear) - leapYearsThrough(firstYear - 1L);
    }

    private static long leapYearsThrough(long year) {
        return Math.floorDiv(year, 4L) - Math.floorDiv(year, 100L) + Math.floorDiv(year, 400L);
    }
}
