package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Convert the input Sudoku in the format int[][] into SudokuSquare[][].
 */
public class SudokuBuilder {

    public SudokuBuilder() { }

    public SudokuSquare[][]  buildSudoku(int[][] sudokuToConvert) {
        int sudokuRowSize = sudokuToConvert.length;
        int sudokuColSize = sudokuToConvert[0].length;
        validateSizes(sudokuRowSize, sudokuColSize);

        int regionRowSize = sudokuRowSize == 9 ? 3 : sudokuRowSize == 4 ? 2 : -1;
        int regionColSize = sudokuColSize == 9 ? 3 : sudokuColSize == 4 ? 2 : -1;
        validateRegionSizes(sudokuRowSize, sudokuColSize);

        validateConstructorParameters(
                sudokuToConvert, regionRowSize, regionColSize, sudokuRowSize, sudokuColSize);

        return convertIntToSudokuSquare(sudokuToConvert, sudokuRowSize, sudokuColSize, regionRowSize, regionColSize);
    }


    // TODO to implement
    private void validateRegionSizes(int sudokuRowSize, int sudokuColSize) {
    }

    // TODO to implement
    private void validateSizes(int sudokuRowSize, int sudokuColSize) {
        // regionRowSize must be equal to regionColSize
        // and let see the valid sizes
    }

    public SudokuSquare[][]  buildSudoku(int[][] sudokuToConvert, int sudokuRowSize, int sudokuColSize,
                                         int regionRowSize, int regionColSize) {

        validateConstructorParameters(sudokuToConvert, regionRowSize, regionColSize, sudokuRowSize, sudokuColSize);

        return convertIntToSudokuSquare(sudokuToConvert, sudokuRowSize, sudokuColSize, regionRowSize, regionColSize);
    }

    private SudokuSquare[][] convertIntToSudokuSquare(int[][] sudokuToResolve, int sudokuRowSize, int sudokuColSize, int regionRowSize, int regionColSize) {
        int nbPossibleValues = sudokuRowSize;

        SudokuSquare[][] sudokuSquares = buildSudokuSquaresAndDefaultBroadcastWinner(sudokuRowSize, sudokuColSize, nbPossibleValues);

        createHorizontalRegionsAndAddToSquares(sudokuSquares, sudokuRowSize, sudokuColSize);
        createVerticalRegionsAndAddToSquares(sudokuSquares, sudokuRowSize, sudokuColSize);
        createSubgridRegionsAndAddToSquare(sudokuSquares, sudokuRowSize, sudokuColSize, regionRowSize, regionColSize);

        for(int i = 0 ; i < sudokuRowSize ; i++) {
            for(int j = 0 ; j < sudokuColSize ; j++) {
                sudokuSquares[i][j].setInitialValue(sudokuToResolve[i][j]);
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
    private void validateConstructorParameters(int[][] sudoKuToResolve,
                                               int regionRowSize, int regionColSize,
                                               int sudokuRowSize, int sudokuColSize) {

        if(sudoKuToResolve.length != sudokuRowSize)
            throw new IllegalArgumentException("The number of rows "+ sudokuRowSize
                    +" is not consistent with the size of the sudoku " + sudoKuToResolve.length + ".");

        for (int i = 0; i < sudoKuToResolve.length ; i++) {
            int[] aRow = sudoKuToResolve[i];
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
