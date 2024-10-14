package main.java;

import java.util.Random;
import java.util.stream.IntStream;

public class ThrowTheDiceStatistics {

    public static final int NB_SIDES_MIN = 2; // a coin
    public static final int NB_SIDES_MAX = 20; // performance purpose - arbitrary value for now

    private final MathUtils mathUtils = MathUtils.getInstance();

    private int nbSidesOfTheDice = -1;


    /**
     * Set the number of sides of the dice.
     * @param nbSidesOfTheDice the value should respect the limits NB_SIDES_MIN (inclusively) and NB_SIDES_MAX (inclusively)
     * @throws IllegalArgumentException in case the number of sides of the dice is too small or too big.
     */
    public ThrowTheDiceStatistics (int nbSidesOfTheDice){
        validateNbSidesOfTheDice(nbSidesOfTheDice);
        this.nbSidesOfTheDice = nbSidesOfTheDice;
    }


    /**
     * Throw the dice multiple times.
     * @param nbOfThrows The number of throws
     * @return the results of the throws. At the index x, we have the number of times the dice side "x + 1" was gotten.
     */
    public int[] throwTheDice(int nbOfThrows) {
        int[] throwResults = buildArrayForThrowResults();

        throwTheDiceAndReturnSideValues(nbOfThrows)
                .forEach(sideOfOneThrow -> {
                    incrementCountOfTheSide(throwResults, sideOfOneThrow);
                });

        return throwResults;
    }

    /**
     * Throw the dice a number of times per a number of batches and return the standard deviation.
     * ----------|--------------------|--------------|-------------------
     * Size of   | Standard deviation | Size of all  | Standard deviation
     * the batch | of the batch       | the batches  | of all the batches
     * ----------|--------------------|--------------|-------------------
     * 600000    | 270,886            | 600000       | 270,886
     * 600000    | 344,510            | 1200000      | 560,394
     * 600000    | 362,144            | 1800000      | 647,643
     * 600000    | 394,079            | 2400000      | 421,383
     * 600000    | 307,812            | 3000000      | 629,479
     * 600000    | 311,781            | 3600000      | 622,122
     *
     * @param nbOfThrowPerBatch The number of throws per batch
     * @param nbOfBatch The number of batches to do
     * @return the standard deviation of each batch and the standard deviation of the sum of all the batches.
     * @throws IllegalArgumentException in case the number of throws to do is too big
     */
    public double[][] throwTheDice(int nbOfThrowPerBatch, int nbOfBatch) throws IllegalArgumentException {
        validateLimitOfAllTheThrows(nbOfThrowPerBatch, nbOfBatch);

        int[] allTheBatch = buildArrayForThrowResults();
        int[] resultsOfOneBatch;

        /* {@link #buildStandardDeviationResults(int[], int[])} */
        double[][] standardDeviationResults = new double[nbOfBatch][4];

        for(int i = 0 ; i < nbOfBatch ; i++) {
            resultsOfOneBatch = throwTheDice(nbOfThrowPerBatch);
            mergeResults(allTheBatch, resultsOfOneBatch);

            standardDeviationResults[i] = buildStandardDeviationResults(resultsOfOneBatch, allTheBatch);
        }

        return standardDeviationResults;
    }


    // number of throw of the batch, standard deviation of the batch, number of all the throws so far, standard deviation of it
    private double[] buildStandardDeviationResults(int[] resultsOfOneBatch, int[] resultsOfAllTheBatch) {
        double[] resultsAfterCurrentBatch = new double[4];

        resultsAfterCurrentBatch[0] = getNbOfThrow(resultsOfOneBatch);
        resultsAfterCurrentBatch[1] = mathUtils.calculateStandardDeviation(convertToDoubleArray(resultsOfOneBatch));
        resultsAfterCurrentBatch[2] = getNbOfThrow(resultsOfAllTheBatch);
        resultsAfterCurrentBatch[3] = mathUtils.calculateStandardDeviation(convertToDoubleArray(resultsOfAllTheBatch));

        return resultsAfterCurrentBatch;
    }

    private static int getNbOfThrow(int[] nbOfThrowPerSide) {
        int nbOfThrow = 0;

        for (int nbOfThrowOfOneSide: nbOfThrowPerSide) {
            nbOfThrow += nbOfThrowOfOneSide;
        }

        return nbOfThrow;
    }

    private static double[] convertToDoubleArray(int[] resultsOfOneBatch) {
        double [] resultsDouble = new double[resultsOfOneBatch.length];

        for(int i = 0; i < resultsOfOneBatch.length ; i ++) {
            resultsDouble[i] = resultsOfOneBatch[i];
        }
        return resultsDouble;
    }

    private void mergeResults(int[] allTheBatch, int[] resultsOfOneBatch) {
        for(int i = 0; i < resultsOfOneBatch.length ; i++) {
            allTheBatch[i] += resultsOfOneBatch[i];
        }
    }

    // At the index "x" of the array, we have the results of the dice side "x + 1"
    private int[] buildArrayForThrowResults() {
        return new int[this.nbSidesOfTheDice];
    }

    private static void  incrementCountOfTheSide(int[] throwResults, int oneThrow) {
        throwResults[oneThrow - 1]++;
    }

    private IntStream throwTheDiceAndReturnSideValues(int nbOfThrows) {
        return new Random().ints(nbOfThrows, 1, this.nbSidesOfTheDice + 1);
    }

    private void validateLimitOfAllTheThrows(int nbOfThrowPerBatch, int nbOfBatch) throws IllegalArgumentException {
        try {
            Math.multiplyExact(nbOfThrowPerBatch, nbOfBatch);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException(String.format("Integer overflow since the too many throws are requested. The limit is %s.", Integer.MAX_VALUE));
        }
    }

    private void validateNbSidesOfTheDice(int nbSidesOfTheDice) throws IllegalArgumentException {
        if(nbSidesOfTheDice < NB_SIDES_MIN)
            throw new IllegalArgumentException(String.format("A dice should have at less %s sides - this is a coin", NB_SIDES_MIN));
        if(nbSidesOfTheDice > NB_SIDES_MAX)
            throw new IllegalArgumentException(String.format("A dice should have a maximum of %s sides - for performance reason", NB_SIDES_MAX));
    }
}
