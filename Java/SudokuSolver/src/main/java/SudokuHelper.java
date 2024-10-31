package main.java;

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
        for(int i = 0 ; i < sudokuSquares.length ; i++) {
            for (int j = 0; j < sudokuSquares[0].length; j++) {
                System.out.print(sudokuSquares[i][j].getWinnerValue() + "\t");
            }
            System.out.print("\n");
        }
    }

    public void printlnRemainingPossibleValues(SudokuSquare[][] sudokuSquares) {
        for(int i = 0 ; i < sudokuSquares.length ; i++) {
            for (int j = 0; j < sudokuSquares[0].length; j++) {
                System.out.print(sudokuSquares[i][j].toString()+ "\t");
            }
            System.out.print("\n");
        }
    }

    public boolean allWinnerFound(SudokuSquare[][] sudokuSquares) {
        if(sudokuSquares == null || sudokuSquares.length == 0)
            return false;

        for(int i = 0 ; i < sudokuSquares.length ; i++) {
            for(int j = 0 ; j < sudokuSquares[0].length ; j++) {
                if (! sudokuSquares[i][j].isWinnerValueFound())
                    return false;
            }
        }
        return true;
    }
}
