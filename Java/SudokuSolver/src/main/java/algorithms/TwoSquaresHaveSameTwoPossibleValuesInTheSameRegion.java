package main.java.algorithms;

import main.java.SudokuRegion;
import main.java.SudokuSquare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.java.SudokuHelper.SOP;

public class TwoSquaresHaveSameTwoPossibleValuesInTheSameRegion extends SudokuAbstractAlgorithm {

    @Override
    protected boolean internalRunAlgorithm(SudokuRegion region) {
        boolean findAWinner = false;

        List<SudokuSquare> squares = region.getSudokuSquares();

        for(int i = 0; i < squares.size(); i++) {
            SudokuSquare squareI = squares.get(i);
            List<SudokuSquare> squaresWithThe2ValuesOfSquareI = new ArrayList<>();

            if(squareI.getPossibleValues().length == 2) {
                squaresWithThe2ValuesOfSquareI.add(squareI);

                for(int j = 0; j < squares.size() ; j++) {
                    if (j == i) continue;

                    int[] possibleValuesOfJ = squares.get(j).getPossibleValues();

                    if(possibleValuesOfJ.length > 2
                            && contains(possibleValuesOfJ, squareI.getPossibleValues()[0])
                            && contains(possibleValuesOfJ, squareI.getPossibleValues()[1])) {
                        squaresWithThe2ValuesOfSquareI.add(squares.get(j));
                    }
                    else if (contains(possibleValuesOfJ, squareI.getPossibleValues()[0])
                            || contains(possibleValuesOfJ, squareI.getPossibleValues()[1])) {
                        squaresWithThe2ValuesOfSquareI = new ArrayList<>();
                        break;
                    }
                }
            }

            if(squaresWithThe2ValuesOfSquareI.size() == 2) {
                if(SOP)
                    System.out.println("A square has 2 possible values" + Arrays.toString(squaresWithThe2ValuesOfSquareI.get(0).getPossibleValues())
                            + " while a second also has at less those 2 possibles values" + Arrays.toString(squaresWithThe2ValuesOfSquareI.get(1).getPossibleValues()));
                findAWinner = true;
                setAllValuesLoserExceptSomeValues(squaresWithThe2ValuesOfSquareI.get(1), squareI.getPossibleValues());
                setLoserValueForAllSquaresExceptSquares(region, squareI.getPossibleValues(), squareI, squaresWithThe2ValuesOfSquareI.get(1));
            }
        }

        return findAWinner;
    }

    private static boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }
}
