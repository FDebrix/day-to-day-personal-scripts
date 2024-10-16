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
    public void test_changeStateOfAValue_indexTooSmall() {
        int nbPossibleValues = 4;
        SudokuSquare aSquareWith4PossibleValues = new SudokuSquare(nbPossibleValues);

        assertThrows(IllegalArgumentException.class,
                () -> aSquareWith4PossibleValues.changeStateOfAValue(0, POSSIBLE_VALUE));
    }

    @Test
    public void test_changeStateOfAValue_indexTooBig() {
        int nbPossibleValues = 4;
        SudokuSquare aSquareWith4PossibleValues = new SudokuSquare(nbPossibleValues);

        assertThrows(IllegalArgumentException.class,
                () -> aSquareWith4PossibleValues.changeStateOfAValue(5, POSSIBLE_VALUE));
    }

    @Test
    public void test_changeStateOfAValue_LOSER_VALUE() {
        int nbPossibleValues = 4;
        int value3 = 3;
        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
        ValueState[] valueStates = theSquare.getValueState();

        assertEquals(POSSIBLE_VALUE, valueStates[value3]);
        assertFalse(theSquare.isWinnerValueFound());
        assertEquals(NOT_FOUND_YET, theSquare.getWinnerValue());

        theSquare.changeStateOfAValue(value3, LOSER_VALUE);

        assertEquals(LOSER_VALUE, valueStates[value3]);
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

        assertEquals(POSSIBLE_VALUE, valueStates[winnerValue]);
        assertFalse(theSquare.isWinnerValueFound());
        assertEquals(NOT_FOUND_YET, theSquare.getWinnerValue());

        theSquare.setWinnerValue(winnerValue);

        assertEquals(WINNER_VALUE, valueStates[winnerValue]);
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

        assertEquals(POSSIBLE_VALUE, valueStates[value3]);
        theSquare.setLoserValue(value3);
        assertEquals(LOSER_VALUE, valueStates[value3]);
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

        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
        theSquare.setLoserValue(1);
        theSquare.setLoserValue(3);
        theSquare.setLoserValue(4);
        assertTrue(theSquare.isWinnerValueFound());
        assertEquals(2, theSquare.getWinnerValue());
    }

    @Test
    public void test_setLoserValue_whenWinnerValueAlreadyFound() {
        int nbPossibleValues = 4;

        SudokuSquare theSquare = new SudokuSquare(nbPossibleValues);
        theSquare.setLoserValue(1);
        theSquare.setLoserValue(3);
        theSquare.setWinnerValue(4);
        assertTrue(theSquare.isWinnerValueFound());
        assertEquals(4, theSquare.getWinnerValue());
        theSquare.setLoserValue(2);
        assertTrue(theSquare.isWinnerValueFound());
        assertEquals(4, theSquare.getWinnerValue());
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
}
