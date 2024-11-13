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
        SudokuBuilder.SudokuBuilderOutput sudokuBuilderOutput = builder.buildSudoku(sudoKuToResolve);
        squares = sudokuBuilderOutput.allTheSquares;
        regions = sudokuBuilderOutput.allTheRegions;

        if (SOP && allWinnerFound()) {
            System.out.println(this.getClass().getName() + ": The sudoku was resolved without running algorithms");
        }
    }


    public int[][] resolveTheSudoku() {

        // TODO - to refactor to make more "dynamic"
        OneValueInOneSquareOfTheRegion algo1 = new OneValueInOneSquareOfTheRegion();
        boolean algo1FoundWinner = false;
        ValueInHorizontalOrVerticalOfASubGrid algo2 = new ValueInHorizontalOrVerticalOfASubGrid();
        boolean algo2FoundWinner = false;
        TwoValuesPossibleInTwoSquaresInTheSameRegion algo3 = new TwoValuesPossibleInTwoSquaresInTheSameRegion();
        boolean algo3FoundWinner = false;

        for (int i = 0; i < 20; i++) {

            algo1FoundWinner = algo1.runAlgorithm(regions);
            if (allWinnerFound()) {
                if (SOP) System.out.println("The sudoku was resolved after the iteration " + i+1
                        + " after running the algorithm " + OneValueInOneSquareOfTheRegion.class.getName());
                break;
            }

            algo2FoundWinner = algo2.runAlgorithm(regions);
            if (allWinnerFound()) {
                if (SOP) System.out.println("The sudoku was resolved after the iteration " + i+1
                        + " after running the algorithm " + ValueInHorizontalOrVerticalOfASubGrid.class.getName());
                break;
            }

            algo3FoundWinner = algo3.runAlgorithm(regions);
            if (allWinnerFound()) {
                if (SOP) System.out.println("The sudoku was resolved after the iteration " + i+1
                        + " after running the algorithm " + TwoValuesPossibleInTwoSquaresInTheSameRegion.class.getName());
                break;
            }

            if( !algo1FoundWinner && !algo2FoundWinner && !algo3FoundWinner) {
                System.out.println("During the iteration " + i+1 + ", all algorithms were run but no winner were found. The resolver is blocked. Please implement a new algorithm.");
                break;
            }
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
