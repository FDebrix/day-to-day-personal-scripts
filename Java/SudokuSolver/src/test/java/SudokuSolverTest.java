package test.java;

import main.java.SudokuSolver;
import main.java.SudokuSquare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.java.SudokuTestHelper.*;

public class SudokuSolverTest {

    @Test
    public void test_constructor() {
        new SudokuSolver(SUDOKU_1_9x9_SIMPLE, 3, 3, 9, 9);
    }


    @Test
    public void test_getSudoku() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_1_9x9_SIMPLE, 3, 3, 9, 9);

        int[][] sudokuOutput = solver.getSudokuToResolve();

        assertEquals(SUDOKU_1_9x9_SIMPLE, sudokuOutput);
    }

    @Test
    public void test_convertArrayToSudokuSquare(){
        int nbRows = SUDOKU_1_9x9_SIMPLE.length;
        int nbColumns = SUDOKU_1_9x9_SIMPLE[0].length;
        SudokuSolver solver = new SudokuSolver(SUDOKU_1_9x9_SIMPLE, 3, 3, 9, 9);
        SudokuSquare[][] sudokuSquares = solver.getSudokuSquaresForTheValue();

        assertEquals(nbRows, sudokuSquares.length);

        for (int i = 0; i < SUDOKU_1_9x9_SIMPLE.length ; i++) {
            assertEquals(nbColumns, sudokuSquares[i].length);
        }

        for(int i = 0 ; i < nbRows ; i++) {
            for (int j = 0; j < nbColumns; j++) {

                assertEquals(i, sudokuSquares[i][j].getRowId());
                assertEquals(j, sudokuSquares[i][j].getColId());

                if (SUDOKU_1_9x9_SIMPLE[i][j] != 0) {
                    assertTrue(sudokuSquares[i][j].isWinnerValueFound());
                    assertEquals(SUDOKU_1_9x9_SIMPLE[i][j], sudokuSquares[i][j].getWinnerValue());
                }
                else {
                    assertFalse(sudokuSquares[i][j].isWinnerValueFound());
                }
            }
        }
    }

    @Test
    public void test_run_2x2() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_1_2x2, 2, 2, 4, 4);

        solver.resolveTheSudoku();

        int[][] output = solver.getWinnerValues();
        assertEquals(SUDOKU_1_2x2.length, output.length);
        assertEquals(SUDOKU_1_2x2[0].length, output[0].length);
        assertArrayEquals(SUDOKU_1_2x2_EXPECTED, output);
    }

    @Test
    public void test_run_9x9_simple() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_1_9x9_SIMPLE, 3, 3, 9, 9);

        solver.resolveTheSudoku();

        int[][] output = solver.getWinnerValues();
        assertEquals(SUDOKU_1_9x9_SIMPLE.length, output.length);
        assertEquals(SUDOKU_1_9x9_SIMPLE[0].length, output[0].length);
        assertArrayEquals(SUDOKU_1_9x9_SIMPLE_EXPECTED, output);
    }

    @Test
    public void test_run_9x9_medium() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_1_9x9_MEDIUM, 3, 3, 9, 9);

        solver.resolveTheSudoku();

        int[][] output = solver.getWinnerValues();
        assertEquals(SUDOKU_1_9x9_MEDIUM.length, output.length);
        assertEquals(SUDOKU_1_9x9_MEDIUM[0].length, output[0].length);

        assertArrayEquals(SUDOKU_1_9x9_MEDIUM_EXPECTED, output);
    }

    @Test
    public void test_run_9x9_difficile() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_1_9x9_DIFFICILE, 3, 3, 9, 9);

        solver.resolveTheSudoku();

        int[][] output = solver.getWinnerValues();
        assertEquals(SUDOKU_1_9x9_DIFFICILE.length, output.length);
        assertEquals(SUDOKU_1_9x9_DIFFICILE[0].length, output[0].length);

        assertArrayEquals(SUDOKU_1_9x9_DIFFICILE_EXPECTED, output);
    }


    @Test
    public void test_run_9x9_expert() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_1_9x9_EXPERT, 3, 3, 9, 9);

        solver.resolveTheSudoku();

        System.out.println("\nEXPERT\n");
        solver.printlnRemainingPossibleValues();

        int[][] output = solver.getWinnerValues();
        assertEquals(SUDOKU_1_9x9_EXPERT.length, output.length);
        assertEquals(SUDOKU_1_9x9_EXPERT[0].length, output[0].length);


        assertArrayEquals(SUDOKU_1_9x9_EXPERT_EXPECTED, output);
    }
}
