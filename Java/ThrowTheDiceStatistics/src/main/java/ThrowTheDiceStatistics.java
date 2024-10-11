package main.java;

import java.util.Random;
import java.util.stream.IntStream;

public class ThrowTheDiceStatistics {

    public static final int NB_SIDES_MIN = 2; // a coin
    public static final int NB_SIDES_MAX = 20; // performance purpose - arbitrary value for now

    private int nbSidesOfTheDice = 0 ;


    /**
     * Set the number of sides of the dice.
     * @param nbSidesOfTheDice the value should respect the limits NB_SIDES_MIN (inclusively) and NB_SIDES_MAX (inclusively)
     */
    public ThrowTheDiceStatistics (int nbSidesOfTheDice){
        validateNbSidesOfTheDice(nbSidesOfTheDice);
        this.nbSidesOfTheDice = nbSidesOfTheDice;
    }


    public int[][] throwsTheDice(int nbOfThrows) {
        // each element of the parent array contains the data of one side of the dice
        // the data of one side of the dice is composed of 2 elements : the side value and the number of time this side was returned
        int[][] throwResults = new int[this.nbSidesOfTheDice][2];

        // Set the dice value in the first element of each sub array
        for(int i = 0; i < this.nbSidesOfTheDice; i++) {
            throwResults[i][0] = i + 1;
        }

        throwTheDiceAndReturnSideValues(nbOfThrows)
                .forEach(oneThrow -> {
                    throwResults[oneThrow - 1][1]++;
                });

        return throwResults;
    }

    private IntStream throwTheDiceAndReturnSideValues(int nbOfThrows) {
        return new Random().ints(nbOfThrows, 1, this.nbSidesOfTheDice + 1);
    }

    private void validateNbSidesOfTheDice(int nbSidesOfTheDice) {
        if(nbSidesOfTheDice < NB_SIDES_MIN)
            throw new IllegalArgumentException(String.format("A dice should have at less %s sides - this is a coin", NB_SIDES_MIN));
        if(nbSidesOfTheDice > NB_SIDES_MAX)
            throw new IllegalArgumentException(String.format("A dice should have a maximum of %s sides - for performance reason", NB_SIDES_MAX));
    }
}
