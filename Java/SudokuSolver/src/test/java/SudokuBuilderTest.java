package test.java;

import main.java.SudokuBuilder;
import main.java.SudokuSquare;
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
    public void test_buildSudoku_lengthFirstRowDifferentOfRowSize() {
        SudokuBuilder sudokuBuilder = SudokuBuilder.getInstance();

        int[][] sudokuToResolve = {
                {0, 1, 7, 0, 4, 5, 0, 0, 0},
                {0, 9, 4, 0, 2, 0, 8}};

        assertThrows(IllegalArgumentException.class,
                () -> sudokuBuilder.buildSudoku(sudokuToResolve, 3, 4, 1, 7));
    }

    @Test
    public void test_buildSudoku_rowsDonTHaveSameSize() {
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

    @Test
    public void test_buildSudoku(){
        int nbRows = SUDOKU_1_9x9_SIMPLE.length;
        int nbColumns = SUDOKU_1_9x9_SIMPLE[0].length;

        SudokuSquare[][] sudokuSquares = SudokuBuilder.getInstance().buildSudoku(SUDOKU_1_9x9_SIMPLE, 3, 3, 9, 9);

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
}
