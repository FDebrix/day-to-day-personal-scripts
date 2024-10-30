package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Convert the input Sudoku in the format int[][] into SudokuSquare[][].
 */
public class SudokuBuilder {

    private int[][] sudokuToConvert;
    private int sudokuRowSize;
    private int sudokuColSize;
    private int regionRowSize;
    private int regionColSize;


    public SudokuBuilder() { }

    public SudokuSquare[][]  buildSudoku(int[][] theSudokuToConvert) {
        sudokuToConvert = theSudokuToConvert;

        sudokuRowSize = sudokuToConvert.length;
        sudokuColSize = sudokuToConvert[0].length;
        validateSizes();

        regionRowSize = sudokuRowSize == 9 ? 3 : sudokuRowSize == 4 ? 2 : -1;
        regionColSize = sudokuColSize == 9 ? 3 : sudokuColSize == 4 ? 2 : -1;
        validateRegionSizes();

        validateConstructorParameters();

        return convertIntToSudokuSquare();
    }


    // TODO to implement
    private void validateRegionSizes() {
    }

    // TODO to implement
    private void validateSizes() {
        // regionRowSize must be equal to regionColSize
        // and let see the valid sizes
    }
/*
    public SudokuSquare[][]  buildSudoku(int[][] sudokuToConvert, int sudokuRowSize, int sudokuColSize,
                                         int regionRowSize, int regionColSize) {

        validateConstructorParameters(sudokuToConvert, regionRowSize, regionColSize, sudokuRowSize, sudokuColSize);

        return convertIntToSudokuSquare(sudokuToConvert, sudokuRowSize, sudokuColSize, regionRowSize, regionColSize);
    }*/

    private SudokuSquare[][] convertIntToSudokuSquare() {
        int nbPossibleValues = sudokuRowSize;

        SudokuSquare[][] sudokuSquares = buildSudokuSquaresAndDefaultBroadcastWinner(sudokuRowSize, sudokuColSize, nbPossibleValues);

        createHorizontalRegionsAndAddToSquares(sudokuSquares, sudokuRowSize, sudokuColSize);
        createVerticalRegionsAndAddToSquares(sudokuSquares, sudokuRowSize, sudokuColSize);
        createSubgridRegionsAndAddToSquare(sudokuSquares, sudokuRowSize, sudokuColSize, regionRowSize, regionColSize);

        for(int i = 0 ; i < sudokuRowSize ; i++) {
            for(int j = 0 ; j < sudokuColSize ; j++) {
                sudokuSquares[i][j].setInitialValue(sudokuToConvert[i][j]);
            }
        }

        return sudokuSquares;
    }

    private void createSubgridRegionsAndAddToSquare(SudokuSquare[][] sudokuSquares,
                                                    int sudokuRowSize, int sudokuColSize, int regionRowSize, int regionColSize) {
        int nbSubgridRow = sudokuRowSize / regionRowSize;
        int nbSubgridCol = sudokuColSize / regionColSize;

        for(int subgridRowId = 0 ; subgridRowId < nbSubgridRow ; subgridRowId ++) {
            for(int subgridColId = 0 ; subgridColId < nbSubgridCol ; subgridColId ++) {

                List<SudokuSquare> squaresOfTheSubgrid = new ArrayList<>();
                SudokuRegion subgridRegion = new SudokuRegion(squaresOfTheSubgrid);

                for(int i = 0; i < regionRowSize ; i++) {
                    for (int j = 0 ; j < regionColSize ; j++) {
                        int rowId = subgridRowId * regionRowSize + i;
                        int colId = subgridColId * regionColSize + j;
                        squaresOfTheSubgrid.add(sudokuSquares[rowId][colId]);
                        ((SudokuRegions)sudokuSquares[rowId][colId].getBroadcastWinner()).
                                addBroadcastWinner(subgridRegion);
                    }
                }
            }
        }
    }

    private void createVerticalRegionsAndAddToSquares(SudokuSquare[][] sudokuSquares, int sudokuRowSize, int sudokuColSize) {
        for(int i = 0 ; i < sudokuColSize ; i++) {
            List<SudokuSquare> squaresOnTheCol = new ArrayList<>();
            SudokuRegion verticalRegion = new SudokuRegion(squaresOnTheCol);

            for (int j = 0; j < sudokuRowSize; j++) {
                squaresOnTheCol.add(sudokuSquares[j][i]);
                ((SudokuRegions)sudokuSquares[j][i].getBroadcastWinner()).
                        addBroadcastWinner(verticalRegion);
            }
        }
    }

    private void createHorizontalRegionsAndAddToSquares(SudokuSquare[][] sudokuSquares, int sudokuRowSize, int sudokuColSize) {
        for(int i = 0 ; i < sudokuRowSize ; i++) {
            List<SudokuSquare> squaresOnTheRow = new ArrayList<>();
            SudokuRegion horizontalRegion = new SudokuRegion(squaresOnTheRow);

            for (int j = 0; j < sudokuColSize; j++) {
                squaresOnTheRow.add(sudokuSquares[i][j]);
                ((SudokuRegions)sudokuSquares[i][j].getBroadcastWinner()).
                        addBroadcastWinner(horizontalRegion);
            }
        }
    }

    private SudokuSquare[][] buildSudokuSquaresAndDefaultBroadcastWinner(int sudokuRowSize, int sudokuColSize, int nbPossibleValues) {
        SudokuSquare[][] sudokuSquares = new SudokuSquare[sudokuRowSize][sudokuColSize];

        for(int i = 0; i < sudokuRowSize; i++) {
            for(int j = 0; j < sudokuColSize; j++) {
                sudokuSquares[i][j] = new SudokuSquare(nbPossibleValues, i, j);
                sudokuSquares[i][j].setBroadcastWinner(new SudokuRegions());
            }
        }
        return sudokuSquares;
    }

    // TODO
    // Let be more drastic and validate that the input is respecting "normal" size
    // https://en.wikipedia.org/wiki/Sudoku#Variations_of_grid_sizes_or_region_shapes
    private void validateConstructorParameters() {

        if(sudokuToConvert.length != sudokuRowSize)
            throw new IllegalArgumentException("The number of rows "+ sudokuRowSize
                    +" is not consistent with the size of the sudoku " + sudokuToConvert.length + ".");

        for (int i = 0; i < sudokuToConvert.length ; i++) {
            int[] aRow = sudokuToConvert[i];
            if (aRow.length != sudokuColSize)
                throw new IllegalArgumentException("The size " + aRow.length +
                        " of the row " + i + " is not consistent with expectation " +sudokuColSize+ ".");
        }

        if( (sudokuRowSize/regionRowSize)*regionRowSize != sudokuRowSize)
            throw new IllegalArgumentException("The region row size "+ regionRowSize +
                    " is not proportional with the sudoku row size " + sudokuRowSize + ".");

        if( (sudokuColSize/regionColSize)*regionColSize != sudokuColSize)
            throw new IllegalArgumentException("The region column size "+ regionColSize +
                    " is not proportional with the sudoku column size " + sudokuColSize + ".");
    }
}
