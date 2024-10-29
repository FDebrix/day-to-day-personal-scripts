package main.java;

/**
 * Read the input Sudoku in the format int[][] and return SudokuSquare[][].
 */
public class SudokuBuilder {

    private static SudokuBuilder sudokuBuilder;

    private SudokuBuilder() { }

    public static SudokuBuilder getInstance() {
        if(sudokuBuilder == null)
            sudokuBuilder = new SudokuBuilder();
        return sudokuBuilder;
    }

    public SudokuSquare[][]  buildSudoku(int[][] sudoKuToResolve, int regionRowSize, int regionColSize,
                            int sudokuRowSize, int sudokuColSize) {
        validateConstructorParameters(
                sudoKuToResolve, regionRowSize, regionColSize, sudokuRowSize, sudokuColSize);

        SudokuSquare[][] sudokuSquares = new SudokuSquare[1][1];
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
