package test.java;

import main.java.SudokuRegion;
import main.java.SudokuRegions;
import main.java.SudokuSquare;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static main.java.SudokuSquare.NOT_FOUND_YET;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuRegionsTest {


    @Test
    public void test_broadcastWinner_broadcastWinnersEmpty() {
        SudokuRegions regions = new SudokuRegions();
        regions.broadcastWinner(null);
    }


    @Test
    public void test_addBroadcastWinner_nullThrowException() {
        SudokuRegions regions = new SudokuRegions();
        assertThrows(IllegalArgumentException.class,
                () -> regions.addBroadcastWinner(null));
    }

    @Test
    public void test_broadcastWinner_broadcastWinnersNotEmpty() {
        int winnerValueOfSquare1 = 3;
        int[] possibleValues = {1, 2, 3, 4};
        int[] possibleValuesOfSquare2AfterBroadcast = {1, 2, 4};

        SudokuSquare square1 = new SudokuSquare(4, 1, 1);
        SudokuSquare square2 = new SudokuSquare(4, 1, 2);
        List<SudokuSquare> squares = Arrays.asList(square1, square2);

        SudokuRegion region = new SudokuRegion(squares);

        SudokuRegions regions = new SudokuRegions();
        regions.addBroadcastWinner(region);

        square1.setBroadcastWinner(regions);
        square2.setBroadcastWinner(regions);

        assertFalse(square1.isWinnerValueFound());
        assertEquals(NOT_FOUND_YET, square1.getWinnerValue());
        assertArrayEquals(possibleValues, square1.getPossibleValues());
        assertFalse(square2.isWinnerValueFound());
        assertEquals(NOT_FOUND_YET, square2.getWinnerValue());
        assertArrayEquals(possibleValues, square2.getPossibleValues());

        square1.setWinnerValue(winnerValueOfSquare1);

        assertTrue(square1.isWinnerValueFound());
        assertEquals(winnerValueOfSquare1, square1.getWinnerValue());
        assertFalse(square2.isWinnerValueFound());
        assertEquals(NOT_FOUND_YET, square2.getWinnerValue());
        assertArrayEquals(possibleValuesOfSquare2AfterBroadcast, square2.getPossibleValues());
    }
}
