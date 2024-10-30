package main.java;

public class SudokuHelper {

    public static void printlnFoundValues(SudokuSquare[][] sudokuSquares) {
        for(int i = 0 ; i < sudokuSquares.length ; i++) {
            for (int j = 0; j < sudokuSquares[0].length; j++) {
                System.out.print(sudokuSquares[i][j].getWinnerValue() + "\t");
            }
            System.out.print("\n");
        }
    }

    public static void printlnRemainingPossibleValues(SudokuSquare[][] sudokuSquares) {
        for(int i = 0 ; i < sudokuSquares.length ; i++) {
            for (int j = 0; j < sudokuSquares[0].length; j++) {
                System.out.print(sudokuSquares[i][j].toString()+ "\t");
            }
            System.out.print("\n");
        }
    }


    public static boolean allWinnerFound(SudokuSquare[][] sudokuSquares) {
        for(int i = 0 ; i < sudokuSquares.length ; i++) {
            for(int j = 0 ; j < sudokuSquares[0].length ; j++) {
                if (! sudokuSquares[i][j].isWinnerValueFound())
                    return false;
            }
        }
        return true;
    }
}
