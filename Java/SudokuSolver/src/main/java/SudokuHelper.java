package main.java;

public class SudokuHelper {

    private static SudokuHelper instance;

    private SudokuHelper() {}

    public static SudokuHelper getInstance() {
        if(instance == null)
            instance = new SudokuHelper();
        return instance;
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
