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
            SudokuSquare squareI = squares.get(i);
            SudokuSquare squareJSameAsI = squareI;

            if(squareI.getPossibleValues().length == 2) {
                int nbSquaresWithThoseTwoPossibleValues = 1;

                for(int j = i + 1; j < squares.size(); j++) {
                    SudokuSquare squareIdJ = squares.get(j);

                    if(squareIdJ.getPossibleValues().length == 2
                        && Arrays.equals(squareI.getPossibleValues(), squareIdJ.getPossibleValues())) {

                        squareJSameAsI = squareIdJ;
                        nbSquaresWithThoseTwoPossibleValues++;
                    }
                }

                if(nbSquaresWithThoseTwoPossibleValues == 2) {
                    setLoserValueForAllSquaresExceptSquares(region, squareI.getPossibleValues(), squareI, squareJSameAsI);
                }
            }
        }

        return findAWinner;
    }
}
