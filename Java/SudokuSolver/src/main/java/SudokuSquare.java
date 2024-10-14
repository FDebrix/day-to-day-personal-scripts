package main.java;

/**
 * A square in a sudoku can have multiple values. The number of values depend on the size of the suduko.
 * At minimum, a square should have 4 different possible values. The smallest value is 1.
 *
 * To simplify our life, each possible value is represented by the same index.
 */
public class SudokuSquare {

    private static final int MIN_POSSIBLE_VALUES = 4;

    private ValueState[] possibleValues;


    public SudokuSquare(int nbPossibleValues) {
        validateNbPossibleValues(nbPossibleValues);

        possibleValues = new ValueState[nbPossibleValues + 1];

        // The index 0 will not be used
        possibleValues[0] = ValueState.NOT_THIS_VALUE;

        for (int i = 1 ; i < possibleValues.length ; i++) {
            possibleValues[i] = ValueState.POSSIBLE_VALUE;
        }
    }

    public ValueState[] getValueState() {
        return possibleValues;
    }

    public void changeStateOfAValue(int value, ValueState newState) {
        validateValue(value);

        possibleValues[value] = newState;
    }

    public int getWinnerValue() {
        for(int i = 1 ; i < possibleValues.length ; i++) {
            if (possibleValues[i] == ValueState.WINNER_VALUE)
                return i;
        }

        return 0;
    }


    public enum ValueState {
        POSSIBLE_VALUE, WINNER_VALUE, NOT_THIS_VALUE
    }

    private void validateValue (int value) throws IllegalArgumentException {
        if(value <= 0 || value >= possibleValues.length)
            throw new IllegalArgumentException(String.format("You can just update the values between %s and %s.", 1, possibleValues.length - 1));
    }

    private void validateNbPossibleValues(int nbPossibleValues) throws IllegalArgumentException  {
        if (nbPossibleValues < MIN_POSSIBLE_VALUES)
            throw new IllegalArgumentException(String.format("A square should have at less %s values", MIN_POSSIBLE_VALUES));
    }
}
