package io.github.juanmafx.p3exercises.domain.statistics;

public record WilsonInterval(double lower, double upper) {

    public static final double Z_95_PERCENT = 1.959963984540054;

    public static WilsonInterval of(long successes, long trials, double z) {
        if (trials <= 0) {
            throw new IllegalArgumentException("trials must be positive");
        }
        if (successes < 0 || successes > trials) {
            throw new IllegalArgumentException("successes must be between 0 and trials");
        }
        if (!(z > 0) || !Double.isFinite(z)) {
            throw new IllegalArgumentException("z must be positive and finite");
        }
        double n = trials;
        double proportion = successes / n;
        double zSquared = z * z;
        double denominator = 1 + zSquared / n;
        double center = (proportion + zSquared / (2 * n)) / denominator;
        double halfWidth = z / denominator
                * Math.sqrt(proportion * (1 - proportion) / n + zSquared / (4 * n * n));
        double lower = successes == 0 ? 0 : Math.max(0, Math.min(proportion, center - halfWidth));
        double upper = successes == trials ? 1 : Math.min(1, Math.max(proportion, center + halfWidth));
        return new WilsonInterval(lower, upper);
    }
}
