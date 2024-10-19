package main.java;

import java.util.*;

public class SudokuSolver {

    private int[][] sudokuToResolve;
    private int regionRowSize = 0;
    private int regionColSize = 0;
    private int sudokuRowSize = 0;
    private int sudokuColSize = 0;
    private SudokuSquare[][] sudokuSquares;


    public SudokuSolver (int[][] sudoKuToResolve, int regionRowSize, int regionColSize,
                         int sudokuRowSize, int sudokuColSize) {
        validateConstructorParameters(
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

            //System.out.println("\n\nk:" + k);
            printlnRemainingPossibleValues();

            if(allWinnerFound()) break;

            // As long as #handleWinnerNotDigested is digesting, we don't run deeper algorithms
            if( !foundAWinnerNotDigested) {
                handleWinnerValueAvailableOnlyInOneSquare();
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


    // TODO write javadoc
    // Second algorithm
    private void handleWinnerValueAvailableOnlyInOneSquare() {
        for(int i = 0 ; i < sudokuRowSize ; i++) {
            List<SudokuSquare> oneRow = getSquaresOfTheRow(i);
            handleListOfSquaresHaveAllSolutions(oneRow);
        }
        for(int i = 0 ; i < sudokuColSize ; i++) {
            List<SudokuSquare> oneCol = getSquaresOfTheCol(i);
            handleListOfSquaresHaveAllSolutions(oneCol);
        }

        int nbRegionRow = sudokuRowSize / regionRowSize;
        int nbRegionCol = sudokuColSize / regionColSize;

        for(int i = 0 ; i < nbRegionRow ; i++) {
            for(int j = 0 ; j < nbRegionCol ; j++) {
                List<SudokuSquare> oneRegion = getSquaresOfTheRegion(i, j);
                handleListOfSquaresHaveAllSolutions(oneRegion);
            }
        }
    }

    // to rename
    private void handleListOfSquaresHaveAllSolutions(List<SudokuSquare> listOfSquares) {
        validateSizeListOfSquares(listOfSquares);

        //List<List<SudokuSquare>> squaresPerPossibleValues = new ArrayList<List<SudokuSquare>>(sudokuColSize + 1);

        List<List<SudokuSquare>> squaresPerPossibleValues = buildEmptySquaresPerPossibleValues();

        for(SudokuSquare aSquare : listOfSquares) {
            if(aSquare.isWinnerValueFound()) {
                squaresPerPossibleValues.get(aSquare.getWinnerValue()).add(aSquare);
            }
            else {
                int[] possibleValues = aSquare.getPossibleValues();

                //System.out.println("The possible values :" + aSquare.toString());
                for(int possibleValue : possibleValues) {
                    squaresPerPossibleValues.get(possibleValue).add(aSquare);
                }
            }
        }

        for(int i = 0 ; i < squaresPerPossibleValues.size() ; i++) {
            List<SudokuSquare> squaresForOnePossibleValue = squaresPerPossibleValues.get(i);
            if(squaresForOnePossibleValue.size() == 1
                    && ! squaresForOnePossibleValue.getFirst().isWinnerValueFound() ){

                squaresForOnePossibleValue.getFirst().setWinnerValue(i);
            }
        }

        //System.out.println("Squares per possible values:\n" + squaresPerPossibleValues);
        // I AM HERE
    }

    private List<List<SudokuSquare>> buildEmptySquaresPerPossibleValues() {
        List<List<SudokuSquare>> squaresPerPossibleValues = new ArrayList<List<SudokuSquare>>();

        for (int i = 0 ; i < sudokuColSize + 1 ; i++) {
            squaresPerPossibleValues.add(i, new ArrayList<SudokuSquare>());
        }

        //System.out.println("sudokuColSize " + sudokuColSize + ", the size" + squaresPerPossibleValues.size());
        return squaresPerPossibleValues;
    }

    private void validateSizeListOfSquares(List<SudokuSquare> listOfSquares) {
        if(listOfSquares.size() != regionColSize * regionRowSize)
            throw new IllegalStateException("The size of the list " + listOfSquares.size()
                    + "does not match the multiplication of the size in column of a region "
                    + regionColSize + " and tje size in row of a region " + regionRowSize
            );
    }

    private List<SudokuSquare> getSquaresOfTheRegion(int regionRowId, int regionColId) {
        List<SudokuSquare> list = new ArrayList<>();

        int[] rowIdsOfTheRegion = getRowIdsOfRegionRowId(regionRowId);
        int[] columnIdsOfTheRegion = getColumnIdsOfRegionColumnId(regionColId);

        for(int columnIdOfTheRegion : columnIdsOfTheRegion) {
            for (int rowIdOfTheRegion : rowIdsOfTheRegion) {
                list.add(sudokuSquares[rowIdOfTheRegion][columnIdOfTheRegion]);
            }
        }

        return list;
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
