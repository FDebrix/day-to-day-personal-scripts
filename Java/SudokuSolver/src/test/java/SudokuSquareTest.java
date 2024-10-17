package test.java;

import main.java.SudokuSquare;
import main.java.SudokuSquare.ValueState;
import org.junit.jupiter.api.Test;

import static main.java.SudokuSquare.NOT_FOUND_YET;
import static main.java.SudokuSquare.ValueState.*;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuSquareTest {

    @Test
    public void test_constructor_nbPossibleValuesTooSmall() {
        assertThrows(IllegalArgumentException.class,
                () -> new SudokuSquare(0));
    }

    @Test
    public void test_changeStateOfAValue_indexTooSmall() {
        int nbPossibleValues = 4;
        SudokuSquare aSquareWith4PossibleValues = new SudokuSquare(nbPossibleValues);

        assertThrows(IllegalArgumentException.class,
                () -> aSquareWith4PossibleValues.changeStateOfAValue(0, LOSER_VALUE));
    }

    @Test
    public void test_changeStateOfAValue_indexTooBig() {
        int nbPossibleValues = 4;
        SudokuSquare aSquareWith4PossibleValues = new SudokuSquare(nbPossibleValues);

        assertThrows(IllegalArgumentException.class,
                () -> aSquareWith4PossibleValues.changeStateOfAValue(5, LOSER_VALUE));
    }

    @Test
    public void test_constructor_getValueState_4PossibleValues() {
        int nbPossibleValues = 4;
        int sizeOfTheOutputTable = nbPossibleValues + 1;
        SudokuSquare aSquareWith4PossibleValues = new SudokuSquare(nbPossibleValues);

        ValueState[] valueStates = aSquareWith4PossibleValues.getValueState();

        assertEquals(sizeOfTheOutputTable, valueStates.length);
        assertEquals(LOSER_VALUE, valueStates[0]);

        for (int i = 1 ; i < valueStates.length ; i++) {
            assertEquals(POSSIBLE_VALUE, valueStates[i]);
        }
    }

    @Test
    public void test_constructor_getValueState_4PossibleValuesAndInitialValue0() {
        int nbPossibleValues = 4;
        int sizeOfTheOutputTable = nbPossibleValues + 1;
        SudokuSquare aSquareWith4PossibleValues = new SudokuSquare(nbPossibleValues, 0);

        ValueState[] valueStates = aSquareWith4PossibleValues.getValueState();

        assertEquals(sizeOfTheOutputTable, valueStates.length);
        assertEquals(LOSER_VALUE, valueStates[0]);

        for (int i = 1 ; i < valueStates.length ; i++) {
            assertEquals(POSSIBLE_VALUE, valueStates[i]);
        }
    }

    @Test
    public void test_setInitialValue_winnerValue() {
        int nbPossibleValues = 4;
        int winnerValue = 2;

        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues, winnerValue);

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
        int nbPossibleValues = 4;
        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
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
        int nbPossibleValues = 4;
        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);

        assertThrows(IllegalArgumentException.class,
                () -> theSquare.changeStateOfAValue(3, POSSIBLE_VALUE));
    }


    @Test
    public void test_setWinnerValue_indexTooBig() {
        int nbPossibleValues = 4;
        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);

        assertThrows(IllegalArgumentException.class,
                () -> theSquare.setWinnerValue(5));
    }

    @Test
    public void test_setWinnerValue() {
        int nbPossibleValues = 4;
        int winnerValue = 3;
        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
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
        int nbPossibleValues = 4;
        int value3 = 3;
        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
        theSquare.setLoserValue(value3);

        assertThrows(IllegalStateException.class,
                () -> theSquare.setWinnerValue(value3));
    }

    @Test
    public void test_setLoserValue_tooSmall() {
        int nbPossibleValues = 4;
        int value0 = 0;

        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);

        assertThrows(IllegalArgumentException.class,
                () -> theSquare.setLoserValue(value0));
    }

    @Test
    public void test_setLoserValue() {
        int nbPossibleValues = 4;
        int value3 = 3;

        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
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
        int nbPossibleValues = 4;
        int value3 = 3;
        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
        theSquare.setWinnerValue(value3);

        assertThrows(IllegalStateException.class,
                () -> theSquare.setLoserValue(value3));
    }

    @Test
    public void test_setLoserValue_whenRemainOneAutomaticallySetToWinnerValue() {
        int nbPossibleValues = 4;
        int winnerValue = 2;
        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
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
        int nbPossibleValues = 4;
        int winnerValue = 4;

        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
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
        int nbPossibleValues = 4;

        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
        theSquare.setLoserValue(1);
        theSquare.setLoserValue(1);
        assertFalse(theSquare.isWinnerValueFound());
    }

    @Test
    public void test_setWinnerValue_shouldBeAbleToSetTwoTimesTheSameValue() {
        int nbPossibleValues = 4;

        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
        theSquare.setWinnerValue(1);
        theSquare.setWinnerValue(1);
        assertTrue(theSquare.isWinnerValueFound());
    }

    @Test
    public void test_setWinnerValue_shouldNotBeAbleToSetAnotherWinnerValue() {
        int nbPossibleValues = 4;

        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
        theSquare.setWinnerValue(1);

        assertThrows(IllegalStateException.class,
                () -> theSquare.setWinnerValue(2));
    }

    @Test
    public void test_winnerValueDigestedAtTheSudokuLayer() {
        int nbPossibleValues = 4;

        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
        assertFalse(theSquare.winnerValueDigestedAtTheSudokuLayer());
    }

    @Test
    public void test_setWinnerValueDigestedAtTheSudokuLayer_whenWinnerValueNotFoundYet(){
        int nbPossibleValues = 4;

        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
        assertThrows(IllegalStateException.class,
                () -> theSquare.setWinnerValueDigestedAtTheSudokuLayer());
    }

    @Test
    public void test_setWinnerValueDigestedAtTheSudokuLayer_whenWinnerValueFound(){
        int nbPossibleValues = 4;
        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
        theSquare.setWinnerValue(3);

        theSquare.setWinnerValueDigestedAtTheSudokuLayer();

        assertTrue(theSquare.winnerValueDigestedAtTheSudokuLayer());
    }

    @Test
    public void test_toString() {
        int nbPossibleValues = 4;
        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);

        String possibleValues = theSquare.toString();

        assertEquals("1,2,3,4", possibleValues);
    }

    @Test
    public void test_toString_oneLoserValue() {
        int nbPossibleValues = 4;
        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
        theSquare.setLoserValue(2);

        String possibleValues = theSquare.toString();

        assertEquals("1,3,4", possibleValues);
    }

    @Test
    public void test_toString_oneWinnerValue() {
        int nbPossibleValues = 4;
        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
        theSquare.setWinnerValue(2);

        String possibleValues = theSquare.toString();

        assertEquals("2", possibleValues);
    }
}
