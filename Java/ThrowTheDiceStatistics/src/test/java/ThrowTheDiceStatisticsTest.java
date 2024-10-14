package test.java;

import main.java.ThrowTheDiceStatistics;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThrowTheDiceStatisticsTest {

    private static boolean PRINT_LN = false;

    @BeforeAll
    public static void configure() {
        PRINT_LN = true;
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
    public void test_throwsTheDice_throw60TimesA6SidesDice() {
        int nbSidesOfTheDice = 6;
        int nbOfThrow = 60;
        ThrowTheDiceStatistics throwsTheDiceStatistics = new ThrowTheDiceStatistics(nbSidesOfTheDice);

        int[] output = throwsTheDiceStatistics.throwTheDice(nbOfThrow);

        validateOutput(nbSidesOfTheDice, nbOfThrow, output);
        if(PRINT_LN) printlnResults(output);
    }

    @Test
    public void test_throwsTheDice_validateTotalNbOfRun(){
        int nbSidesOfTheDice = 6;
        int nbOfThrowPerBatch = Integer.MAX_VALUE;
        int nbOfBatch = 2;
        ThrowTheDiceStatistics throwsTheDiceStatistics = new ThrowTheDiceStatistics(nbSidesOfTheDice);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> throwsTheDiceStatistics.throwTheDice(nbOfThrowPerBatch, nbOfBatch));
    }

    @Test
    // This is an example of usage of the functionality. Time consuming!
    public void test_throwsTheDice_throws600000Times100Batches(){
        int nbSidesOfTheDice = 6;
        int nbOfThrowPerBatch = 600000;
        int nbOfBatch = 1000;
        ThrowTheDiceStatistics throwsTheDiceStatistics = new ThrowTheDiceStatistics(nbSidesOfTheDice);

        double[][] standardDeviationResults = throwsTheDiceStatistics.throwTheDice(nbOfThrowPerBatch, nbOfBatch);

        if(PRINT_LN) printlnStandardDeviationResults(standardDeviationResults, nbSidesOfTheDice);
    }


    private void printlnStandardDeviationResults(double[][] standardDeviationResults, int nbSidesOfTheDice) {

        // In order to import into a sheet
        for(double[] standardDeviationResult : standardDeviationResults) {
            System.out.printf("%.0f - %.3f - %.0f - %.3f - %.9f%n",
                    standardDeviationResult[0], standardDeviationResult[1], standardDeviationResult[2], standardDeviationResult[3],
                    (standardDeviationResult[3] / standardDeviationResult[2] / nbSidesOfTheDice));
        }

        // To display
        for(double[] standardDeviationResult : standardDeviationResults) {
            System.out.printf("A batch of %.0f with SD %.3f. The sum is %.0f with SD %.3f and %.9f percentage%n",
                    standardDeviationResult[0], standardDeviationResult[1], standardDeviationResult[2], standardDeviationResult[3],
                    standardDeviationResult[3] / standardDeviationResult[2] / nbSidesOfTheDice);
        }
    }

    private void printlnResults(int[] output) {
        System.out.printf("Results of the run.%n");
        for (int i = 0; i < output.length; i ++) {
            System.out.printf("The side %d was returned %d.%n", i + 1, output[i]);
        }
    }

    private void validateOutput(int nbSidesOfTheDice, int expectedNbOfThrowDone, int[] output) {
        assertEquals(nbSidesOfTheDice, output.length);

        int nbOfThrowInTheOutput = 0;

        for (int j : output) {
            nbOfThrowInTheOutput += j;
        }

        assertEquals(expectedNbOfThrowDone, nbOfThrowInTheOutput);
    }
}