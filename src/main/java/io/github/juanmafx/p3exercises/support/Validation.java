package io.github.juanmafx.p3exercises.support;

public final class Validation {

    public static final int MAX_LABEL_CODE_POINTS = 100;

    private Validation() {
    }

    public static String requireLabel(String value, String field) {
        if (value == null || value.codePoints().allMatch(Validation::isSpace)) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        String stripped = value.strip();
        if (stripped.codePointCount(0, stripped.length()) > MAX_LABEL_CODE_POINTS) {
            throw new IllegalArgumentException(field + " must be at most " + MAX_LABEL_CODE_POINTS + " characters");
        }
        if (stripped.codePoints().anyMatch(Validation::isUnsafeCodePoint)) {
            throw new IllegalArgumentException(field + " must not contain control or format characters");
        }
        return stripped;
    }

    public static int requireRange(int value, int min, int max, String field) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(field + " must be between " + min + " and " + max);
        }
        return value;
    }

    public static double requireOpenClosedRange(double value, double min, double max, String field) {
        if (!(value > min && value <= max)) {
            throw new IllegalArgumentException(field + " must be greater than " + min + " and at most " + max);
        }
        return value;
    }

    private static boolean isSpace(int codePoint) {
        return Character.isWhitespace(codePoint) || Character.isSpaceChar(codePoint);
    }

    private static boolean isUnsafeCodePoint(int codePoint) {
        int type = Character.getType(codePoint);
        return type == Character.CONTROL
                || type == Character.FORMAT
                || type == Character.SURROGATE
                || type == Character.PRIVATE_USE
                || type == Character.UNASSIGNED
                || type == Character.LINE_SEPARATOR
                || type == Character.PARAGRAPH_SEPARATOR;
    }
}
