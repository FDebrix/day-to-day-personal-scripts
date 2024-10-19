package test.java;

import main.java.SudokuSolver;
import main.java.SudokuSquare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuSolverTest {


    private final int[][] SUDOKU_1_2x2 = {
            {0, 0, 0, 4},
            {0, 0, 0, 0},
            {2, 0, 0, 3},
            {4, 0, 1, 2}
    };

    private final int[][] SUDOKU_1_2x2_EXPECTED = {
            {1, 2, 3, 4},
            {3, 4, 2, 1},
            {2, 1, 4, 3},
            {4, 3, 1, 2}
    };


    private final int[][] SUDOKU_1_9x9_SIMPLE = {
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

    private final int[][] SUDOKU_1_9x9_SIMPLE_EXPECTED = {
            {8, 1, 7, 3, 4, 5, 9, 2, 6},
            {3, 9, 4, 7, 2, 6, 8, 5, 1},
            {2, 6, 5, 8, 9, 1, 4, 7, 3},
            {1, 2, 3, 4, 7, 9, 6, 8, 5},
            {5, 8, 9, 1, 6, 2, 7, 3, 4},
            {7, 4, 6, 5, 3, 8, 2, 1, 9},
            {4, 7, 2, 9, 1, 3, 5, 6, 8},
            {9, 5, 1, 6, 8, 7, 3, 4, 2},
            {6, 3, 8, 2, 5, 4, 1, 9, 7}
    };


    private final int[][] SUDOKU_1_9x9_MEDIUM = {
            {0, 6, 0, 0, 1, 2, 0, 5, 0},
            {0, 5, 3, 7, 8, 0, 0, 0, 0},
            {0, 0, 7, 0, 0, 0, 0, 0, 9},
            {2, 0, 4, 6, 7, 0, 5, 9, 1},
            {6, 0, 5, 3, 4, 1, 7, 8, 0},
            {8, 0, 1, 0, 0, 0, 0, 0, 3},
            {0, 1, 0, 2, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 3, 7, 9, 0, 6},
            {0, 0, 6, 0, 0, 0, 2, 7, 0}
    };

    private final int[][] SUDOKU_1_9x9_MEDIUM_EXPECTED = {
            {4, 6, 8, 9, 1, 2, 3, 5, 7},
            {9, 5, 3, 7, 8, 6, 1, 2, 4},
            {1, 2, 7, 4, 5, 3, 8, 6, 9},
            {2, 3, 4, 6, 7, 8, 5, 9, 1},
            {6, 9, 5, 3, 4, 1, 7, 8, 2},
            {8, 7, 1, 5, 2, 9, 6, 4, 3},
            {7, 1, 9, 2, 6, 5, 4, 3, 8},
            {5, 4, 2, 8, 3, 7, 9, 1, 6},
            {3, 8, 6, 1, 9, 4, 2, 7, 5}
    };


    private final int[][] SUDOKU_1_9x9_DIFFICILE = {
            {7, 3, 4, 0, 9, 0, 0, 0, 0},
            {0, 0, 5, 0, 3, 1, 4, 0, 0},
            {0, 0, 0, 0, 0, 7, 0, 3, 8},
            {0, 4, 2, 0, 1, 9, 0, 8, 7},
            {5, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 9, 7, 0, 0, 0, 0, 0, 0},
            {9, 0, 6, 1, 7, 0, 3, 4, 0},
            {8, 5, 0, 0, 0, 0, 0, 2, 0},
            {0, 0, 0, 6, 0, 0, 0, 0, 0}
    };

    private final int[][] SUDOKU_1_9x9_DIFFICILE_EXPECTED = {
            {7, 3, 4, 8, 9, 5, 1, 6, 2},
            {6, 8, 5, 2, 3, 1, 4, 7, 9},
            {2, 1, 9, 4, 6, 7, 5, 3, 8},
            {3, 4, 2, 5, 1, 9, 6, 8, 7},
            {5, 6, 8, 7, 2, 4, 9, 1, 3},
            {1, 9, 7, 3, 8, 6, 2, 5, 4},
            {9, 2, 6, 1, 7, 8, 3, 4, 5},
            {8, 5, 1, 9, 4, 3, 7, 2, 6},
            {4, 7, 3, 6, 5, 2, 8, 9, 1}
    };


    private final int[][] SUDOKU_1_9x9_EXPERT = {
            {0, 0, 0, 5, 0, 0, 0, 0, 0},
            {0, 0, 0, 8, 4, 2, 7, 9, 5},
            {5, 9, 0, 7, 0, 0, 0, 0, 0},
            {4, 0, 6, 0, 0, 0, 8, 0, 2},
            {0, 0, 8, 0, 7, 6, 0, 0, 0},
            {0, 0, 0, 2, 8, 0, 0, 3, 6},
            {0, 0, 9, 6, 0, 8, 1, 5, 0},
            {7, 0, 0, 0, 5, 0, 0, 4, 8},
            {0, 0, 5, 0, 0, 0, 0, 0, 3}
    };

    private final int[][] SUDOKU_1_9x9_EXPERT_EXPECTED = {
            {0, 0, 0, 5, 0, 0, 0, 0, 0},
            {0, 0, 0, 8, 4, 2, 7, 9, 5},
            {5, 9, 0, 7, 0, 0, 0, 0, 0},
            {4, 0, 6, 0, 0, 0, 8, 0, 2},
            {0, 0, 8, 0, 7, 6, 0, 0, 0},
            {0, 0, 0, 2, 8, 0, 0, 3, 6},
            {0, 0, 9, 6, 0, 8, 1, 5, 0},
            {7, 0, 0, 0, 5, 0, 0, 4, 8},
            {0, 0, 5, 0, 0, 0, 0, 0, 3}
    };

    // https://sudoku.com/fr/difficile/



    @Test
    public void test_constructor() {
        new SudokuSolver(SUDOKU_1_9x9_SIMPLE, 3, 3, 9, 9);
    }

    @Test
    public void test_constructor_inconsistent() {
        int[][] sudokuToResolve = {
                {0, 1, 7, 0, 4, 5, 0, 0, 0},
                {0, 9, 4, 0, 2, 0, 8}};

        assertThrows(IllegalArgumentException.class,
                () -> new SudokuSolver(sudokuToResolve, 3, 4, 2, 9));
    }

    @Test
    public void test_constructor_failedOnRegionSideRow() {
        assertThrows(IllegalArgumentException.class,
                () -> new SudokuSolver(SUDOKU_1_9x9_SIMPLE, 4, 3, 9, 9));
    }

    @Test
    public void test_constructor_failedOnRegionSideCol() {
        assertThrows(IllegalArgumentException.class,
                () -> new SudokuSolver(SUDOKU_1_9x9_SIMPLE, 3, 4, 9, 9));
    }

    @Test
    public void test_getSudoku() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_1_9x9_SIMPLE, 3, 3, 9, 9);

        int[][] sudokuOutput = solver.getSudoku();

        assertEquals(SUDOKU_1_9x9_SIMPLE, sudokuOutput);
    }

    @Test
    public void test_convertArrayToSudokuSquare(){
        int nbRows = SUDOKU_1_9x9_SIMPLE.length;
        int nbColumns = SUDOKU_1_9x9_SIMPLE[0].length;
        SudokuSolver solver = new SudokuSolver(SUDOKU_1_9x9_SIMPLE, 3, 3, 9, 9);
        SudokuSquare[][] sudokuSquares = solver.convertArrayToSudokuSquare();

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
    public void test_run_9x9_simple() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_1_9x9_SIMPLE, 3, 3, 9, 9);
        solver.convertArrayToSudokuSquare();

        solver.letDoAFirstRun();

        int[][] output = solver.getWinnerValues();
        assertEquals(SUDOKU_1_9x9_SIMPLE.length, output.length);
        assertEquals(SUDOKU_1_9x9_SIMPLE[0].length, output[0].length);
        assertArrayEquals(SUDOKU_1_9x9_SIMPLE_EXPECTED, output);
    }

    @Test
    public void test_run_9x9_medium() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_1_9x9_MEDIUM, 3, 3, 9, 9);
        solver.convertArrayToSudokuSquare();

        solver.letDoAFirstRun();

        int[][] output = solver.getWinnerValues();
        assertEquals(SUDOKU_1_9x9_MEDIUM.length, output.length);
        assertEquals(SUDOKU_1_9x9_MEDIUM[0].length, output[0].length);


        assertArrayEquals(SUDOKU_1_9x9_MEDIUM_EXPECTED, output);
    }

    @Test
    public void test_run_9x9_difficile() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_1_9x9_DIFFICILE, 3, 3, 9, 9);
        solver.convertArrayToSudokuSquare();

        solver.letDoAFirstRun();

        int[][] output = solver.getWinnerValues();
        assertEquals(SUDOKU_1_9x9_DIFFICILE.length, output.length);
        assertEquals(SUDOKU_1_9x9_DIFFICILE[0].length, output[0].length);


        assertArrayEquals(SUDOKU_1_9x9_DIFFICILE_EXPECTED, output);
    }


    @Test
    public void test_run_9x9_expert() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_1_9x9_EXPERT, 3, 3, 9, 9);
        solver.convertArrayToSudokuSquare();

        solver.letDoAFirstRun();

        System.out.println("\nEXPERT\n");
        solver.printlnRemainingPossibleValues();

        int[][] output = solver.getWinnerValues();
        assertEquals(SUDOKU_1_9x9_EXPERT.length, output.length);
        assertEquals(SUDOKU_1_9x9_EXPERT[0].length, output[0].length);


        assertArrayEquals(SUDOKU_1_9x9_EXPERT_EXPECTED, output);
    }


    @Test
    public void test_run_2x2() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_1_2x2, 2, 2, 4, 4);
        solver.convertArrayToSudokuSquare();

        solver.letDoAFirstRun();

        int[][] output = solver.getWinnerValues();
        assertEquals(SUDOKU_1_2x2.length, output.length);
        assertEquals(SUDOKU_1_2x2[0].length, output[0].length);
        assertArrayEquals(SUDOKU_1_2x2_EXPECTED, output);
    }
}
