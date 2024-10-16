package main.java;

import static main.java.SudokuSquare.ValueState.LOSER_VALUE;
import static main.java.SudokuSquare.ValueState.POSSIBLE_VALUE;
import static main.java.SudokuSquare.ValueState.WINNER_VALUE;

/**
 * A square in a sudoku can have multiple values. The number of values depend on the size of the sudoku.
 * At minimum, a square should have 4 different possible values. The smallest value is 1.
 *
 * To simplify our life, each possible value is represented by the same index.
 */
public class SudokuSquare {

    public enum ValueState {
        POSSIBLE_VALUE, WINNER_VALUE, LOSER_VALUE
    }

    public static final int NOT_FOUND_YET = -1;

    private static final int MIN_POSSIBLE_VALUES = 4;

    private ValueState[] possibleValues;
    private int foundValue = NOT_FOUND_YET;


    public SudokuSquare(int nbPossibleValues) {
        validateNbPossibleValues(nbPossibleValues);

        this.possibleValues = new ValueState[nbPossibleValues + 1];

        // The index 0 will not be used
        this.possibleValues[0] = LOSER_VALUE;

        for (int i = 1 ; i < this.possibleValues.length ; i++) {
            this.possibleValues[i] = POSSIBLE_VALUE;
        }
    }

    public ValueState[] getValueState() {
        return this.possibleValues;
    }

    public void changeStateOfAValue(int value, ValueState newState) {
        validateValue(value);
        validateNewState(newState);

        if(newState == WINNER_VALUE)
            computeWinnerValue(value);
        else
            computeLoserValue(value);
    }

    public void setWinnerValue(int value) {
        changeStateOfAValue(value, WINNER_VALUE);
    }

    public void setLoserValue(int value) {
        changeStateOfAValue(value, LOSER_VALUE);
    }

    public boolean isWinnerValueFound() {
        return this.foundValue != NOT_FOUND_YET;
    }

    public int getWinnerValue() {
        return this.foundValue;
    }



    private void computeLoserValue(int value) {
        if(is_WINNER_VALUE(value))  {
            throw new IllegalStateException(
                    String.format("The value %s was set previously in the state %s. Then it cannot be updated to %s.",
                            value, possibleValues[value], LOSER_VALUE));
        }

        this.possibleValues[value] = LOSER_VALUE;
        checkAndSetTheUniquePOSSIBLE_VALUE_as_WINNER_VALUE();
    }

    private void computeWinnerValue(int value) {
        if(is_LOSER_VALUE(value))  {
            throw new IllegalStateException(
                    String.format("The value %s was set previously in the state %s. Then it cannot be updated to %s.",
                            value, possibleValues[value], WINNER_VALUE));
        }

        this.possibleValues[value] = WINNER_VALUE;
        this.foundValue = value;
    }

    private boolean is_WINNER_VALUE(int value) {
        return WINNER_VALUE == this.possibleValues[value];
    }

    private boolean is_LOSER_VALUE(int value) {
        return LOSER_VALUE == this.possibleValues[value];
    }

    private void checkAndSetTheUniquePOSSIBLE_VALUE_as_WINNER_VALUE() {
        int nbOfPOSSIBLE_VALUE = 0;
        int lastValuePOSSIBLE_VALUE = 0;

        for (int i = 1 ; i < this.possibleValues.length ; i++) {
            if (this.possibleValues[i] == POSSIBLE_VALUE) {
                nbOfPOSSIBLE_VALUE++;
                lastValuePOSSIBLE_VALUE = i;
            }
        }

        if(nbOfPOSSIBLE_VALUE == 1) {
            computeWinnerValue(lastValuePOSSIBLE_VALUE);
        }
    }

    private void validateNewState(ValueState newState) {
        if(newState != WINNER_VALUE && newState != LOSER_VALUE)
            throw new IllegalArgumentException(
                    String.format("Only the state %s and %s are allowed", WINNER_VALUE, LOSER_VALUE));
    }

    private void validateValue (int value) throws IllegalArgumentException {
        if(value <= 0 || value >= this.possibleValues.length)
            throw new IllegalArgumentException(
                    String.format("You can just update the values between %s and %s.", 1, possibleValues.length - 1));
    }

    private void validateNbPossibleValues(int nbPossibleValues) throws IllegalArgumentException  {
        if (nbPossibleValues < MIN_POSSIBLE_VALUES)
            throw new IllegalArgumentException(
                    String.format("A square should have at less %s values", MIN_POSSIBLE_VALUES));
    }
}
