package test.java;

import main.java.SudokuHelper;
import main.java.SudokuSolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.java.SudokuSolverTestData.*;

public class SudokuSolverTest {

    @Test
    public void test_constructor() {
        new SudokuSolver(SUDOKU_id2_9x9_SIMPLE_INPUT);
    }

    @Test
    public void test_run_2x2() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_id1_2x2_INPUT);

        int[][] output = solver.resolveTheSudoku();

        assertEquals(SUDOKU_id1_2x2_INPUT.length, output.length);
        assertEquals(SUDOKU_id1_2x2_INPUT[0].length, output[0].length);
        assertArrayEquals(SUDOKU_id1_2x2_EXPECTED, output);
    }

    @Test
    public void test_run_9x9_simple() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_id2_9x9_SIMPLE_INPUT);

        int[][] output = solver.resolveTheSudoku();

        assertEquals(SUDOKU_id2_9x9_SIMPLE_INPUT.length, output.length);
        assertEquals(SUDOKU_id2_9x9_SIMPLE_INPUT[0].length, output[0].length);
        assertArrayEquals(SUDOKU_id2_9x9_SIMPLE_EXPECTED, output);
    }

    @Test
    public void test_run_9x9_medium() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_id3_9x9_MEDIUM_INPUT);

        int[][] output = solver.resolveTheSudoku();

        assertEquals(SUDOKU_id3_9x9_MEDIUM_INPUT.length, output.length);
        assertEquals(SUDOKU_id3_9x9_MEDIUM_INPUT[0].length, output[0].length);

        assertArrayEquals(SUDOKU_id3_9x9_MEDIUM_EXPECTED, output);
    }

    @Test
    public void test_run_9x9_difficile() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_id4_9x9_DIFFICILE_INPUT);

        int[][] output = solver.resolveTheSudoku();

        assertEquals(SUDOKU_id4_9x9_DIFFICILE_INPUT.length, output.length);
        assertEquals(SUDOKU_id4_9x9_DIFFICILE_INPUT[0].length, output[0].length);

        assertArrayEquals(SUDOKU_id4_9x9_DIFFICILE_EXPECTED, output);
    }

    @Test
    public void test_run_9x9_expert() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_id5_9x9_EXPERT_INPUT);

        int[][] output = solver.resolveTheSudoku();

        assertEquals(SUDOKU_id5_9x9_EXPERT_INPUT.length, output.length);
        assertEquals(SUDOKU_id5_9x9_EXPERT_INPUT[0].length, output[0].length);

        assertArrayEquals(SUDOKU_id5_9x9_EXPERT_EXPECTED, output);
    }

    @Test
    public void test_run_9x9_master() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_id6_9x9_MASTER_INPUT);

        int[][] output = solver.resolveTheSudoku();

        assertEquals(SUDOKU_id6_9x9_MASTER_INPUT.length, output.length);
        assertEquals(SUDOKU_id6_9x9_MASTER_INPUT[0].length, output[0].length);

        assertArrayEquals(SUDOKU_id6_9x9_MASTER_EXPECTED, output);
    }

    @Test
    public void test_run_9x9_extreme() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_id7_9x9_EXTREME_INPUT);

        int[][] output = solver.resolveTheSudoku();

        solver.printPossibleValues();

        assertEquals(SUDOKU_id7_9x9_EXTREME_INPUT.length, output.length);
        assertEquals(SUDOKU_id7_9x9_EXTREME_INPUT[0].length, output[0].length);
        assertArrayEquals(SUDOKU_id7_9x9_EXTREME_EXPECTED, output);
    }

    @Test
    public void test_run_9x9_impossible() {
        SudokuSolver solver = new SudokuSolver(SUDOKU_id7_9x9_IMPOSSIBLE_INPUT);

        int[][] output = solver.resolveTheSudoku();

        solver.printPossibleValues();
        SudokuHelper.getInstance().printlnRemainingPossibleValues(output);

        assertEquals(SUDOKU_id7_9x9_IMPOSSIBLE_INPUT.length, output.length);
        assertEquals(SUDOKU_id7_9x9_IMPOSSIBLE_INPUT[0].length, output[0].length);
        assertArrayEquals(SUDOKU_id7_9x9_IMPOSSIBLE_EXPECTED_TEMP, output);
    }
}
