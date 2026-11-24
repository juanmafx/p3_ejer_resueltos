package io.github.juanmafx.p3exercises.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ApplicationTest {

    private static final Clock CLOCK = Clock.fixed(Instant.parse("2026-06-01T00:00:00Z"), ZoneOffset.UTC);

    private final ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errBytes = new ByteArrayOutputStream();

    private int run(String... args) {
        return Application.run(
                args,
                new PrintStream(outBytes, true, StandardCharsets.UTF_8),
                new PrintStream(errBytes, true, StandardCharsets.UTF_8),
                CLOCK);
    }

    private String out() {
        return outBytes.toString(StandardCharsets.UTF_8);
    }

    private String err() {
        return errBytes.toString(StandardCharsets.UTF_8);
    }

    @Test
    void leapYearCommand() {
        assertEquals(Application.EXIT_SUCCESS, run("leap-year", "1900", "2000"));
        assertTrue(out().contains("1900 is not a leap year"));
        assertTrue(out().contains("2000 is a leap year"));
    }

    @Test
    void firstPositiveCommand() {
        assertEquals(Application.EXIT_SUCCESS, run("first-positive", "-1", "0", "2.5"));
        assertTrue(out().contains("2.5 is positive"));
    }

    @Test
    void firstPositiveFailsWithoutCandidate() {
        assertEquals(Application.EXIT_USAGE, run("first-positive", "-1", "0"));
    }

    @Test
    void vehicleCommand() {
        assertEquals(Application.EXIT_SUCCESS, run("vehicle", "Acme", "2010", "1000", "12.50"));
        assertTrue(out().contains("Age: 16 years"));
        assertTrue(out().contains("price 12.50"));
    }

    @Test
    void personCommand() {
        assertEquals(Application.EXIT_SUCCESS, run("person", "Ada", "Lovelace", "1990", "1.75", "70"));
        assertTrue(out().contains("Ada Lovelace, age reached in 2026: 36"));
        assertTrue(out().contains("Body mass index: 22.86"));
    }

    @Test
    void gradesCommand() {
        assertEquals(Application.EXIT_SUCCESS, run("grades", "Alice:10,9,8", "Bob:2,3,4"));
        assertTrue(out().contains("Best student: Alice (9.0000)"));
        assertTrue(out().contains("Regular: 1"));
        assertTrue(out().contains("Not regular: 1"));
    }

    @Test
    void matchCommandReportsAwayWinner() {
        assertEquals(Application.EXIT_SUCCESS, run("match", "Home", "0", "Away", "2"));
        assertTrue(out().contains("Winner: Away"));
    }

    @Test
    void scheduleCommand() {
        assertEquals(Application.EXIT_SUCCESS, run("schedule", "A", "B", "C", "D"));
        assertTrue(out().contains("Round 3"));
    }

    @Test
    void simulateCommandIsReproducible() {
        assertEquals(Application.EXIT_SUCCESS, run("simulate", "42", "200", "1.2", "A:1.5", "B:1.5"));
        String first = out();
        outBytes.reset();
        assertEquals(Application.EXIT_SUCCESS, run("simulate", "42", "200", "1.2", "A:1.5", "B:1.5"));
        assertEquals(first, out());
    }

    @Test
    void simulateNeutralizesSpreadsheetFormulas() {
        assertEquals(Application.EXIT_SUCCESS, run("simulate", "1", "10", "1", "=SUM(A1):1.5", "B:1.5"));
        assertTrue(out().contains("'=SUM(A1),"));
    }

    @Test
    void simulateEnforcesRunLimit() {
        assertEquals(Application.EXIT_USAGE, run("simulate", "1", "100001", "1", "A:1", "B:1"));
    }

    @Test
    void simulateEnforcesTotalMatchBudget() {
        String[] full = new String[4 + 64];
        full[0] = "simulate";
        full[1] = "1";
        full[2] = "100000";
        full[3] = "1";
        for (int index = 0; index < 64; index++) {
            full[4 + index] = "T" + index + ":1";
        }
        assertEquals(Application.EXIT_USAGE, run(full));
        assertTrue(err().contains("simulation exceeds"));
    }

    @Test
    void loopTraceCommand() {
        assertEquals(Application.EXIT_SUCCESS, run("loop-trace"));
        assertEquals(4, out().lines().count());
    }

    @Test
    void unknownCommandDoesNotEchoInput() {
        assertEquals(Application.EXIT_USAGE, run("bad\u001b[31mcommand"));
        assertFalse(err().contains("\u001b"));
    }

    @Test
    void invalidNumberDoesNotEchoInput() {
        assertEquals(Application.EXIT_USAGE, run("leap-year", "x\u001b[2J"));
        assertFalse(err().contains("\u001b"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"grades", "match", "schedule", "simulate", "vehicle", "person", "leap-year"})
    void missingArgumentsReturnUsageError(String command) {
        assertEquals(Application.EXIT_USAGE, run(command));
        assertTrue(err().contains("usage:"));
    }

    @Test
    void helpSucceedsAndNoArgumentsFail() {
        assertEquals(Application.EXIT_SUCCESS, run("--help"));
        assertEquals(Application.EXIT_USAGE, run());
    }
}
