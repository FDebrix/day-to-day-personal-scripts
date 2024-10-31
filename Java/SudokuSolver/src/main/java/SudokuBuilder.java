package main.java;

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

    public SudokuBuilderOutput  buildSudoku(int[][] theSudokuToConvert) {
        validateInputSudoku(theSudokuToConvert);
        sudokuToConvert = theSudokuToConvert;

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

    private void validateInputSudoku(int[][] theSudokuToConvert) {
        if(theSudokuToConvert.length != theSudokuToConvert[0].length) {
            throw new IllegalArgumentException("The input sudoku is not a square.");
        }
    }

    private void validateSudokuSizes4Or9() {
        if(sudokuRowSize != 9 && sudokuRowSize != 4)
            throw new IllegalArgumentException("Only sudoku of size 4 or 9 are allowed. You provided a sudoku of row size " + sudokuRowSize);
    }

    private void initAllTheSquaresAndAllTheRegions() {
        allTheSquares = buildSudokuSquaresAndSetDefaultBroadcastWinner();

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

                List<SudokuSquare> squaresOfTheSubgrid = new ArrayList<>();
                SudokuRegion subgridRegion = new SudokuRegion(squaresOfTheSubgrid);
                allSubgridRegions.add(subgridRegion);

                for(int i = 0; i < regionRowSize ; i++) {
                    for (int j = 0 ; j < regionColSize ; j++) {
                        int rowId = subgridRowId * regionRowSize + i;
                        int colId = subgridColId * regionColSize + j;
                        squaresOfTheSubgrid.add(allTheSquares[rowId][colId]);
                        ((SudokuRegions)allTheSquares[rowId][colId].getBroadcastWinner()).
                                addBroadcastWinner(subgridRegion);
                    }
                }
            }
        }

        return allSubgridRegions;
    }

    private List<SudokuRegion> createVerticalRegionsAndAddToSquares() {
        List<SudokuRegion> allVerticalRegions = new ArrayList<>();

        for(int i = 0 ; i < sudokuColSize ; i++) {
            List<SudokuSquare> squaresOnTheCol = new ArrayList<>();
            SudokuRegion verticalRegion = new SudokuRegion(squaresOnTheCol);
            allVerticalRegions.add(verticalRegion);

            for (int j = 0; j < sudokuRowSize; j++) {
                squaresOnTheCol.add(allTheSquares[j][i]);
                ((SudokuRegions)allTheSquares[j][i].getBroadcastWinner()).
                        addBroadcastWinner(verticalRegion);
            }
        }

        return allVerticalRegions;
    }

    private List<SudokuRegion> createHorizontalRegionsAndAddToSquares() {
        List<SudokuRegion> allHorizontalRegions = new ArrayList<>();

        for(int i = 0 ; i < sudokuRowSize ; i++) {
            List<SudokuSquare> squaresOnTheRow = new ArrayList<>();
            SudokuRegion horizontalRegion = new SudokuRegion(squaresOnTheRow);
            allHorizontalRegions.add(horizontalRegion);

            for (int j = 0; j < sudokuColSize; j++) {
                squaresOnTheRow.add(allTheSquares[i][j]);
                ((SudokuRegions)allTheSquares[i][j].getBroadcastWinner()).
                        addBroadcastWinner(horizontalRegion);
            }
        }

        return allHorizontalRegions;
    }

    private SudokuSquare[][] buildSudokuSquaresAndSetDefaultBroadcastWinner() {
        SudokuSquare[][] sudokuSquares = new SudokuSquare[sudokuRowSize][sudokuColSize];

        for(int i = 0; i < sudokuRowSize; i++) {
            for(int j = 0; j < sudokuColSize; j++) {
                sudokuSquares[i][j] = new SudokuSquare(nbPossibleValues);
                sudokuSquares[i][j].setBroadcastWinner(new SudokuRegions());
            }
        }
        return sudokuSquares;
    }
}
