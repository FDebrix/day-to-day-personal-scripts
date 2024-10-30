package main.java;

import java.util.Arrays;
import java.util.List;

/**
 * Convert the input Sudoku in the format int[][] into SudokuSquare[][].
 */
public class SudokuBuilder {

    private static SudokuBuilder sudokuBuilder;

    private SudokuBuilder() { }

    /**
     * To get the singleton instance
     * @return
     */
    public static SudokuBuilder getInstance() {
        if(sudokuBuilder == null)
            sudokuBuilder = new SudokuBuilder();
        return sudokuBuilder;
    }

    /**
     *
     * @param sudokuToConvert
     * @param regionRowSize
     * @param regionColSize
     * @param sudokuRowSize
     * @param sudokuColSize
     * @return
     */
    public SudokuSquare[][]  buildSudoku(int[][] sudokuToConvert, int regionRowSize, int regionColSize,
                            int sudokuRowSize, int sudokuColSize) {
        validateConstructorParameters(
                sudokuToConvert, regionRowSize, regionColSize, sudokuRowSize, sudokuColSize);

        return convertIntToSudokuSquare(sudokuToConvert, regionRowSize, regionColSize, sudokuRowSize, sudokuColSize);
    }

    private SudokuSquare[][] convertIntToSudokuSquare(int[][] sudokuToResolve, int regionRowSize, int regionColSize,
                                                      int sudokuRowSize, int sudokuColSize) {
        int nbPossibleValues = sudokuRowSize;
        SudokuSquare[][] sudokuSquares = buildSudokuSquares(sudokuRowSize, sudokuColSize, nbPossibleValues);

        for(int i = 0 ; i < sudokuRowSize ; i++) {
            for(int j = 0 ; j < sudokuColSize ; j++) {

                // TODO START
                // NEED TO IMPLEMENT THE CREATION OF THE regions AND SET PROPERLY WITH
                // THEIRS RESPECTIVES SQUARES
                List<SudokuSquare> squares = Arrays.asList(sudokuSquares[i][j]);
                BroadcastWinner region = new SudokuRegion(squares);
                sudokuSquares[i][j].setBroadcastWinner(region);
                // TODO END

                sudokuSquares[i][j].setInitialValue(sudokuToResolve[i][j]);
            }
        }

        return sudokuSquares;
    }

    private SudokuSquare[][] buildSudokuSquares(int sudokuRowSize, int sudokuColSize, int nbPossibleValues) {
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
                    +" is not consistent with the size of the sudoku" + sudoKuToResolve.length + ".");

        for (int i = 0; i < sudoKuToResolve.length ; i++) {
            int[] aRow = sudoKuToResolve[i];
            if (aRow.length != sudokuColSize)
                throw new IllegalArgumentException("The size " + aRow.length +
                        " of the row " + i + " is not consistent with expectation" +sudokuColSize+ ".");
        }

        if( (sudokuRowSize/regionRowSize)*regionRowSize != sudokuRowSize)
            throw new IllegalArgumentException("The region row size "+ regionRowSize +
                    " is not proportional with the sudoku row size " + sudokuRowSize + ".");

        if( (sudokuColSize/regionColSize)*regionColSize != sudokuColSize)
            throw new IllegalArgumentException("The region column size "+ regionColSize +
                    " is not proportional with the sudoku column size " + sudokuColSize + ".");
    }
}
