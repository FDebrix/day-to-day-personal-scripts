package main.java;

import java.util.StringJoiner;

import static main.java.SudokuSquare.ValueState.*;

/**
 * A sudoku is composer of squares. A square in a sudoku can have multiple values.
 * The number of values depend on the size of the sudoku.
 * At minimum, a square should have 4 different possible values. THe smallest possible value for a square must be 1.
 */
public class SudokuSquare {

    public enum ValueState {
        POSSIBLE_VALUE, WINNER_VALUE, LOSER_VALUE
    }

    public static final int NOT_FOUND_YET = 0;

    private static final int MIN_POSSIBLE_VALUES = 4;

    // The possible values of the square.
    // - the index i is used for the possible value i,
    // - the index 0 is not used.
    // A value starts in the state "POSSIBLE_VALUE" and then can just move to "WINNER_VALUE" or "LOSER_VALUE".
    // There is no way to change from "WINNER_VALUE" to "LOSER_VALUE", or vice versa.
    private ValueState[] possibleValues;

    // As soon as we found the winner value, we save it into attribute #foundValue.
    // We can have used possibleValues[0] to save it. But I prefer to keep the code clear.
    private int foundValue = NOT_FOUND_YET;

    private BroadcastWinner broadcastWinner;


    public SudokuSquare(int nbPossibleValues) {
        validateNbPossibleValues(nbPossibleValues);

        this.possibleValues = new ValueState[nbPossibleValues + 1];

        // The index 0 will not be used
        this.possibleValues[0] = LOSER_VALUE;

        for (int i = 1 ; i < this.possibleValues.length ; i++) {
            this.possibleValues[i] = POSSIBLE_VALUE;
        }
    }

    public void setBroadcastWinner(BroadcastWinner theBroadcastWinner) {
        if(theBroadcastWinner == null)
            throw new IllegalArgumentException("The input broadcast winner cannot by null");

        this.broadcastWinner = theBroadcastWinner;
    }

    public BroadcastWinner getBroadcastWinner() {
        return this.broadcastWinner;
    }

    // Used for unittests. Need to revisit to make it protected or package visibility.
    public ValueState[] getValueState() {
        return this.possibleValues;
    }

    public void changeStateOfAValue(int value, ValueState newState) {
        validateValue(value);
        validateNewStateIsWinnerOrLoser(newState);

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

    // The returned value can be NOT_FOUND_YET - this is correct
    public int getWinnerValue() {
        return this.foundValue;
    }

    public int[] getWinnerValueOrPossibleValues() {
        if(isWinnerValueFound()) return new int[] { this.foundValue } ;
        else return getPossibleValues();
    }

    public int[] getPossibleValues() {
        // TODO - we are looping two times on possibleValues
        int nbOfPossibleValues = countValuesInState_POSSIBLE_VALUE();

        int[] valueInStatePossibleValue = new int[nbOfPossibleValues];
        int indexForNextPossibleValue = 0;

        for(int i = 0 ; i < possibleValues.length ; i++) {
            if (possibleValues[i] == POSSIBLE_VALUE) {
                valueInStatePossibleValue[indexForNextPossibleValue] = i;
                indexForNextPossibleValue++;
            }
        }
        return valueInStatePossibleValue;
    }

    // 0 means that the winner value is unknown for now
    // else that means the winner value is known
    public void setInitialValue(int value) {
        if(value == NOT_FOUND_YET)
            return;
        setWinnerValue (value);
    }

    @Override
    public String toString() {

        if ( isWinnerValueFound() )
            return String.valueOf(getWinnerValue());
        else {
            StringJoiner joiner = new StringJoiner(",");

            for(int i = 0 ; i < possibleValues.length ; i++) {
                if(possibleValues[i] == POSSIBLE_VALUE) {
                    joiner.add(String.valueOf(i));
                }
            }
            return joiner.toString();
        }
    }




    private int countValuesInState_POSSIBLE_VALUE() {
        int nbOfPossibleValues = 0;

        for (ValueState possibleValue : possibleValues) {
            if (possibleValue == POSSIBLE_VALUE) {
                nbOfPossibleValues++;
            }
        }
        return nbOfPossibleValues;
    }

    private void computeLoserValue(int value) {
        // We already set the value as a loser value previously. Nothing to do.
        if(is_LOSER_VALUE(value))
            return;

        if(is_WINNER_VALUE(value))  {
            throw new IllegalStateException(
                    String.format("The value %s was set previously in the state %s. Then it cannot be updated to %s.",
                            value, possibleValues[value], LOSER_VALUE));
        }

        this.possibleValues[value] = LOSER_VALUE;

        checkAndSetTheUniquePOSSIBLE_VALUE_as_WINNER_VALUE();
    }

    private void computeWinnerValue(int value) {
        // We already set the value as the winner value previously. Nothing to do.
        if(is_WINNER_VALUE(value))
            return;

        // We have a winner value already, but its value is different of the input value.
        if(isWinnerValueFound()) {
            throw new IllegalStateException(
                    String.format("The value %s was set as the winner value. Then it should not be possible that %s now is the winner value.",
                            getWinnerValue(), value));
        }

        if(is_LOSER_VALUE(value))  {
            throw new IllegalStateException(
                    String.format("The value %s was set previously in the state %s. Then it cannot be updated to %s.",
                            value, possibleValues[value], WINNER_VALUE));
        }

        if(broadcastWinner == null) {
            throw new IllegalStateException("Cannot compute a winner value without a broadcast winner");
        }

        this.possibleValues[value] = WINNER_VALUE;
        this.foundValue = value;
        set_LOSER_VALUE_toAllValuesExcept(value);
        broadcastWinner.broadcastWinner(this);
    }

    private void set_LOSER_VALUE_toAllValuesExcept(int value) {
        for (int i = 1 ; i < this.possibleValues.length ; i++) {
            if(i != value) {
                setLoserValue(i);
            }
        }
    }

    private boolean is_WINNER_VALUE(int value) {
        return WINNER_VALUE == this.possibleValues[value];
    }

    private boolean is_LOSER_VALUE(int value) {
        return LOSER_VALUE == this.possibleValues[value];
    }

    private void checkAndSetTheUniquePOSSIBLE_VALUE_as_WINNER_VALUE() {
        if(isWinnerValueFound())
            return;

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

    private void validateNewStateIsWinnerOrLoser(ValueState newState) {
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
