package main.java;

import java.util.*;
import java.util.stream.Collectors;


public class SudokuSolver {

    private int regionRowSize = 0;
    private int regionColSize = 0;
    private int sudokuRowSize = 0;
    private int sudokuColSize = 0;

    private SudokuSquare[][] sudokuSquares;


    public SudokuSolver (int[][] sudoKuToResolve, int regionRowSize, int regionColSize,
                         int sudokuRowSize, int sudokuColSize) {

        // TODO add a light unit test to cover the fact we are validating the input
        sudokuSquares = SudokuBuilder.getInstance().buildSudoku(sudoKuToResolve, regionRowSize, regionColSize, sudokuRowSize, sudokuColSize);

        this.regionRowSize = regionRowSize;
        this.regionColSize = regionColSize;
        this.sudokuRowSize = sudokuRowSize;
        this.sudokuColSize = sudokuColSize;
    }


    public void resolveTheSudoku() {
        List<List<SudokuSquare>> listOfSquares = getAllListsOfSquaresWhichShouldContainAllValues() ;

        for(int k = 0 ; k < 20 ; k++) {

            System.out.println("\t\t\tk " + k);

            boolean foundAWinnerNotDigested = handleWinnerNotDigested();

            if(allWinnerFound()) break;

            // As long as #handleWinnerNotDigested is digesting, we don't run deeper algorithms
            if( !foundAWinnerNotDigested) {
                boolean foundAWinner = handleOneValuesOnlyAvailableInOneSquare(listOfSquares);

                if(! foundAWinner) {
                    System.out.println("\t\tSize " + listOfSquares.size());
                    //handleTwoValuesOnlyAvailableInTwoSquares(listOfSquares);
                }

                if(allWinnerFound()) break;
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




    private void handleTwoValuesOnlyAvailableInTwoSquares(List<List<SudokuSquare>> listOfSquares) {
        listOfSquares.forEach(this::handleTwoValuesOnlyAvailableInTwoSquaresOneList);
    }

    private void handleTwoValuesOnlyAvailableInTwoSquaresOneList(List<SudokuSquare> listOfSquares) {
        validateSizeListOfSquares(listOfSquares);

        System.out.println("----------------- BEGINNING OF THE FUNCTION -------------------");
        System.out.println("handleTwoValuesOnlyAvailableInTwoSquaresOneList BEGIN " + listOfSquares.toString());

        Map<Integer, List<SudokuSquare>> squaresPerPossibleValues = initSquaresPerPossibleValues(listOfSquares);

        System.out.println("List of squares for all values which are not winner yet");
        squaresPerPossibleValues.entrySet().stream()
                .forEach(entry -> System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue()));

        List<Integer> keysToRemove = new ArrayList<>();

        for(Map.Entry<Integer, List<SudokuSquare>> entry : squaresPerPossibleValues.entrySet()) {
            Integer key = entry.getKey();
            List<SudokuSquare> value = entry.getValue();
            if(value.size() != 2
                    || value.get(0).getPossibleValues().length != 2
                    || value.get(1).getPossibleValues().length != 2 ) {
                keysToRemove.add(key);
            }
        }

        for(Integer keyToRemove: keysToRemove) {
            squaresPerPossibleValues.remove(keyToRemove);
        }

        System.out.println("After clean up");
        squaresPerPossibleValues.entrySet().stream()
                .forEach(entry -> System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue()));

        if(squaresPerPossibleValues.size() <2) {
            System.out.println("There is just one value with only 2 squares");
            return;
        }

        Collection<List<SudokuSquare>> squaresWith2PossibleValues = squaresPerPossibleValues.values();

        for (List<SudokuSquare> oneListOfSquares : squaresWith2PossibleValues) {
            //System.out.print(oneListOfSquares);
            //System.out.print(" Occurrences: ");
            //System.out.println(Collections.frequency(squaresWith2PossibleValues, oneListOfSquares));
            int frequencyOfTheList = Collections.frequency(squaresWith2PossibleValues, oneListOfSquares);

            // we found 2 squares with the same list of 2 possible values
            if(frequencyOfTheList == 2) {
                System.out.println("We found a couple of 2 squares with the two same possible values.");

                List<SudokuSquare> squaresToSetLoser = new ArrayList<>(listOfSquares);
                squaresToSetLoser.removeAll(oneListOfSquares);

                for(int valueLoserInOtherSquares : oneListOfSquares.getFirst().getPossibleValues()) {
                    setLoserValueToSquares(valueLoserInOtherSquares, squaresToSetLoser);
                }
                return;
            }
        }
    }

    private Map<Integer, List<SudokuSquare>> initSquaresPerPossibleValues(List<SudokuSquare> listOfSquares) {
        Map<Integer, List<SudokuSquare>> squaresPerPossibleValues = new HashMap<Integer, List<SudokuSquare>>();

        for(SudokuSquare aSquare : listOfSquares) {
            int[] thePossibleValuesOfTheSquare = aSquare.getPossibleValues();

            for(int onePossibleValue : thePossibleValuesOfTheSquare) {
                List<SudokuSquare> theSquares = getSudokuSquaresForTheValue(squaresPerPossibleValues, onePossibleValue);
                theSquares.add(aSquare);
            }
        }

        return squaresPerPossibleValues;
    }

    private static List<SudokuSquare> getSudokuSquaresForTheValue(Map<Integer, List<SudokuSquare>> squaresPerPossibleValues, Integer onePossibleValueInt) {
        if(! squaresPerPossibleValues.containsKey(onePossibleValueInt)) {
            List<SudokuSquare> theList = new ArrayList<SudokuSquare>();
            squaresPerPossibleValues.put(onePossibleValueInt, new ArrayList<SudokuSquare>());
        }

        return squaresPerPossibleValues.get(onePossibleValueInt);
    }

    private void setLoserValueToSquares(int valueLoserInOtherSquares, List<SudokuSquare> squaresToSetLoser) {
        System.out.println("setLoserValueToSquares " + valueLoserInOtherSquares + " - "+ squaresToSetLoser.toString());
        for(SudokuSquare square : squaresToSetLoser) {
            square.setLoserValue(valueLoserInOtherSquares);
        }
    }

    // Second algorithm : find the value only available in one place
    // Let assume we have a 9 x9 sudoku. On one row, we have the follow possible values:
    //                      8,9 | 3 | 7 | 4 | 5 | 1 | 8,9 | 2,8 | 6
    // Only 3 squares are not resolved yet. In the position 8, we can see the possibles values 2 and 8.
    // 2 is not possible in other squares. Then even if there are 2 possibles values in the position 8,
    // we know for sure that 2 is the winner value in the position 8. We can update this line to
    //                      8,9 | 3 | 7 | 4 | 5 | 1 | 8,9 | 2 | 6
    private boolean handleOneValuesOnlyAvailableInOneSquare(List<List<SudokuSquare>> listOfSquares) {
        boolean findAWinner = false;
        for(List<SudokuSquare> squares : listOfSquares) {
            boolean findAWinnerOneRun = handleOneValueOnlyAvailableInOneSquareOneList(squares);
            findAWinner &= findAWinnerOneRun;
        }

        return findAWinner;
        //listOfSquares.forEach(this::handleOneValueOnlyAvailableInOneSquareOneList);
    }

    private List<List<SudokuSquare>> getAllListsOfSquaresWhichShouldContainAllValues() {
        List<List<SudokuSquare>> allBlocksOfSquares = new ArrayList<List<SudokuSquare>>();

        for(int i = 0 ; i < sudokuRowSize ; i++) {
            allBlocksOfSquares.add(getSquaresOfTheRow(i));
        }
        for(int i = 0 ; i < sudokuColSize ; i++) {
            allBlocksOfSquares.add(getSquaresOfTheCol(i));
        }

        int nbRegionRow = sudokuRowSize / regionRowSize;
        int nbRegionCol = sudokuColSize / regionColSize;

        for(int i = 0 ; i < nbRegionRow ; i++) {
            for(int j = 0 ; j < nbRegionCol ; j++) {
                allBlocksOfSquares.add(getSquaresOfTheRegion(i, j));
            }
        }
        return allBlocksOfSquares;
    }


    // to rename
    private boolean handleOneValueOnlyAvailableInOneSquareOneList(List<SudokuSquare> listOfSquares) {
        validateSizeListOfSquares(listOfSquares);

        boolean findAWinner = false;

        List<List<SudokuSquare>> squaresPerPossibleValues = buildEmptySquaresPerPossibleValues();

        for(SudokuSquare aSquare : listOfSquares) {
            if(aSquare.isWinnerValueFound()) {
                squaresPerPossibleValues.get(aSquare.getWinnerValue()).add(aSquare);
            }
            else {
                int[] possibleValues = aSquare.getPossibleValues();

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
                findAWinner = true;
            }
        }

        return findAWinner;
    }

    private List<List<SudokuSquare>> buildEmptySquaresPerPossibleValues() {
        List<List<SudokuSquare>> squaresPerPossibleValues = new ArrayList<List<SudokuSquare>>();

        for (int i = 0 ; i < sudokuColSize + 1 ; i++) {
            squaresPerPossibleValues.add(i, new ArrayList<SudokuSquare>());
        }

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
    // The square has a winner value but the cleanup was not done on the line, the row and the region.
    private boolean handleWinnerNotDigested() {
        boolean foundWinnerNotDigested = false;

        for(int i = 0 ; i < sudokuRowSize ; i++) {
            for(int j = 0 ; j < sudokuColSize ; j++) {
                if(isWinnerValueButNotYetDigest(i, j)) {
                    int winnerValue = sudokuSquares[i][j].getWinnerValue();
                    setLoserValueToOtherSquares(winnerValue, sudokuSquares[i][j]);
                    sudokuSquares[i][j].setWinnerValueDigestedAtTheSudokuLayer();
                    foundWinnerNotDigested = true;
                }
            }
        }
        return foundWinnerNotDigested;
    }

    private boolean isWinnerValueButNotYetDigest(int i, int j) {
        return this.sudokuSquares[i][j].isWinnerValueFound()
                && !this.sudokuSquares[i][j].winnerValueDigestedAtTheSudokuLayer();
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

    private void setLoserValueToOtherSquares(int loserValue, List<SudokuSquare> squaresToNotUpdate) {
        setLoserOnAColumnExcept(loserValue, squaresToNotUpdate);
        setLoserOnARowExcept(loserValue, squaresToNotUpdate);
        setLoserOnARegionExcept(loserValue, squaresToNotUpdate);
    }

    private void setLoserValueToOtherSquares(int loserValue, SudokuSquare squareToNotUpdate) {
        setLoserOnAColumnExcept(loserValue, squareToNotUpdate);
        setLoserOnARowExcept(loserValue, squareToNotUpdate);
        setLoserOnARegionExcept(loserValue, squareToNotUpdate);
    }

    private void setLoserOnARegionExcept(int loserValue, SudokuSquare square) {
        List<SudokuSquare> squares = Collections.singletonList(square);
        setLoserOnARegionExcept(loserValue, squares);
    }

    // /!\ we assume the squares are in the same region
    // TODO implement a validation
    private void setLoserOnARegionExcept(int loserValue, List<SudokuSquare> squaresToNotUpdate) {
        List<Integer> colsIdToNotSetLoser = squaresToNotUpdate.stream()
                .map(SudokuSquare::getColId)
                .collect(Collectors.toList());

        List<Integer> rowsIdToNotSetLoser = squaresToNotUpdate.stream()
                .map(SudokuSquare::getRowId)
                .collect(Collectors.toList());

        int[] columnIdsOfRegion = getColumnIdsOfRegionColumnId(
                getRegionColumnId(squaresToNotUpdate.getFirst().getColId()));
        int[] rowIdsOfRegion = getRowIdsOfRegionRowId(
                getRegionRowId(squaresToNotUpdate.getFirst().getRowId()));

        for(int columnIdOfTheRegion : columnIdsOfRegion) {
            for (int rowIdOfTheRegion : rowIdsOfRegion) {
                if( ! colsIdToNotSetLoser.contains(columnIdOfTheRegion)
                        && ! rowsIdToNotSetLoser.contains(rowIdOfTheRegion)) {
                    sudokuSquares[rowIdOfTheRegion][columnIdOfTheRegion].setLoserValue(loserValue);
                }
            }
        }
    }

    private void setLoserOnARowExcept(int loserValue, SudokuSquare square) {
        List<SudokuSquare> squares = Collections.singletonList(square);
        setLoserOnARowExcept (loserValue, squares);
    }

    private void setLoserOnARowExcept(int loserValue, List<SudokuSquare> squaresToNotUpdate) {
        validateSameRow(squaresToNotUpdate);
        int rowId = squaresToNotUpdate.getFirst().getRowId();

        List<Integer> colsIdToNotSetLoser = squaresToNotUpdate.stream()
                .map(SudokuSquare::getColId)
                .collect(Collectors.toList());

        for(int i = 0; i < sudokuColSize ; i++) {
            if( ! colsIdToNotSetLoser.contains(i) ) {
                sudokuSquares[rowId][i].setLoserValue(loserValue);
            }
        }
    }

    private void validateSameRow(List<SudokuSquare> squares) {
        if(squares.size() <= 1)
            return;
        int rowIdOfTheFirstSquare = squares.getFirst().getRowId();
        for(SudokuSquare aSquare : squares) {
            if(aSquare.getRowId() != rowIdOfTheFirstSquare)
                throw new IllegalStateException("We need all the squares to have the same row id.");
        }
    }


    private void setLoserOnAColumnExcept(int loserValue, SudokuSquare square) {
        List<SudokuSquare> squares = Collections.singletonList(square);
        setLoserOnAColumnExcept (loserValue, squares);
    }

    private void setLoserOnAColumnExcept(int loserValue, List<SudokuSquare> squaresToNotUpdate) {
        validateSameColumn(squaresToNotUpdate);

        List<Integer> rowsIdToNotSetLoser = squaresToNotUpdate.stream()
                .map(SudokuSquare::getRowId)
                .collect(Collectors.toList());

        int colId = squaresToNotUpdate.getFirst().getColId();

        for(int i = 0 ; i < sudokuRowSize ; i++) {
            if( ! rowsIdToNotSetLoser.contains(i)) {
                sudokuSquares[i][colId].setLoserValue(loserValue);
            }
        }
    }

    private void validateSameColumn(List<SudokuSquare> squares) {
        if(squares.size() <= 1)
            return;
        int colIdOfTheFirstSquare = squares.getFirst().getColId();
        for(SudokuSquare aSquare : squares) {
            if(aSquare.getColId() != colIdOfTheFirstSquare)
                throw new IllegalStateException("We need all the squares to have the same column id.");
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
}
