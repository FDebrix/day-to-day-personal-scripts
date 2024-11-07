package test.java;

import main.java.SudokuRegion;
import main.java.SudokuRegions;
import main.java.SudokuSquare;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static main.java.SudokuRegion.SudokuRegionType.*;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuRegionTest {

    @Test
    public void test_constructor_doesNotAcceptNullList () {
        assertThrows(IllegalArgumentException.class,
                () -> new SudokuRegion(null, HORIZONTAL));
    }

    @Test
    public void test_constructor_doesNotAcceptNullRegionType () {
        SudokuSquare square = new SudokuSquare(4);
        List<SudokuSquare> squares = List.of(square);

        assertThrows(IllegalArgumentException.class,
                () -> new SudokuRegion(squares, null));
    }

    @Test
    public void test_constructor_persistList() {
        SudokuSquare square = new SudokuSquare(4);
        List<SudokuSquare> squares = List.of(square);
        List<SudokuSquare> output;

        SudokuRegion region = new SudokuRegion(squares, HORIZONTAL);
        output = region.getSudokuSquares() ;

        assertEquals(squares, output);
        assertEquals(HORIZONTAL, region.getRegionType());
    }

    @Test
    public void test_broadcastWinner_cannotBeNull () {
        SudokuSquare square1 = new SudokuSquare(4);
        List<SudokuSquare> squares = List.of(square1);
        SudokuRegion region = new SudokuRegion(squares, VERTICAL);

        assertEquals(VERTICAL, region.getRegionType());
        assertThrows(IllegalArgumentException.class,
                () -> region.broadcastWinner(null));
    }

    @Test
    public void test_broadcastWinner_mustBeAWinnerValue () {
        SudokuSquare square1 = new SudokuSquare(4);
        List<SudokuSquare> squares = List.of(square1);
        SudokuRegion region = new SudokuRegion(squares, HORIZONTAL);

        assertThrows(IllegalArgumentException.class,
                () -> region.broadcastWinner(square1));
    }

    @Test
    public void test_broadcastWinner () {
        SudokuSquare square1 = new SudokuSquare(4);
        int winnerValueOfTheSquare1 = 3;

        SudokuSquare square2 = new SudokuSquare(4);
        int[] remainingPossibleValuesForSquare2 = {1, 2, 4};

        List<SudokuSquare> squares = Arrays.asList(square1, square2);
        SudokuRegion region = new SudokuRegion(squares, HORIZONTAL);
        SudokuRegions regions = new SudokuRegions();
        regions.addRegion(region);

        square1.setWinnerValue(winnerValueOfTheSquare1);

        region.broadcastWinner(square1);

        assertTrue(square1.isWinnerValueFound());
        assertEquals(winnerValueOfTheSquare1, square1.getWinnerValue());
        assertEquals(0, square1.getPossibleValues().length);
        assertFalse(square2.isWinnerValueFound());
        assertArrayEquals(remainingPossibleValuesForSquare2, square2.getPossibleValues());
    }
}
