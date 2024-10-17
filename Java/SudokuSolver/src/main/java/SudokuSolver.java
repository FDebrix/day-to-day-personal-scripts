package main.java;

import java.util.Arrays;

public class SudokuSolver {

    private int[][] sudokuToResolve;
    private SudokuSquare[][] sudokuSquares;


    public SudokuSolver (int[][] sudoKuToResolve) {
        validateSudokuToResolveIsConsistent(sudoKuToResolve);

        this.sudokuToResolve = sudoKuToResolve;
    }

    public int[][] getSudoku() {
        return this.sudokuToResolve;
    }


    public SudokuSquare[][] convertArrayToSudokuSquare() {
        int nbRows = sudokuToResolve.length;
        int nbColumns = sudokuToResolve[0].length;
        int nbPossibleValues = nbRows;
        this.sudokuSquares = new SudokuSquare[nbRows][nbColumns];

        for(int i = 0 ; i < nbRows ; i++) {
            for(int j = 0 ; j < nbColumns ; j++) {
                this.sudokuSquares[i][j] = new SudokuSquare(nbPossibleValues, sudokuToResolve[i][j]);
            }
        }
        /*.out.println("AFTER THE CONVERSION - BEGIN");
        printlnFoundValues();
        System.out.println("AFTER THE CONVERSION - BEGIN\n\n\n");

         */

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
                    handleWinnerValueAvailableOnlyInOneSquare(i);
                }
            }
        }
    }

    private void handleWinnerValueAvailableOnlyInOneSquare(int columnId) {
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

    private boolean handleWinnerNotDigested() {
        boolean foundWinnerNotDigested = false;

        int nbRows = sudokuToResolve.length;
        int nbColumns = sudokuToResolve[0].length;
        for(int i = 0 ; i < nbRows ; i++) {
            for(int j = 0 ; j < nbColumns ; j++) {
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
        int nbRows = sudokuToResolve.length;
        int nbColumns = sudokuToResolve[0].length;

        for(int i = 0 ; i < nbRows ; i++) {
            for(int j = 0 ; j < nbColumns ; j++) {
                if (! this.sudokuSquares[i][j].isWinnerValueFound())
                    return false;
            }
        }
        return true;
    }

    public int[][] getWinnerValues() {
        int nbRows = sudokuToResolve.length;
        int nbColumns = sudokuToResolve[0].length;
        int[][] result = new int[nbRows][nbColumns];

        for(int i = 0; i < sudokuSquares.length ; i++) {
            for (int j = 0; j < sudokuSquares[0].length ; j++) {
                result[i][j] = sudokuSquares[i][j].getWinnerValue();
            }
        }

        return result;
    }

    private void setLoserValueToOtherSquares(int loserValue, int rowId, int columnId) {
        int nbRows = sudokuToResolve.length;
        int nbColumns = sudokuToResolve[0].length;

        for(int i = 0 ; i < nbRows ; i++) {
            if( i != rowId ) {
                sudokuSquares[i][columnId].setLoserValue(loserValue);
            }
        }
        for(int i = 0; i < nbColumns ; i++) {
            if( i != columnId) {
                sudokuSquares[rowId][i].setLoserValue(loserValue);
            }
        }

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

    private int[] getColumnIdsOfRegionColumnId(int regionColumnId) {
        // hardcoded
        int regionColumnSize = 3;
        int[] columnIdsOfTheRegion = new int[regionColumnSize];

        columnIdsOfTheRegion[0] = regionColumnId * regionColumnSize;
        columnIdsOfTheRegion[1] = regionColumnId * regionColumnSize + 1;
        columnIdsOfTheRegion[2] = regionColumnId * regionColumnSize + 2;

        return columnIdsOfTheRegion;
    }

    private int[] getRowIdsOfRegionRowId(int regionRowId) {
        // hardcoded
        int regionRowSize = 3;
        int[] rowIdsOfTheRegion = new int[regionRowSize];

        rowIdsOfTheRegion[0] = regionRowId * regionRowSize;
        rowIdsOfTheRegion[1] = regionRowId * regionRowSize + 1;
        rowIdsOfTheRegion[2] = regionRowId * regionRowSize + 2;

        return rowIdsOfTheRegion;
    }

    private int getRegionRowId(int rowId) {
        // hard coded for now
        return rowId / 3;
    }

    private int getRegionColumnId(int columnId) {
        // hard coded for now
        return columnId / 3;
    }

    public void printlnFoundValues() {
        int nbRows = sudokuToResolve.length;
        int nbColumns = sudokuToResolve[0].length;

        for(int i = 0 ; i < nbRows ; i++) {
            for (int j = 0; j < nbColumns; j++) {
                System.out.print(sudokuSquares[i][j].getWinnerValue() + "\t");
            }
            System.out.print("\n");
        }
    }

    public void printlnRemainingPossibleValues() {
        int nbRows = sudokuToResolve.length;
        int nbColumns = sudokuToResolve[0].length;

        for(int i = 0 ; i < nbRows ; i++) {
            for (int j = 0; j < nbColumns; j++) {
                System.out.print(sudokuSquares[i][j].toString()+ "\t");
            }
            System.out.print("\n");
        }
    }

    // TODO
    // Let be more drastic and validate that the input is respecting "normal" size
    // https://en.wikipedia.org/wiki/Sudoku#Variations_of_grid_sizes_or_region_shapes
    private void validateSudokuToResolveIsConsistent(int[][] sudoKuToResolve) {
        int lengthFirstRow = sudoKuToResolve[0].length;

        for (int[] aRow: sudoKuToResolve) {
            if (aRow.length != lengthFirstRow)
                throw new IllegalArgumentException("The length of the rows is not consistent");
        }
    }
}
