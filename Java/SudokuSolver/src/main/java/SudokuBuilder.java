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
    private int nbPossibleValues ;


    public SudokuBuilder() { }

    public SudokuSquare[][]  buildSudoku(int[][] theSudokuToConvert) {
        validateInputSudoku(theSudokuToConvert);
        sudokuToConvert = theSudokuToConvert;

        sudokuRowSize = sudokuToConvert.length;
        sudokuColSize = sudokuToConvert[0].length;
        validateSudokuSizes9Or4();

        regionRowSize = sudokuRowSize == 9 ? 3 : 2;
        regionColSize = sudokuColSize == 9 ? 3 : 2;

        nbPossibleValues = sudokuRowSize;

        return convertIntToSudokuSquare();
    }

    private void validateInputSudoku(int[][] theSudokuToConvert) {
        if(theSudokuToConvert.length != theSudokuToConvert[0].length) {
            throw new IllegalArgumentException("The input sudoku is not a square.");
        }
    }

    private void validateSudokuSizes9Or4() {
        if(sudokuRowSize != 9 && sudokuRowSize != 4)
            throw new IllegalArgumentException("Only sudoku of size 4 or 9 are allowed. You provided a sudoku of row size " + sudokuRowSize);
    }


    private SudokuSquare[][] convertIntToSudokuSquare() {

        SudokuSquare[][] sudokuSquares = buildSudokuSquaresAndSetDefaultBroadcastWinner();

        createHorizontalRegionsAndAddToSquares(sudokuSquares);
        createVerticalRegionsAndAddToSquares(sudokuSquares);
        createSubgridRegionsAndAddToSquare(sudokuSquares);

        for(int i = 0 ; i < sudokuRowSize ; i++) {
            for(int j = 0 ; j < sudokuColSize ; j++) {
                sudokuSquares[i][j].setInitialValue(sudokuToConvert[i][j]);
            }
        }

        return sudokuSquares;
    }

    private void createSubgridRegionsAndAddToSquare(SudokuSquare[][] sudokuSquares) {
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

    private void createVerticalRegionsAndAddToSquares(SudokuSquare[][] sudokuSquares) {
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

    private void createHorizontalRegionsAndAddToSquares(SudokuSquare[][] sudokuSquares) {
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

    private SudokuSquare[][] buildSudokuSquaresAndSetDefaultBroadcastWinner() {
        SudokuSquare[][] sudokuSquares = new SudokuSquare[sudokuRowSize][sudokuColSize];

        for(int i = 0; i < sudokuRowSize; i++) {
            for(int j = 0; j < sudokuColSize; j++) {
                sudokuSquares[i][j] = new SudokuSquare(nbPossibleValues, i, j);
                sudokuSquares[i][j].setBroadcastWinner(new SudokuRegions());
            }
        }
        return sudokuSquares;
    }

    // legacy code - to remove?
    /*
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
     */
}
