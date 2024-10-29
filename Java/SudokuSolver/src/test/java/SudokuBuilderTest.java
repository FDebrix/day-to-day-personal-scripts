package test.java;

import main.java.SudokuBuilder;
import main.java.SudokuSolver;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static test.java.SudokuTestHelper.SUDOKU_1_9x9_SIMPLE;

public class SudokuBuilderTest {

    @Test
    public void test_constructor_private () throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<SudokuBuilder> constructor = SudokuBuilder.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void test_getInstance_NotNull() {
        SudokuBuilder sudokuBuilder = SudokuBuilder.getInstance();
        assertNotNull(sudokuBuilder);
    }

    @Test
    public void test_buildSudoku_inconsistent() {
        SudokuBuilder sudokuBuilder = SudokuBuilder.getInstance();

        int[][] sudokuToResolve = {
                {0, 1, 7, 0, 4, 5, 0, 0, 0},
                {0, 9, 4, 0, 2, 0, 8}};

        assertThrows(IllegalArgumentException.class,
                () -> sudokuBuilder.buildSudoku(sudokuToResolve, 3, 4, 2, 9));
    }

    @Test
    public void test_buildSudoku_failedOnRegionSideRow() {
        SudokuBuilder sudokuBuilder = SudokuBuilder.getInstance();

        assertThrows(IllegalArgumentException.class,
                () -> sudokuBuilder.buildSudoku(SUDOKU_1_9x9_SIMPLE, 4, 3, 9, 9));
    }

    @Test
    public void test_buildSudoku_failedOnRegionSideCol() {
        SudokuBuilder sudokuBuilder = SudokuBuilder.getInstance();

        assertThrows(IllegalArgumentException.class,
                () -> sudokuBuilder.buildSudoku(SUDOKU_1_9x9_SIMPLE, 3, 4, 9, 9));
    }

    @Test
    public void test_buildSudoku_returnIsNotNull() {
        assertNotNull(SudokuBuilder.getInstance().buildSudoku(SUDOKU_1_9x9_SIMPLE, 3, 3, 9, 9));
    }
}
