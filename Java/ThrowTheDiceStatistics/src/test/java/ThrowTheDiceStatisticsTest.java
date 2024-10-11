package test.java;

import main.java.ThrowTheDiceStatistics;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThrowTheDiceStatisticsTest {

    private static boolean PRINT_LN = false;

    @BeforeAll
    public static void configure() {
        PRINT_LN = false;
    }

    @Test
    public void test_constructor_nbOfSideIsValid() {
        int nbSidesOfTheDice = 6;
        ThrowTheDiceStatistics throwTheDiceStatistics = new ThrowTheDiceStatistics(nbSidesOfTheDice);
    }

    @Test
    public void test_constructor_nbOfSideIsTooSmall() {
        int nbSidesOfTheDice= 1;
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new ThrowTheDiceStatistics(nbSidesOfTheDice));
    }

    @Test
    public void test_constructor_nbOfSideIsTooBig() {
        int nbSidesOfTheDice = 21;
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new ThrowTheDiceStatistics(nbSidesOfTheDice));
    }


    @Test
    public void test_throwsTheDice_throw10TimesA6SidesDice() {
        int nbSidesOfTheDice = 6;
        int nbOfThrow = 10;
        ThrowTheDiceStatistics throwsTheDiceStatistics = new ThrowTheDiceStatistics(nbSidesOfTheDice);

        int[][] output = throwsTheDiceStatistics.throwsTheDice(nbOfThrow);

        validateOutput(nbSidesOfTheDice, nbOfThrow, output);
        if(PRINT_LN) printlnResults(output);
    }

    @Test
    public void test_throwsTheDice_throw10TimesA4SidesDice() {
        int nbSidesOfTheDice = 10;
        int nbOfThrow = 10000000;
        ThrowTheDiceStatistics throwsTheDiceStatistics = new ThrowTheDiceStatistics(nbSidesOfTheDice);

        int[][] output = throwsTheDiceStatistics.throwsTheDice(nbOfThrow);

        validateOutput(nbSidesOfTheDice, nbOfThrow, output);
        if(PRINT_LN) printlnResults(output);
    }



    private void printlnResults(int[][] output) {
        System.out.println("Results of the run.");
        for (int i = 0; i < output.length; i ++) {
            System.out.println(String.format("The side %s was returned %s.", output[i][0], output[i][1]));
        }
    }

    private void validateOutput(int nbSidesOfTheDice, int expectedNbOfThrowDone, int[][] output) {
        assertEquals(nbSidesOfTheDice, output.length);

        int nbOfThrowInTheOutput = 0;

        for(int i = 0; i < nbSidesOfTheDice ; i++) {
            assertEquals(2, output[i].length);
            assertEquals( i+1, output[i][0]);
            nbOfThrowInTheOutput += output[i][1];
        }

        assertEquals(expectedNbOfThrowDone, nbOfThrowInTheOutput);
    }
}