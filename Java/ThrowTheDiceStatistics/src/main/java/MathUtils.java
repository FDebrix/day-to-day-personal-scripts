package main.java;

public class MathUtils {

    private static MathUtils THE_INSTANCE = null;

    private MathUtils() {
    }

    // If I recall, should be synchronized for multi threading env
    public static MathUtils getInstance() {
        if (THE_INSTANCE == null)
            THE_INSTANCE = new MathUtils();
        return THE_INSTANCE;
    }

    public double calculateMean(double[] values) {
        validateNotEmpty(values);

        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    public double calculateVariance(double[] values) {
        validateNotEmpty(values);

        double mean = calculateMean(values);

        return calculateVariance (values, mean);
    }

    public double calculateVariance(double[] values, double mean) {
        validateNotEmpty(values);

        double sumOfSquareOfTheDiff = 0;
        for (double value : values) {
            sumOfSquareOfTheDiff += Math.pow(value - mean, 2);
        }

        // Should we divide by "values.length" or "values.length - 1"? LOL. In our context, we will use "values.length"
        // Explanation in French https://www.amq.math.ca/wp-content/uploads/bulletin/vol55/no2/07-maitre-Stat-mai-2015.pdf
        // Explanation in English https://www.reddit.com/r/learnmath/comments/lvxik0/comment/gpecma2/
        return sumOfSquareOfTheDiff / (values.length);
    }

    public double calculateStandardDeviation(double[] values) {
        validateNotEmpty(values);

        double variance = calculateVariance(values);

        return calculateStandardDeviation(variance);
    }

    public double calculateStandardDeviation(double variance) {
        return Math.sqrt(variance);
    }




    private void validateNotEmpty(double[] values) {
        if (values.length == 0)
            throw new IllegalArgumentException("The input array cannot be empty");
    }
}
