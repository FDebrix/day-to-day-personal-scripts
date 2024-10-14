package main.java;

public class SudokuSolver {

    private int[][] sudokuToResolve;

    public SudokuSolver (int[][] sudoKuToResolve) {
        validateSudokuToResolveIsConsistent(sudoKuToResolve);

        this.sudokuToResolve = sudoKuToResolve;
    }

    public int[][] getSudoku() {
        return sudokuToResolve;
    }


    public SudokuSquare[][] convertArrayToSudokuSquare() {
        int nbRows = sudokuToResolve.length;
        int nbColumns = sudokuToResolve[0].length;
        SudokuSquare[][] sudokuSquares = new SudokuSquare[nbRows][nbColumns];

        // TO IMPLEMENT


        return sudokuSquares;
    }

    private void validateSudokuToResolveIsConsistent(int[][] sudoKuToResolve) {
        int lengthFirstRow = sudoKuToResolve[0].length;

        for (int[] aRow: sudoKuToResolve) {
            if (aRow.length != lengthFirstRow)
                throw new IllegalArgumentException("The length of the rows is not consistent");
        }
    }
}
