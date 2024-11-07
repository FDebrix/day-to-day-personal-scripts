package main.java.algorithms;

import main.java.SudokuRegion;
import main.java.SudokuSquare;

import java.util.Arrays;
import java.util.List;

public class TwoValuesPossibleInTwoSquaresInTheSameRegion extends SudokuAbstractAlgorithm {

    @Override
    protected boolean internalRunAlgorithm(SudokuRegion region) {
        boolean findAWinner = false;

        List<SudokuSquare> squares = region.getSudokuSquares();

        for(int i = 0; i < squares.size(); i++) {
            SudokuSquare squareIdI = squares.get(i);
            SudokuSquare squareIdJSameAsI = squareIdI;

            if(squareIdI.getPossibleValues().length == 2) {
                int nbSquaresWithThoseTwoPossibleValues = 1;

                for(int j = i + 1; j < squares.size(); j++) {
                    SudokuSquare squareIdJ = squares.get(j);

                    if(squareIdJ.getPossibleValues().length == 2
                        && Arrays.equals(squareIdI.getPossibleValues(), squareIdJ.getPossibleValues())) {

                        squareIdJSameAsI = squareIdJ;
                        nbSquaresWithThoseTwoPossibleValues++;
                    }
                }

                if(nbSquaresWithThoseTwoPossibleValues == 2) {
                    setLoserValueExceptTwoSquares(region, squareIdI.getPossibleValues(), squareIdI, squareIdJSameAsI);
                }
            }
        }

        return findAWinner;
    }

    private void setLoserValueExceptTwoSquares(SudokuRegion region, int[] loserValues, SudokuSquare... squaresToNotSetLoser) {
        for(SudokuSquare square : region.getSudokuSquares()) {
            if(Arrays.stream(squaresToNotSetLoser).noneMatch(i -> i == square)) {
                for(int loserValue : loserValues) {
                    square.setLoserValue(loserValue);
                }
            }
        }
    }
}
