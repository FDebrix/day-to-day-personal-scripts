package test.java;

import main.java.SudokuSolver;
import main.java.SudokuSquare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        new SudokuSolver(SUDOKU_TO_RESOLVE);
    }

    @Test
    public void test_constructor_inconsistent() {
        int[][] sudokuToResolve = {
                {0, 1, 7, 0, 4, 5, 0, 0, 0},
                {0, 9, 4, 0, 2, 0, 8}};

        assertThrows(IllegalArgumentException.class,
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
        int nbRows = SUDOKU_TO_RESOLVE.length;
        int nbColumns = SUDOKU_TO_RESOLVE[0].length;
        SudokuSolver solver = new SudokuSolver(SUDOKU_TO_RESOLVE);
        SudokuSquare[][] sudokuSquares = solver.convertArrayToSudokuSquare();

        assertEquals(nbRows, sudokuSquares.length);

        for (int i = 0 ; i < SUDOKU_TO_RESOLVE.length ; i++) {
            assertEquals(nbColumns, sudokuSquares[i].length);
        }

        for(int i = 0 ; i < nbRows ; i++) {
            for (int j = 0; j < nbColumns; j++) {
                if (SUDOKU_TO_RESOLVE[i][j] != 0) {
                    assertTrue(sudokuSquares[i][j].isWinnerValueFound());
                    assertEquals(SUDOKU_TO_RESOLVE[i][j], sudokuSquares[i][j].getWinnerValue());
                }
                else {
                    assertFalse(sudokuSquares[i][j].isWinnerValueFound());
                }
            }
        }

        // solver.printlnFoundValues();
        //solver.printlnRemainingPossibleValues();
    }

    @Test
    public void test_run() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_TO_RESOLVE);
        solver.convertArrayToSudokuSquare();
        solver.printlnRemainingPossibleValues();
        solver.letDoAFirstRun();
        System.out.println("\nAFTER\n");
        solver.printlnRemainingPossibleValues();
    }


}
