package test.java;

import main.java.SudokuRegion;
import main.java.ValueInHorizontalOrVerticalOfASubGrid;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ValueInHorizontalOrVerticalOfASubGridTest {


    @Test
    public void test_constructor_regionIsNull_returnFalse () {
        ValueInHorizontalOrVerticalOfASubGrid algo = new ValueInHorizontalOrVerticalOfASubGrid();
        assertFalse(algo.runAlgorithm((SudokuRegion)null));
    }

    @Test
    public void test_constructor_regionsAreNull_returnFalse () {
        ValueInHorizontalOrVerticalOfASubGrid algo = new ValueInHorizontalOrVerticalOfASubGrid();
        assertFalse(algo.runAlgorithm((List)null));
    }
}
