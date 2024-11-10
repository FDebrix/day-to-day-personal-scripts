package main;

import main.java.SudokuSolver;

public class Main {

    static int[][] SUDOKU_id7_9x9_EXTREME_INPUT = {
            {1, 0, 0, 3, 0, 0, 0, 0, 0},
            {8, 0, 0, 0, 0, 0, 0, 0, 6},
            {0, 0, 5, 0, 1, 9, 4, 0, 0},
            {0, 0, 0, 0, 0, 2, 0, 0, 0},
            {0, 0, 1, 0, 7, 5, 9, 0, 0},
            {0, 9, 0, 0, 0, 0, 0, 4, 0},
            {0, 0, 0, 2, 0, 0, 7, 0, 0},
            {0, 0, 3, 4, 0, 0, 0, 0, 0},
            {5, 0, 0, 0, 3, 7, 0, 8, 0}
    };

    public static void main(String[] args) {
        SudokuSolver solver = new SudokuSolver(SUDOKU_id7_9x9_EXTREME_INPUT);
        solver.resolveTheSudoku();
        solver.printPossibleValues();
    }
}
