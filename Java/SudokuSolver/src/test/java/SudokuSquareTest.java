package test.java;

import main.java.SudokuSquare;
import main.java.SudokuSquare.ValueState;
import org.junit.jupiter.api.Test;

import static main.java.SudokuSquare.ValueState.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SudokuSquareTest {

    @Test
    public void test_constructor_nbPossibleValuesTooSmall() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new SudokuSquare(0));
    }

    @Test
    public void test_constructor_4PossibleValues() {
        int nbPossibleValues = 4;
        SudokuSquare aSquareWith4PossibleValues = new SudokuSquare(nbPossibleValues);

        ValueState[] valueStates = aSquareWith4PossibleValues.getValueState();

        assertEquals(nbPossibleValues + 1, valueStates.length);
        assertEquals(NOT_THIS_VALUE, valueStates[0]);

        for (int i = 1 ; i < valueStates.length ; i++) {
            assertEquals(POSSIBLE_VALUE, valueStates[i]);
        }
    }

    @Test
    public void test_changeStateOfAValue_index0() {
        int nbPossibleValues = 4;
        SudokuSquare aSquareWith4PossibleValues = new SudokuSquare(nbPossibleValues);

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> aSquareWith4PossibleValues.changeStateOfAValue(0, POSSIBLE_VALUE));
    }

    @Test
    public void test_changeStateOfAValue_index5() {
        int nbPossibleValues = 4;
        SudokuSquare aSquareWith4PossibleValues = new SudokuSquare(nbPossibleValues);

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> aSquareWith4PossibleValues.changeStateOfAValue(5, POSSIBLE_VALUE));
    }


    @Test
    public void test_changeStateOfAValue_index4() {
        int nbPossibleValues = 4;
        SudokuSquare aSquareWith4PossibleValues = new SudokuSquare(nbPossibleValues);
        ValueState[] valueStates = aSquareWith4PossibleValues.getValueState();
        assertEquals(POSSIBLE_VALUE, valueStates[4]);

        aSquareWith4PossibleValues.changeStateOfAValue(4, WINNER_VALUE);

        assertEquals(WINNER_VALUE, valueStates[4]);
    }
}
