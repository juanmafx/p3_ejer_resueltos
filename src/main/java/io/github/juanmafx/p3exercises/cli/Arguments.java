package io.github.juanmafx.p3exercises.cli;

import java.math.BigDecimal;
import java.util.List;

public final class Arguments {

    public static final int MAX_NUMERIC_TOKEN_LENGTH = 40;

    private Arguments() {
    }

    public static void requireCount(List<String> args, int min, int max) {
        if (args.size() < min || args.size() > max) {
            throw new IllegalArgumentException("wrong number of arguments");
        }
    }

    public static int parseInt(String token, String field) {
        try {
            return Integer.parseInt(bounded(token, field));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(field + " must be an integer");
        }
    }

    public static long parseLong(String token, String field) {
        try {
            return Long.parseLong(bounded(token, field));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(field + " must be an integer");
        }
    }

    public static double parseDouble(String token, String field) {
        try {
            return Double.parseDouble(bounded(token, field));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(field + " must be a number");
        }
    }

    public static BigDecimal parseDecimal(String token, String field) {
        try {
            BigDecimal value = new BigDecimal(bounded(token, field));
            if (Math.abs((long) value.scale()) > MAX_NUMERIC_TOKEN_LENGTH) {
                throw new IllegalArgumentException(field + " has an unsupported scale");
            }
            return value;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(field + " must be a decimal number");
        }
    }

    public static String[] splitPair(String token, String field) {
        int separator = token.lastIndexOf(':');
        if (separator <= 0 || separator == token.length() - 1) {
            throw new IllegalArgumentException(field + " must use the form name:value");
        }
        return new String[] {token.substring(0, separator), token.substring(separator + 1)};
    }

    private static String bounded(String token, String field) {
        if (token.length() > MAX_NUMERIC_TOKEN_LENGTH) {
            throw new IllegalArgumentException(field + " is too long");
        }
        return token;
    }
}
