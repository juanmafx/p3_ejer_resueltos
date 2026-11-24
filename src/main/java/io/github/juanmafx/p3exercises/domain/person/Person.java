package io.github.juanmafx.p3exercises.domain.person;

import io.github.juanmafx.p3exercises.support.Validation;
import java.time.Year;

public record Person(String firstName, String lastName, int birthYear) {

    public static final int MIN_BIRTH_YEAR = 1800;
    public static final int MAX_BIRTH_YEAR = 9999;

    public Person {
        firstName = Validation.requireLabel(firstName, "firstName");
        lastName = Validation.requireLabel(lastName, "lastName");
        Validation.requireRange(birthYear, MIN_BIRTH_YEAR, MAX_BIRTH_YEAR, "birthYear");
    }

    public String fullName() {
        return firstName + " " + lastName;
    }

    public int ageReachedIn(Year year) {
        int age = year.getValue() - birthYear;
        if (age < 0) {
            throw new IllegalArgumentException("year must not precede birthYear");
        }
        return age;
    }
}
