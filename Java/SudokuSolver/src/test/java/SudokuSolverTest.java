package test.java;

import main.java.SudokuSolver;
import main.java.SudokuSquare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SudokuSolverTest {


    private final int[][] SUDOKU_TO_RESOLVE = {
            {0, 1, 7, 0, 4, 5, 0, 0, 0},
            {0, 9, 4, 0, 2, 0, 8, 5, 1},
            {0, 6, 0, 8, 0, 0, 0, 7, 3},
            {1, 2, 0, 0, 0, 0, 6, 8, 0},
            {5, 0, 9, 1, 6, 2, 0, 0, 4},
            {0, 4, 0, 0, 0, 8, 0, 1, 9},
            {0, 0, 2, 9, 1, 3, 0, 0, 0},
            {0, 0, 1, 6, 8, 0, 0, 0, 2},
            {0, 0, 0, 2, 0, 0, 0, 9, 0}
    };

    @Test
    public void test_constructor() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_TO_RESOLVE);
    }

    @Test
    public void test_constructor_inconsistent() {
        int[][] sudokuToResolve = {
                {0, 1, 7, 0, 4, 5, 0, 0, 0},
                {0, 9, 4, 0, 2, 0, 8}};

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new SudokuSolver(sudokuToResolve));
    }

    @Test
    public void test_getSudoku() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_TO_RESOLVE);

        int[][] sudokuOutput = solver.getSudoku();

        assertEquals(SUDOKU_TO_RESOLVE, sudokuOutput);
    }

    @Test
    public void test_convertArrayToSudokuSquare(){
        SudokuSolver solver = new SudokuSolver(SUDOKU_TO_RESOLVE);
        SudokuSquare[][] sudokuSquares = solver.convertArrayToSudokuSquare();

        assertEquals(SUDOKU_TO_RESOLVE.length, sudokuSquares.length);

        for (int i = 0 ; i < SUDOKU_TO_RESOLVE.length ; i++) {
            assertEquals(SUDOKU_TO_RESOLVE[i].length, sudokuSquares[i].length);
        }

        assertEquals(1, SUDOKU_TO_RESOLVE[0][1]);
        // TODO
        // assertEquals(1, sudokuSquares[0][1].getWinnerValue());
    }


    /*
    private void areSame(int[][] sudokuExpected, int[][] sudokuOutput) {
        System.out.printf("exp %s output %s", sudokuExpected, sudokuOutput);
        assertEquals(sudokuExpected.length, sudokuOutput.length);
        for (int i = 0 ; i < sudokuExpected.length ; i++) {
            assertEquals(sudokuExpected[i].length, sudokuOutput[i].length);
        }
        for (int i = 0 ; i < sudokuExpected.length ; i++) {
            for (int j = 0 ; j < sudokuExpected[i].length ; j++) {
                System.out.printf("i %s, j %s, expected %s, output %s%n", i, j, sudokuExpected[i][j], sudokuOutput[i][j]);
                assertEquals(sudokuExpected[i][j], sudokuOutput[i][j]);
            }
        }
    }

     */
}
