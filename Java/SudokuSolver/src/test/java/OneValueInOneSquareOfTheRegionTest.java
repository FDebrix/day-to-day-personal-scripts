package test.java;

import main.java.OneValueInOneSquareOfTheRegion;
import main.java.SudokuRegion;
import main.java.SudokuSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OneValueInOneSquareOfTheRegionTest {
    @Mock
    private SudokuSquare square1;

    @Mock
    private SudokuSquare square2;

    @Mock
    private SudokuSquare square3;

    @Mock
    private SudokuSquare square4;

    @BeforeEach
    public void beforeAll() {
        square1 = mock(SudokuSquare.class);
        square2 = mock(SudokuSquare.class);
        square3 = mock(SudokuSquare.class);
        square4 = mock(SudokuSquare.class);
    }

    @Test
    public void test_runAlgorithm_regionsNull_returnFalse() {
        OneValueInOneSquareOfTheRegion algo = new OneValueInOneSquareOfTheRegion();
        assertFalse(algo.runAlgorithm((List)null));
    }

    @Test
    public void test_runAlgorithm_regionNull_returnFalse() {
        OneValueInOneSquareOfTheRegion algo = new OneValueInOneSquareOfTheRegion();
        assertFalse(algo.runAlgorithm((SudokuRegion)null));
    }

    @Test
    public void test_runAlgorithm_regionsEmpty_returnFalse() {
        OneValueInOneSquareOfTheRegion algo = new OneValueInOneSquareOfTheRegion();
        assertFalse(algo.runAlgorithm(new ArrayList<>()));
    }

    @Test
    public void test_runAlgorithm_4SquaresWith4PossibleValues_returnFalse() {
        int[] squarePossibleValues = {1, 2, 3, 4};
        when(square1.getWinnerValueOrPossibleValues()).thenReturn(squarePossibleValues);
        when(square2.getWinnerValueOrPossibleValues()).thenReturn(squarePossibleValues);
        when(square3.getWinnerValueOrPossibleValues()).thenReturn(squarePossibleValues);
        when(square4.getWinnerValueOrPossibleValues()).thenReturn(squarePossibleValues);

        List<SudokuSquare> squares = Arrays.asList(square1, square2, square3, square4);

        SudokuRegion region = new SudokuRegion(squares);
        List<SudokuRegion> regions = List.of(region);

        OneValueInOneSquareOfTheRegion algo = new OneValueInOneSquareOfTheRegion();
        assertFalse(algo.runAlgorithm(regions));
    }


    @Test
    public void test_runAlgorithm_4SquaresWith2WinnerValues_returnFalse() {
        int[] square1PossibleValues = {2, 3, 4};
        int[] square2PossibleValues = {2, 3};
        int[] square3PossibleValues = {2, 3};
        int[] square4PossibleValues = {1, 2, 4}; // the value 1 only possible for the square 4
        when(square1.getWinnerValueOrPossibleValues()).thenReturn(square1PossibleValues);
        when(square2.getWinnerValueOrPossibleValues()).thenReturn(square2PossibleValues);
        when(square3.getWinnerValueOrPossibleValues()).thenReturn(square3PossibleValues);
        when(square4.getWinnerValueOrPossibleValues()).thenReturn(square4PossibleValues);

        List<SudokuSquare> squares = Arrays.asList(square1, square2, square3, square4);

        SudokuRegion region = new SudokuRegion(squares);
        List<SudokuRegion> regions = List.of(region);

        OneValueInOneSquareOfTheRegion algo = new OneValueInOneSquareOfTheRegion();
        assertTrue(algo.runAlgorithm(regions));
    }
}
