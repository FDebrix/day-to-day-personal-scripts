package main.java;

import main.java.SudokuRegion.SudokuRegionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Convert the input Sudoku in the format int[][] into SudokuSquare[][].
 * Also build the regions and assign them to the squares.
 * Use SudokuBuilderOutput to return them.
 */
public class SudokuBuilder {

    private int[][] sudokuToConvert;

    private int sudokuRowSize;
    private int sudokuColSize;
    private int regionRowSize;
    private int regionColSize;
    private int nbPossibleValues ;
    private SudokuSquare[][] allTheSquares;
    private List<SudokuRegion> allTheRegions;


    public static class SudokuBuilderOutput {
        public SudokuSquare[][] allTheSquares;
        public List<SudokuRegion> allTheRegions;
    }


    public SudokuBuilder() { }


    public SudokuBuilderOutput buildSudoku(int[][] sudokuToConvert) {
        validateInputSudoku(sudokuToConvert);
        this.sudokuToConvert = sudokuToConvert;

        sudokuRowSize = sudokuToConvert.length;
        sudokuColSize = sudokuToConvert[0].length;
        validateSudokuSizes4Or9();

        regionRowSize = sudokuRowSize == 9 ? 3 : 2;
        regionColSize = sudokuColSize == 9 ? 3 : 2;

        nbPossibleValues = sudokuRowSize;

        initAllTheSquaresAndAllTheRegions();

        SudokuBuilderOutput output = new SudokuBuilderOutput();
        output.allTheSquares = allTheSquares;
        output.allTheRegions = allTheRegions;

        return output;
    }

    private void validateInputSudoku(int[][] sudoku) {
        if(sudoku.length != sudoku[0].length) {
            throw new IllegalArgumentException("The input sudoku is not a square.");
        }
    }

    private void validateSudokuSizes4Or9() {
        if(sudokuRowSize != 9 && sudokuRowSize != 4)
            throw new IllegalArgumentException("Only sudoku of size 4 or 9 are allowed. You provided a sudoku of row size " + sudokuRowSize);
    }

    private void initAllTheSquaresAndAllTheRegions() {
        allTheSquares = buildSudokuSquares();

        allTheRegions = new ArrayList<>();
        allTheRegions.addAll(createHorizontalRegionsAndAddToSquares());
        allTheRegions.addAll(createVerticalRegionsAndAddToSquares());
        allTheRegions.addAll(createSubgridRegionsAndAddToSquare());

        for(int i = 0 ; i < sudokuRowSize ; i++) {
            for(int j = 0 ; j < sudokuColSize ; j++) {
                allTheSquares[i][j].setInitialValue(sudokuToConvert[i][j]);
            }
        }
    }

    private List<SudokuRegion> createSubgridRegionsAndAddToSquare() {
        List<SudokuRegion> allSubgridRegions = new ArrayList<>();
        int nbSubgridRow = sudokuRowSize / regionRowSize;
        int nbSubgridCol = sudokuColSize / regionColSize;

        for(int subgridRowId = 0 ; subgridRowId < nbSubgridRow ; subgridRowId ++) {
            for(int subgridColId = 0 ; subgridColId < nbSubgridCol ; subgridColId ++) {
                List<SudokuSquare> squaresOfTheSubgrid = getSquaresInSubgrid(subgridRowId, subgridColId);
                SudokuRegion subgridRegion = new SudokuRegion(squaresOfTheSubgrid, SudokuRegionType.SUB_GRID);
                allSubgridRegions.add(subgridRegion);
            }
        }

        return allSubgridRegions;
    }

    private List<SudokuSquare> getSquaresInSubgrid(int subgridRowId, int subgridColId) {
        List<SudokuSquare> squaresOfTheSubgrid = new ArrayList<>();

        for(int i = 0; i < regionRowSize ; i++) {
            for (int j = 0 ; j < regionColSize ; j++) {
                int rowId = subgridRowId * regionRowSize + i;
                int colId = subgridColId * regionColSize + j;
                squaresOfTheSubgrid.add(allTheSquares[rowId][colId]);
            }
        }
        return squaresOfTheSubgrid;
    }

    private List<SudokuRegion> createVerticalRegionsAndAddToSquares() {
        List<SudokuRegion> allVerticalRegions = new ArrayList<>();

        for(int i = 0 ; i < sudokuColSize ; i++) {
            List<SudokuSquare> squaresOnTheCol = getSquaresInTheColId(i);
            SudokuRegion verticalRegion = new SudokuRegion(squaresOnTheCol, SudokuRegionType.VERTICAL);
            allVerticalRegions.add(verticalRegion);
        }

        return allVerticalRegions;
    }

    private List<SudokuSquare> getSquaresInTheColId(int i) {
        List<SudokuSquare> squaresOnTheCol = new ArrayList<>();

        for (int j = 0; j < sudokuRowSize; j++) {
            squaresOnTheCol.add(allTheSquares[j][i]);
        }
        return squaresOnTheCol;
    }

    private List<SudokuRegion> createHorizontalRegionsAndAddToSquares() {
        List<SudokuRegion> allHorizontalRegions = new ArrayList<>();

        for(int i = 0 ; i < sudokuRowSize ; i++) {
            List<SudokuSquare> squaresOnTheRow = getSquaresInTheRowId(i);
            SudokuRegion horizontalRegion = new SudokuRegion(squaresOnTheRow, SudokuRegionType.HORIZONTAL);
            allHorizontalRegions.add(horizontalRegion);
        }

        return allHorizontalRegions;
    }

    private List<SudokuSquare> getSquaresInTheRowId(int i) {
        List<SudokuSquare> squaresOnTheRow = new ArrayList<>();

        for (int j = 0; j < sudokuColSize; j++) {
            squaresOnTheRow.add(allTheSquares[i][j]);
        }
        return squaresOnTheRow;
    }

    private SudokuSquare[][] buildSudokuSquares() {
        SudokuSquare[][] sudokuSquares = new SudokuSquare[sudokuRowSize][sudokuColSize];

        for(int i = 0; i < sudokuRowSize; i++) {
            for(int j = 0; j < sudokuColSize; j++) {
                sudokuSquares[i][j] = new SudokuSquare(nbPossibleValues);
            }
        }
        return sudokuSquares;
    }
}
