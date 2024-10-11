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

    public double calculateMean(int[] values) {
        validateNotEmpty(values);

        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

        /*
    public double calculateVariance(double[] data, double mean) {
        double variance = 0;
        for (double value : data) {
            variance += Math.pow(value - mean, 2);
        }
        return variance / (data.length - 1);
    }
     */

    private void validateNotEmpty(int[] values) {
        if (values.length == 0)
            throw new IllegalArgumentException("The input array cannot be empty");
    }

}
