package test.java;

import main.java.BroadcastWinner;
import main.java.SudokuRegion;
import main.java.SudokuSolver;
import main.java.SudokuSquare;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuRegionTest {


    @Test
    public void test_constructor_private () throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<SudokuRegion> constructor = SudokuRegion.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void test_constructor_doesNotAcceptNullList () {
        assertThrows(IllegalArgumentException.class,
                () -> new SudokuRegion(null));
    }

    // TODO clean up
    /*
    @Test
    public void test_constructor_doesNotAcceptEmptyList () {
        assertThrows(IllegalArgumentException.class,
                () -> new SudokuRegion(new ArrayList<>()));
    }

     */

    @Test
    public void test_constructor_persistList() {
        SudokuSquare square = new SudokuSquare(4, 1, 1);
        List<SudokuSquare> squares = Arrays.asList(square);
        List<SudokuSquare> output;

        SudokuRegion region = new SudokuRegion(squares);
        output = region.getSudokuSquares() ;

        assertEquals(squares, output);
    }

    @Test
    public void test_broadcastWinner_cannotBeNull () {
        SudokuSquare square1 = new SudokuSquare(4, 1, 1);
        List<SudokuSquare> squares = Arrays.asList(square1);
        SudokuRegion region = new SudokuRegion(squares);

        assertThrows(IllegalArgumentException.class,
                () -> region.broadcastWinner(null));
    }

    @Test
    public void test_broadcastWinner_mustBeAWinnerValue () {
        SudokuSquare square1 = new SudokuSquare(4, 1, 1);
        List<SudokuSquare> squares = Arrays.asList(square1);
        SudokuRegion region = new SudokuRegion(squares);

        assertThrows(IllegalArgumentException.class,
                () -> region.broadcastWinner(square1));
    }

    @Test
    public void test_broadcastWinner () {
        SudokuSquare square1 = new SudokuSquare(4, 1, 1);
        int winnerValueOfTheSquare1 = 3;

        SudokuSquare square2 = new SudokuSquare(4, 1, 1);
        int[] remainingPossibleValuesForSquare2 = {1, 2, 4};

        List<SudokuSquare> squares = Arrays.asList(square1, square2);
        BroadcastWinner region = new SudokuRegion(squares);

        square1.setBroadcastWinner(region);
        square2.setBroadcastWinner(region);
        square1.setWinnerValue(winnerValueOfTheSquare1);

        region.broadcastWinner(square1);

        assertTrue(square1.isWinnerValueFound());
        assertEquals(winnerValueOfTheSquare1, square1.getWinnerValue());
        assertEquals(0, square1.getPossibleValues().length);
        assertFalse(square2.isWinnerValueFound());
        assertArrayEquals(remainingPossibleValuesForSquare2, square2.getPossibleValues());
    }
}
