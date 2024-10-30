package main.java;

import java.util.*;

import static main.java.SudokuHelper.allWinnerFound;


public class SudokuSolver {

    private int regionRowSize = 0;
    private int regionColSize = 0;
    private int sudokuRowSize = 0;
    private int sudokuColSize = 0;

    private SudokuSquare[][] sudokuSquares;


    public SudokuSolver (int[][] sudoKuToResolve, int regionRowSize, int regionColSize,
                         int sudokuRowSize, int sudokuColSize) {

        SudokuBuilder builder = new SudokuBuilder();
        // TODO add a light unit test to cover the fact we are validating the input
        sudokuSquares = builder.buildSudoku(sudoKuToResolve, sudokuRowSize, sudokuColSize, regionRowSize, regionColSize);

        this.regionRowSize = regionRowSize;
        this.regionColSize = regionColSize;
        this.sudokuRowSize = sudokuRowSize;
        this.sudokuColSize = sudokuColSize;
    }


    public void resolveTheSudoku() {
        List<List<SudokuSquare>> listOfSquares = getAllListsOfSquaresWhichShouldContainAllValues() ;

        for(int k = 0 ; k < 20 ; k++) {

            System.out.println("\t\t\tk " + k);

            if(allWinnerFound(this.sudokuSquares)) break;

            boolean foundAWinner = handleOneValuesOnlyAvailableInOneSquare(listOfSquares);

            if(! foundAWinner) {
                System.out.println("\t\tSize " + listOfSquares.size());
                //handleTwoValuesOnlyAvailableInTwoSquares(listOfSquares);
            }

            if(allWinnerFound(this.sudokuSquares)) break;
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
        System.out.println("handleTwoValuesOnlyAvailableInTwoSquaresOneList BEGIN " + listOfSquares);

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
        Map<Integer, List<SudokuSquare>> squaresPerPossibleValues = new HashMap<>();

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
            squaresPerPossibleValues.put(onePossibleValueInt, new ArrayList<>());
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
        boolean findAWinner = true;
        for(List<SudokuSquare> squares : listOfSquares) {
            boolean findAWinnerOneRun = handleOneValueOnlyAvailableInOneSquareOneList(squares);
            findAWinner &= findAWinnerOneRun;
        }

        return findAWinner;
        //listOfSquares.forEach(this::handleOneValueOnlyAvailableInOneSquareOneList);
    }

    private List<List<SudokuSquare>> getAllListsOfSquaresWhichShouldContainAllValues() {
        List<List<SudokuSquare>> allBlocksOfSquares = new ArrayList<>();

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
        List<List<SudokuSquare>> squaresPerPossibleValues = new ArrayList<>();

        for (int i = 0 ; i < sudokuColSize + 1 ; i++) {
            squaresPerPossibleValues.add(i, new ArrayList<>());
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
}
