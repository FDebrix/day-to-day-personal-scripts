package main.java;

import main.java.algorithms.OneValueInOneSquareOfTheRegion;
import main.java.algorithms.TwoValuesPossibleInTwoSquaresInTheSameRegion;
import main.java.algorithms.ValueInHorizontalOrVerticalOfASubGrid;

import java.util.*;

import static main.java.SudokuHelper.SOP;


public class SudokuSolver {

    private SudokuSquare[][] squares;
    private List<SudokuRegion> regions;


    public SudokuSolver(int[][] sudoKuToResolve) {

        SudokuBuilder builder = new SudokuBuilder();
        // TODO add a light unit test to cover the fact we are validating the input
        SudokuBuilder.SudokuBuilderOutput sudokuBuilderOutput = builder.buildSudoku(sudoKuToResolve);
        squares = sudokuBuilderOutput.allTheSquares;
        regions = sudokuBuilderOutput.allTheRegions;

        if (SOP && allWinnerFound()) {
            System.out.println(this.getClass().getName() + ": The sudoku was resolved without running algorithms");
        }
    }


    public int[][] resolveTheSudoku() {
        OneValueInOneSquareOfTheRegion algo1 = new OneValueInOneSquareOfTheRegion();
        ValueInHorizontalOrVerticalOfASubGrid algo2 = new ValueInHorizontalOrVerticalOfASubGrid();
        TwoValuesPossibleInTwoSquaresInTheSameRegion algo3 = new TwoValuesPossibleInTwoSquaresInTheSameRegion();

        for (int k = 0; k < 20; k++) {

            algo1.runAlgorithm(regions);
            if (allWinnerFound()) break;

            algo2.runAlgorithm(regions);
            if (allWinnerFound()) break;

            algo3.runAlgorithm(regions);
            if (allWinnerFound()) break;
        }

        return SudokuHelper.getInstance().getWinnerValues(this.squares);
    }

    private boolean allWinnerFound() {
        return SudokuHelper.getInstance().allWinnerFound(this.squares);
    }

    public void printPossibleValues() {
        SudokuHelper.getInstance().printlnRemainingPossibleValues(this.squares);
    }
}
