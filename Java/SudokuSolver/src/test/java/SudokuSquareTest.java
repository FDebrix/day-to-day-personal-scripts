package test.java;

import main.java.BroadcastWinner;
import main.java.SudokuRegion;
import main.java.SudokuRegions;
import main.java.SudokuSquare;
import main.java.SudokuSquare.ValueState;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static main.java.SudokuSquare.NOT_FOUND_YET;
import static main.java.SudokuSquare.ValueState.*;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuSquareTest {


    private static final int NB_POSSIBLE_VALUE = 4;

    @Test
    public void test_constructor_nbPossibleValuesTooSmall() {
        assertThrows(IllegalArgumentException.class,
                () -> buildSudokuSquare(0));
    }

    @Test
    public void test_changeStateOfAValue_indexTooSmall() {
        SudokuSquare aSquareWith4PossibleValues = buildSudokuSquare(NB_POSSIBLE_VALUE);

        assertThrows(IllegalArgumentException.class,
                () -> aSquareWith4PossibleValues.changeStateOfAValue(0, LOSER_VALUE));
    }

    @Test
    public void test_changeStateOfAValue_indexTooBig() {
        SudokuSquare aSquareWith4PossibleValues = buildSudokuSquare(NB_POSSIBLE_VALUE);

        assertThrows(IllegalArgumentException.class,
                () -> aSquareWith4PossibleValues.changeStateOfAValue(5, LOSER_VALUE));
    }

    @Test
    public void test_constructor_getValueState_4PossibleValues() {
        int sizeOfTheOutputTable = NB_POSSIBLE_VALUE + 1;
        SudokuSquare aSquareWith4PossibleValues = new SudokuSquare(NB_POSSIBLE_VALUE);

        ValueState[] valueStates = aSquareWith4PossibleValues.getValueState();

        assertEquals(sizeOfTheOutputTable, valueStates.length);
        assertEquals(LOSER_VALUE, valueStates[0]);

        for (int i = 1 ; i < valueStates.length ; i++) {
            assertEquals(POSSIBLE_VALUE, valueStates[i]);
        }
    }

    @Test
    public void test_constructor_getValueState_4PossibleValuesAndInitialValue0() {
        int sizeOfTheOutputTable = NB_POSSIBLE_VALUE + 1;
        SudokuSquare aSquareWith4PossibleValues = buildSudokuSquare(NB_POSSIBLE_VALUE, 0);

        ValueState[] valueStates = aSquareWith4PossibleValues.getValueState();

        assertEquals(sizeOfTheOutputTable, valueStates.length);
        assertEquals(LOSER_VALUE, valueStates[0]);

        for (int i = 1 ; i < valueStates.length ; i++) {
            assertEquals(POSSIBLE_VALUE, valueStates[i]);
        }
    }

    @Test
    public void test_setBroadcastWinner_setNullThrowException () {
        SudokuSquare aSquareWith4PossibleValues = buildSudokuSquare(NB_POSSIBLE_VALUE, 0);

        assertThrows(IllegalArgumentException.class,
                () -> aSquareWith4PossibleValues.setRegions(null));
    }

    @Test
    public void test_getBroadcastWinner_whenRegionsNotSet_returnIsNotNull () {
        SudokuSquare square = new SudokuSquare(4);
        SudokuRegions regions = square.getRegions();
        assertNotNull(regions);
        assertTrue(regions.getRegions().isEmpty());
    }

    @Test
    public void test_getBroadcastWinner_returnProperBroadcastWinner () {
        SudokuSquare aSquareWith4PossibleValues = buildSudokuSquare(NB_POSSIBLE_VALUE, 0);
        List<SudokuSquare> squares = Arrays.asList(aSquareWith4PossibleValues);
        BroadcastWinner region = new SudokuRegion(squares, SudokuRegion.SudokuRegionType.HORIZONTAL);
        SudokuRegions regions = new SudokuRegions();
        regions.addBroadcastWinner(region);

        aSquareWith4PossibleValues.setRegions(regions);

        assertEquals(regions, aSquareWith4PossibleValues.getRegions());
    }

    @Test
    public void test_setInitialValue_noBroadcastWinnerThrowException() {
        SudokuSquare square = new SudokuSquare(NB_POSSIBLE_VALUE);
        assertThrows(IllegalStateException.class,
                () -> square.setInitialValue(1));
    }

    @Test
    public void test_setWinnerValue_noBroadcastWinnerThrowException() {
        SudokuSquare square = new SudokuSquare(NB_POSSIBLE_VALUE);
        assertThrows(IllegalStateException.class,
                () -> square.setWinnerValue(1));
    }

    @Test
    public void test_setInitialValue_winnerValue() {
        int winnerValue = 2;

        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE, winnerValue);

        ValueState[] valueStates = theSquare.getValueState();

        assertTrue(theSquare.isWinnerValueFound());
        assertEquals(winnerValue, theSquare.getWinnerValue());
        assertEquals(LOSER_VALUE, valueStates[0]);
        assertEquals(LOSER_VALUE, valueStates[1]);
        assertEquals(WINNER_VALUE, valueStates[2]);
        assertEquals(LOSER_VALUE, valueStates[3]);
        assertEquals(LOSER_VALUE, valueStates[4]);
    }

    @Test
    public void test_changeStateOfAValue_LOSER_VALUE() {
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);
        ValueState[] valueStates = theSquare.getValueState();

        assertEquals(LOSER_VALUE, valueStates[0]);
        assertEquals(POSSIBLE_VALUE, valueStates[1]);
        assertEquals(POSSIBLE_VALUE, valueStates[2]);
        assertEquals(POSSIBLE_VALUE, valueStates[3]);
        assertEquals(POSSIBLE_VALUE, valueStates[4]);
        assertFalse(theSquare.isWinnerValueFound());
        assertEquals(NOT_FOUND_YET, theSquare.getWinnerValue());

        theSquare.changeStateOfAValue(3, LOSER_VALUE);

        assertEquals(LOSER_VALUE, valueStates[0]);
        assertEquals(POSSIBLE_VALUE, valueStates[1]);
        assertEquals(POSSIBLE_VALUE, valueStates[2]);
        assertEquals(LOSER_VALUE, valueStates[3]);
        assertEquals(POSSIBLE_VALUE, valueStates[4]);
        assertFalse(theSquare.isWinnerValueFound());
        assertEquals(NOT_FOUND_YET, theSquare.getWinnerValue());
    }

    @Test
    public void test_changeStateOfAValue_notPossibleToSetPOSSIBLE_VALUE(){
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);

        assertThrows(IllegalArgumentException.class,
                () -> theSquare.changeStateOfAValue(3, POSSIBLE_VALUE));
    }


    @Test
    public void test_setWinnerValue_indexTooBig() {
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);

        assertThrows(IllegalArgumentException.class,
                () -> theSquare.setWinnerValue(5));
    }

    @Test
    public void test_setWinnerValue() {
        int winnerValue = 3;
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);
        ValueState[] valueStates = theSquare.getValueState();

        assertEquals(LOSER_VALUE, valueStates[0]);
        assertEquals(POSSIBLE_VALUE, valueStates[1]);
        assertEquals(POSSIBLE_VALUE, valueStates[2]);
        assertEquals(POSSIBLE_VALUE, valueStates[3]);
        assertEquals(POSSIBLE_VALUE, valueStates[4]);
        assertFalse(theSquare.isWinnerValueFound());
        assertEquals(NOT_FOUND_YET, theSquare.getWinnerValue());

        theSquare.setWinnerValue(winnerValue);

        assertEquals(LOSER_VALUE, valueStates[0]);
        assertEquals(LOSER_VALUE, valueStates[1]);
        assertEquals(LOSER_VALUE, valueStates[2]);
        assertEquals(WINNER_VALUE, valueStates[3]);
        assertEquals(LOSER_VALUE, valueStates[4]);
        assertTrue(theSquare.isWinnerValueFound());
        assertEquals(winnerValue, theSquare.getWinnerValue());
    }

    @Test
    public void test_setWinnerValue_cannotUpdateALoserValue() {
        int value3 = 3;
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);
        theSquare.setLoserValue(value3);

        assertThrows(IllegalStateException.class,
                () -> theSquare.setWinnerValue(value3));
    }

    @Test
    public void test_setLoserValue_tooSmall() {
        int value0 = 0;

        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);

        assertThrows(IllegalArgumentException.class,
                () -> theSquare.setLoserValue(value0));
    }

    @Test
    public void test_setLoserValue() {
        int value3 = 3;

        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);
        ValueState[] valueStates = theSquare.getValueState();

        assertEquals(LOSER_VALUE, valueStates[0]);
        assertEquals(POSSIBLE_VALUE, valueStates[1]);
        assertEquals(POSSIBLE_VALUE, valueStates[2]);
        assertEquals(POSSIBLE_VALUE, valueStates[3]);
        assertEquals(POSSIBLE_VALUE, valueStates[4]);

        theSquare.setLoserValue(value3);

        assertEquals(LOSER_VALUE, valueStates[0]);
        assertEquals(POSSIBLE_VALUE, valueStates[1]);
        assertEquals(POSSIBLE_VALUE, valueStates[2]);
        assertEquals(LOSER_VALUE, valueStates[3]);
        assertEquals(POSSIBLE_VALUE, valueStates[4]);
    }

    @Test
    public void test_setLoserValue_cannotUpdateAWinnerValue() {
        int value3 = 3;
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);
        theSquare.setWinnerValue(value3);

        assertThrows(IllegalStateException.class,
                () -> theSquare.setLoserValue(value3));
    }

    @Test
    public void test_setLoserValue_whenRemainOneAutomaticallySetToWinnerValue() {
        int winnerValue = 2;
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);
        ValueState[] valueStates = theSquare.getValueState();

        theSquare.setLoserValue(1);
        assertFalse(theSquare.isWinnerValueFound());

        theSquare.setLoserValue(3);
        assertFalse(theSquare.isWinnerValueFound());

        theSquare.setLoserValue(4);
        assertTrue(theSquare.isWinnerValueFound());

        assertEquals(LOSER_VALUE, valueStates[0]);
        assertEquals(LOSER_VALUE, valueStates[1]);
        assertEquals(WINNER_VALUE, valueStates[2]);
        assertEquals(LOSER_VALUE, valueStates[3]);
        assertEquals(LOSER_VALUE, valueStates[4]);
        assertEquals(winnerValue, theSquare.getWinnerValue());
    }

    @Test
    public void test_setLoserValue_whenWinnerValueAlreadyFound() {
        int winnerValue = 4;

        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);
        theSquare.setLoserValue(1);
        theSquare.setLoserValue(3);
        theSquare.setWinnerValue(winnerValue);

        assertTrue(theSquare.isWinnerValueFound());
        assertEquals(winnerValue, theSquare.getWinnerValue());

        theSquare.setLoserValue(2);
        assertTrue(theSquare.isWinnerValueFound());
        assertEquals(winnerValue, theSquare.getWinnerValue());
    }

    @Test
    public void test_setLoserValue_shouldBeAbleToSetTwoTimesTheSameValue() {
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);
        theSquare.setLoserValue(1);
        theSquare.setLoserValue(1);
        assertFalse(theSquare.isWinnerValueFound());
    }

    @Test
    public void test_setWinnerValue_shouldBeAbleToSetTwoTimesTheSameValue() {
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);
        theSquare.setWinnerValue(1);
        theSquare.setWinnerValue(1);
        assertTrue(theSquare.isWinnerValueFound());
    }

    @Test
    public void test_setWinnerValue_shouldNotBeAbleToSetAnotherWinnerValue() {
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);
        theSquare.setWinnerValue(1);

        assertThrows(IllegalStateException.class,
                () -> theSquare.setWinnerValue(2));
    }

    @Test
    public void test_toString() {
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);

        String possibleValues = theSquare.toString();

        assertEquals("1,2,3,4", possibleValues);
    }

    @Test
    public void test_toString_oneLoserValue() {
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);
        theSquare.setLoserValue(2);

        String possibleValues = theSquare.toString();

        assertEquals("1,3,4", possibleValues);
    }

    @Test
    public void test_toString_oneWinnerValue() {
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);
        theSquare.setWinnerValue(2);

        String possibleValues = theSquare.toString();

        assertEquals("2", possibleValues);
    }

    @Test
    public void test_getPossibleValues_noChangeAfterConstruction() {
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);

        int[] expectedPossibleValues = {1, 2, 3, 4};
        int[] outputPossibleValues = theSquare.getPossibleValues();
        assertArrayEquals(expectedPossibleValues, outputPossibleValues);
    }

    @Test
    public void test_getPossibleValues_setOnLoser() {
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);
        theSquare.setLoserValue(2);

        int[] expectedPossibleValues = {1, 3, 4};
        int[] outputPossibleValues = theSquare.getPossibleValues();
        assertArrayEquals(expectedPossibleValues, outputPossibleValues);
    }

    @Test
    public void test_getPossibleValues_setOnWinner() {
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);
        theSquare.setWinnerValue(2);

        int[] expectedPossibleValues = {};
        int[] outputPossibleValues = theSquare.getPossibleValues();
        assertArrayEquals(expectedPossibleValues, outputPossibleValues);
    }

    @Test
    public void test_getWinnerValueOrPossibleValues_noWinnerValue() {
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);
        int[] expected = {1, 2, 3, 4};

        int[] output = theSquare.getWinnerValueOrPossibleValues();

        assertEquals(NB_POSSIBLE_VALUE, output.length);
        assertArrayEquals(expected, output);
    }

    @Test
    public void test_getWinnerValueOrPossibleValues_winnerValue() {
        int winnerValue = 2;
        SudokuSquare theSquare = buildSudokuSquare(NB_POSSIBLE_VALUE);
        theSquare.setWinnerValue(winnerValue);

        int[] output = theSquare.getWinnerValueOrPossibleValues();

        assertEquals(1, output.length);
        assertEquals(winnerValue, output[0]);
}


    private SudokuSquare buildSudokuSquare(int nbPossibleValues, int initialValue) {
        SudokuSquare square = new SudokuSquare(nbPossibleValues);
        setBroadcastWinner(square);
        square.setInitialValue(initialValue);

        return square;
    }

    private SudokuSquare buildSudokuSquare(int nbPossibleValues) {
        SudokuSquare square =  new SudokuSquare(nbPossibleValues);
        setBroadcastWinner(square);

        return square;
    }

    private void setBroadcastWinner(SudokuSquare square) {
        List<SudokuSquare> squares = Arrays.asList(square);
        BroadcastWinner region = new SudokuRegion(squares, SudokuRegion.SudokuRegionType.HORIZONTAL);
        SudokuRegions regions = new SudokuRegions();
        regions.addBroadcastWinner(region);
        square.setRegions(regions);
    }
}
