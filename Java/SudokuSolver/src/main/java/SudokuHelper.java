package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuHelper {

    // System.out.println -> for tracking and investigation
    public static final boolean SOP = true;

    private static SudokuHelper instance;

    private SudokuHelper() {}

    public static SudokuHelper getInstance() {
        if(instance == null)
            instance = new SudokuHelper();
        return instance;
    }

    /**
     * Return the winner value of an array of SudokuSquare.
     * Keep in mind that the value 0 means the values is not found yet.
     * @param squares The squares to return the winner value.
     * @return The winner values.
     */
    // TODO to delete?
    public int[][] getWinnerValues(SudokuSquare[][] squares) {
        int sudokuRowSize = squares.length;
        int sudokuColSize = squares[0].length;

        int[][] result = new int[sudokuRowSize][sudokuColSize];

        for(int i = 0; i < sudokuRowSize ; i++) {
            for (int j = 0; j < sudokuColSize ; j++) {
                result[i][j] = squares[i][j].getWinnerValue();
            }
        }
        return result;
    }

    /**
     * Print the found values of all the squares.
     * @param squares The squares to print their winner values.
     */
    public void printlnFoundValues(SudokuSquare[][] squares) {
        for(SudokuSquare[] arrayOfSquares: squares) {
            for (SudokuSquare square : arrayOfSquares) {
                System.out.print(square.getWinnerValue() + "\t");
            }
            System.out.print("\n");
        }
    }

    /**
     * Print the remaining possible values of all the input squares.
     * @param squares The squares to print their possible values.
     */
    public void printlnRemainingPossibleValues(SudokuSquare[][] squares) {
        for(SudokuSquare[] arrayOfSquares: squares) {
            for (SudokuSquare square : arrayOfSquares) {
                System.out.print(square.toString()+ "\t");
            }
            System.out.print("\n");
        }
    }

    /**
     * Print the remaining possible values of all the input squares.
     * @param squarePossibleValues The squares to print their possible values.
     */
    public void printlnRemainingPossibleValues(int[][] squarePossibleValues) {
        for(int[] arrayOfPossibleValues: squarePossibleValues) {
            for (int possibleValue : arrayOfPossibleValues) {
                System.out.print(possibleValue + "\t");
            }
            System.out.print("\n");
        }
    }

    /**
     * Return true if all squares of the provided array found their winner value
     * @param squares The squares to test
     * @return true if all squares of the provided array found their winner value
     */
    public boolean allWinnerFound(SudokuSquare[][] squares) {
        if(squares == null || squares.length == 0)
            return false;

        for(SudokuSquare[] arrayOfSquares: squares) {
            if( ! allWinnerFound(Arrays.asList(arrayOfSquares))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Create a square using a list of possible values without setting any region.
     * If this function is used with one unique possible value, an IllegalStateException will be thrown
     * since the square does not have region assigned to it.
     * @param nbPossibleValues The number of possible values of the square.
     * @param possibleValues The possible values of the square.
     * @return The created square.
     */
    // TODO to review and maybe to fix the IllegalStateException when there is just one possible value.
    public SudokuSquare buildSquare(int nbPossibleValues, int... possibleValues) {
        SudokuSquare square = new SudokuSquare(nbPossibleValues);

        for(int oneOfAllPossibleValues = 1 ; oneOfAllPossibleValues <= nbPossibleValues ; oneOfAllPossibleValues++) {
            int finalOneOfAllPossibleValues = oneOfAllPossibleValues;
            if( Arrays.stream(possibleValues).noneMatch(i -> i == finalOneOfAllPossibleValues)) {
                square.setLoserValue(oneOfAllPossibleValues);
            }
        }
        return square;
    }

    /**
     * Return true if all squares of the provided region found their winner value
     * @param region The region to test
     * @return true if all squares of the provided region found their winner value
     */
    public boolean allWinnerFound(SudokuRegion region) {
        if(region == null)
            return false;

        return allWinnerFound(region.getSudokuSquares());
    }

    // The index i of the first list contains the list of all the squares who have for possible value i.
    public List<List<SudokuSquare>> getSquaresPerPossibleValues(SudokuRegion region) {
        if(region == null)
            return List.of();

        List<List<SudokuSquare>> squaresPerPossibleValues = buildEmptySquaresPerPossibleValues(region);

        for(SudokuSquare aSquare : region.getSudokuSquares()) {
            int[] values = aSquare.getWinnerValueOrPossibleValues();

            for(int value : values) {
                squaresPerPossibleValues.get(value).add(aSquare);
            }
        }

        return squaresPerPossibleValues;
    }

    private List<List<SudokuSquare>> buildEmptySquaresPerPossibleValues(SudokuRegion region) {
        int nbPossibleValues = region.getSudokuSquares().size();

        List<List<SudokuSquare>> squaresPerPossibleValues = new ArrayList<>();

        for (int i = 0 ; i < nbPossibleValues + 1 ; i++) {
            squaresPerPossibleValues.add(i, new ArrayList<>());
        }

        return squaresPerPossibleValues;
    }

    private boolean allWinnerFound(List<SudokuSquare> squares) {
        if(squares == null || squares.isEmpty())
            return false;

        for (SudokuSquare square : squares) {
            if (!square.isWinnerValueFound()) {
                return false;
            }
        }

        return true;
    }
}
