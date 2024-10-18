package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuSolver {

    private int[][] sudokuToResolve;
    private int regionRowSize = 0;
    private int regionColSize = 0;
    private int sudokuRowSize = 0;
    private int sudokuColSize = 0;
    private SudokuSquare[][] sudokuSquares;


    public SudokuSolver (int[][] sudoKuToResolve, int regionRowSize, int regionColSize,
                         int sudokuRowSize, int sudokuColSize) {
        validateConstructorParamaters(
                sudoKuToResolve, regionRowSize, regionColSize, sudokuRowSize, sudokuColSize);

        this.regionRowSize = regionRowSize;
        this.regionColSize = regionColSize;
        this.sudokuRowSize = sudokuRowSize;
        this.sudokuColSize = sudokuColSize;
        this.sudokuToResolve = sudoKuToResolve;
    }

    public int[][] getSudoku() {
        return this.sudokuToResolve;
    }


    public SudokuSquare[][] convertArrayToSudokuSquare() {
        int nbPossibleValues = sudokuRowSize;
        this.sudokuSquares = new SudokuSquare[sudokuRowSize][sudokuColSize];

        for(int i = 0 ; i < sudokuRowSize ; i++) {
            for(int j = 0 ; j < sudokuColSize ; j++) {
                this.sudokuSquares[i][j] = new SudokuSquare(nbPossibleValues, sudokuToResolve[i][j], i, j);
            }
        }
        return this.sudokuSquares;
    }

    public void letDoAFirstRun() {

        for(int k = 0 ; k < 10 ; k++) {
            boolean foundAWinnerNotDigested = handleWinnerNotDigested();

            System.out.println("\n\nk:" + k);
            printlnRemainingPossibleValues();

            if(allWinnerFound()) break;

            // As long as #handleWinnerNotDigested is digesting, we don't run deeper algorithms
            if( !foundAWinnerNotDigested) {
                for(int i = 0 ; i < sudokuSquares.length ; i++) {
                    handleWinnerValueAvailableOnlyInOneSquareINITIAL(i);
                }
            }
        }
    }

    public int[][] getWinnerValues() {
        int[][] result = new int[sudokuRowSize][sudokuColSize];

        for(int i = 0; i < sudokuRowSize ; i++) {
            for (int j = 0; j < sudokuColSize ; j++) {
                result[i][j] = sudokuSquares[i][j].getWinnerValue();
            }
        }
        return result;
    }


    // TODO I AM HERE
    // goal is to replace #handleWinnerValueAvailableOnlyInOneSquareINITIAL
    private void handleWinnerValueAvailableOnlyInOneSquare() {
        for(int i = 0 ; i < sudokuRowSize ; i++) {
            List<SudokuSquare> oneLine = getSquaresOfTheRow(i);
            handleListOfSquares(oneLine);
        }
        for(int i = 0 ; i < sudokuColSize ; i++) {
            List<SudokuSquare> oneLine = getSquaresOfTheCol(i);
            handleListOfSquares(oneLine);
        }
    }

    private void handleListOfSquares(List<SudokuSquare> oneLine) {
    }

    private List<SudokuSquare> getSquaresOfTheCol(int colId) {
        List<SudokuSquare> list = new ArrayList<>();

        for(int i = 0 ; i < sudokuRowSize ; i++) {
            list.add(sudokuSquares[i][colId]);
        }

        return list;
    }

    private List<SudokuSquare> getSquaresOfTheRow(int rowId) {
        List<SudokuSquare> list = new ArrayList<>();

        for(int i = 0 ; i < sudokuColSize ; i++) {
            list.add(sudokuSquares[rowId][i]);
        }

        return list;
    }


    private void handleWinnerValueAvailableOnlyInOneSquareINITIAL(int columnId) {
        // 0 = default value
        // -1 = the value is a winner value
        // else = the number of time the value is still possible
        // There are 10 values for a 9 size sudoku
        int[] countNbOfEachValue={0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        // TODO
        // HARD CODED TO handle the column 2 ONLY FOR NOW
        int nbRows = sudokuToResolve.length;
        for (int i = 0 ; i < nbRows ; i++) {
            if (sudokuSquares[i][columnId].isWinnerValueFound()) {
                System.out.println("\t" + i + ", "+ columnId + "- winner value" + sudokuSquares[i][columnId].getWinnerValue());
                // TODO
                // if the value is already -1 => exception!
                countNbOfEachValue[sudokuSquares[i][columnId].getWinnerValue()] = -1;
            }
            else {
                System.out.println("\t" + i + ", "+ columnId+ " + - NOT winner value" + Arrays.toString(sudokuSquares[i][columnId].getPossibleValues()));
                int[] valuesInStatePossible = sudokuSquares[i][columnId].getPossibleValues();
                for(int aValueInStatePossible : valuesInStatePossible) {
                    countNbOfEachValue[aValueInStatePossible] ++;
                }
            }
        }

        System.out.println("Count:" + Arrays.toString(countNbOfEachValue));

        for(int i = 0; i < countNbOfEachValue.length ; i++) {

            if(countNbOfEachValue[i] == 1) {
                int[] winnerValue = {i};

                for (int j = 0 ; j < nbRows ; j++) {
                    if (! sudokuSquares[j][columnId].isWinnerValueFound()
                            && containsTheValue(sudokuSquares[j][columnId].getPossibleValues(), i)) {

                        sudokuSquares[j][columnId].setWinnerValue(i);
                    }
                }
            }
        }
    }

    private boolean containsTheValue(int[] possibleValues, int searchedValue) {
        for(int possibleValue : possibleValues) {
            if(possibleValue == searchedValue) {
                return true;
            }
        }
        return false;
    }

    // First algorithm: clean up around a winner value.
    // The algorithm checks one time all the squares of the sudoku:
    // the square has a winner value but the cleanup was not done on the line, the row and the region.
    private boolean handleWinnerNotDigested() {
        boolean foundWinnerNotDigested = false;

        for(int i = 0 ; i < sudokuRowSize ; i++) {
            for(int j = 0 ; j < sudokuColSize ; j++) {
                if(isWinnerValueButNotYetDigest(i, j)) {
                    int winnerValue = sudokuSquares[i][j].getWinnerValue();
                    setLoserValueToOtherSquares(winnerValue, i, j);
                    sudokuSquares[i][j].setWinnerValueDigestedAtTheSudokuLayer();
                    foundWinnerNotDigested = true;
                }
            }
        }
        return foundWinnerNotDigested;
    }

    private boolean isWinnerValueButNotYetDigest(int i, int j) {
        return sudokuSquares[i][j].isWinnerValueFound()
                && !sudokuSquares[i][j].winnerValueDigestedAtTheSudokuLayer();
    }

    private boolean allWinnerFound() {
        for(int i = 0 ; i < sudokuRowSize ; i++) {
            for(int j = 0 ; j < sudokuColSize ; j++) {
                if (! this.sudokuSquares[i][j].isWinnerValueFound())
                    return false;
            }
        }
        return true;
    }

    private void setLoserValueToOtherSquares(int loserValue, int rowId, int columnId) {
        setLoserOnAColumnExcept(loserValue, rowId, columnId);
        setLoserOnARowExcept(loserValue, rowId, columnId);
        setLoserOnARegionExcept(loserValue, rowId, columnId);
    }

    private void setLoserOnARegionExcept(int loserValue, int rowId, int columnId) {
        int[] columnIdsOfRegion = getColumnIdsOfRegionColumnId(getRegionColumnId(columnId));
        int[] rowIdsOfRegion = getRowIdsOfRegionRowId(getRegionRowId(rowId));

        for(int columnIdOfTheRegion : columnIdsOfRegion) {
            for (int rowIdOfTheRegion : rowIdsOfRegion) {
                if(columnId != columnIdOfTheRegion && rowId != rowIdOfTheRegion) {
                    sudokuSquares[rowIdOfTheRegion][columnIdOfTheRegion].setLoserValue(loserValue);
                }
            }
        }
    }

    private void setLoserOnARowExcept(int loserValue, int rowId, int columnId) {
        for(int i = 0; i < sudokuColSize ; i++) {
            if( i != columnId) {
                sudokuSquares[rowId][i].setLoserValue(loserValue);
            }
        }
    }

    private void setLoserOnAColumnExcept(int loserValue, int rowId, int columnId) {
        for(int i = 0 ; i < sudokuRowSize ; i++) {
            if( i != rowId) {
                sudokuSquares[i][columnId].setLoserValue(loserValue);
            }
        }
    }

    private int[] getColumnIdsOfRegionColumnId(int regionColumnId) {
        int[] columnIdsOfTheRegion = new int[regionColSize];

        for(int i = 0; i < regionColSize ; i++) {
            columnIdsOfTheRegion[i] = regionColumnId * regionColSize + i;
        }

        return columnIdsOfTheRegion;
    }

    private int[] getRowIdsOfRegionRowId(int regionRowId) {
        int[] rowIdsOfTheRegion = new int[regionRowSize];

        for(int i = 0; i < regionRowSize ; i++) {
            rowIdsOfTheRegion[i] = regionRowId * regionRowSize + i;
        }

        return rowIdsOfTheRegion;
    }

    private int getRegionRowId(int rowId) {
        return rowId / regionRowSize;
    }

    private int getRegionColumnId(int columnId) {
        return columnId / regionColSize;
    }

    public void printlnFoundValues() {
        for(int i = 0 ; i < sudokuRowSize ; i++) {
            for (int j = 0; j < sudokuColSize; j++) {
                System.out.print(sudokuSquares[i][j].getWinnerValue() + "\t");
            }
            System.out.print("\n");
        }
    }

    public void printlnRemainingPossibleValues() {
        for(int i = 0 ; i < sudokuRowSize ; i++) {
            for (int j = 0; j < sudokuColSize; j++) {
                System.out.print(sudokuSquares[i][j].toString()+ "\t");
            }
            System.out.print("\n");
        }
    }

    // TODO
    // Let be more drastic and validate that the input is respecting "normal" size
    // https://en.wikipedia.org/wiki/Sudoku#Variations_of_grid_sizes_or_region_shapes
    private void validateConstructorParamaters(int[][] sudoKuToResolve,
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
