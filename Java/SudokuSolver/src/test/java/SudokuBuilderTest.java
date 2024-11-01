package test.java;

import main.java.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static test.java.SudokuTestHelper.*;

public class SudokuBuilderTest {

    @Test
    public void test_buildSudoku_returnIsNotNull() {
        assertNotNull(getSudokuBuilder().buildSudoku(SUDOKU_id2_9x9_SIMPLE_INPUT));
    }

    @Test
    public void test_buildSudoku_inputSudokuNotSquare() {
        assertThrows(IllegalArgumentException.class,
                () -> getSudokuBuilder().buildSudoku(SUDOKU_INVALID));
    }

    @Test
    public void test_buildSudoku_squareButSizeof5() {
        assertThrows(IllegalArgumentException.class,
                () -> getSudokuBuilder().buildSudoku(SUDOKU_INVALID_5_5));
    }

    // The "simple" Sudoku is so easy that there is no need to run an algorithm.
    // Just the fact to convert the input int[][] into SudokuSquare[][] is enough to resolve it.
    @Test
    public void test_buildSudoku() {

        int nbRows = SUDOKU_id2_9x9_SIMPLE_INPUT.length;
        int nbColumns = SUDOKU_id2_9x9_SIMPLE_INPUT[0].length;
        int nbRegionsIn9x9Sudoku = 27 ;
        int nbSquaresPerRegion = 9;

        SudokuBuilder.SudokuBuilderOutput sudokuBuilderOutput = getSudokuBuilder().buildSudoku(SUDOKU_id2_9x9_SIMPLE_INPUT);
        SudokuSquare[][] sudokuSquares = sudokuBuilderOutput.allTheSquares;
        List<SudokuRegion> regions = sudokuBuilderOutput.allTheRegions;

        assertEquals(nbRows, sudokuSquares.length);

        for (int i = 0; i < SUDOKU_id2_9x9_SIMPLE_INPUT.length ; i++) {
            assertEquals(nbColumns, sudokuSquares[i].length);
        }

        for(int i = 0 ; i < nbRows ; i++) {
            for (int j = 0; j < nbColumns; j++) {
                assertTrue(sudokuSquares[i][j].isWinnerValueFound());
                assertEquals(SUDOKU_id2_9x9_SIMPLE_EXPECTED[i][j], sudokuSquares[i][j].getWinnerValue());
            }
        }

        assertEquals(nbRegionsIn9x9Sudoku, regions.size());

        for(int i = 0 ; i < regions.size() ; i++)
            assertEquals(nbSquaresPerRegion, regions.get(i).getSudokuSquares().size());
    }

    @Test
    public void test_buildSudoku_createRegions() {
        int nbRows = SUDOKU_id1_2x2_INPUT.length;
        int nbColumns = SUDOKU_id1_2x2_INPUT[0].length;

        SudokuSquare[][] sudokuSquares =
                getSudokuBuilder().buildSudoku(SUDOKU_id1_2x2_INPUT).allTheSquares;


        for(int i = 0 ; i < nbRows ; i++) {
            for (int j = 0; j < nbColumns; j++) {
                BroadcastWinner winner = sudokuSquares[i][j].getBroadcastWinner();
                assertInstanceOf(SudokuRegions.class, winner);

                // TODO to continue
            }
        }
    }

    private SudokuBuilder getSudokuBuilder() {
        return new SudokuBuilder();
    }
}
