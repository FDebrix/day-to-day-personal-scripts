package main.java;

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

    public void printlnFoundValues(SudokuSquare[][] sudokuSquares) {
        for(SudokuSquare[] arrayOfSquares: sudokuSquares) {
            for (SudokuSquare square : arrayOfSquares) {
                System.out.print(square.getWinnerValue() + "\t");
            }
            System.out.print("\n");
        }
    }

    public void printlnRemainingPossibleValues(SudokuSquare[][] sudokuSquares) {
        for(SudokuSquare[] arrayOfSquares: sudokuSquares) {
            for (SudokuSquare square : arrayOfSquares) {
                System.out.print(square.toString()+ "\t");
            }
            System.out.print("\n");
        }
    }

    public void printlnRemainingPossibleValues(int[][] sudokuSquares) {
        for(int[] arrayOfPossibleValues: sudokuSquares) {
            for (int possibleValue : arrayOfPossibleValues) {
                System.out.print(possibleValue + "\t");
            }
            System.out.print("\n");
        }
    }
    public boolean allWinnerFound(SudokuSquare[][] sudokuSquares) {
        if(sudokuSquares == null || sudokuSquares.length == 0)
            return false;

        for(SudokuSquare[] arrayOfSquares: sudokuSquares) {
            for (SudokuSquare square : arrayOfSquares) {
                if (! square.isWinnerValueFound())
                    return false;
            }
        }
        return true;
    }

    public boolean allWinnerFound(SudokuRegion region) {
        if(region == null)
            return false;

        List<SudokuSquare> squares = region.getSudokuSquares();

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
