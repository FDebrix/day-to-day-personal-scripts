package main.java;

import java.util.*;

import static main.java.SudokuHelper.SOP;


public class SudokuSolver {

    private SudokuSquare[][] squares;
    private List<SudokuRegion> regions;


    public SudokuSolver (int[][] sudoKuToResolve) {

        SudokuBuilder builder = new SudokuBuilder();
        // TODO add a light unit test to cover the fact we are validating the input
        SudokuBuilder.SudokuBuilderOutput sudokuBuilderOutput = builder.buildSudoku(sudoKuToResolve);
        squares = sudokuBuilderOutput.allTheSquares;
        regions = sudokuBuilderOutput.allTheRegions;

        if(SOP && allWinnerFound()) { System.out.println("The sudoku was resolved without running algorithms"); }
    }


    public int[][] resolveTheSudoku() {
        OneValueInOneSquareOfTheRegion algo1 = new OneValueInOneSquareOfTheRegion();

        for(int k = 0 ; k < 20 ; k++) {

            if(allWinnerFound()) break;

            boolean foundAWinner = algo1.runAlgorithm(regions);

            if(! foundAWinner) {
                //System.out.println("\t\tSize " + regions.listOfSquares.size());
                //handleTwoValuesOnlyAvailableInTwoSquares(listOfSquares);
            }

            if(allWinnerFound()) break;
        }

        return SudokuHelper.getInstance().getWinnerValues(this.squares);
    }

    private boolean allWinnerFound() {
        return SudokuHelper.getInstance().allWinnerFound(this.squares);
    }

    public void printPossibleValues() {
        SudokuHelper.getInstance().printlnRemainingPossibleValues(this.squares);
    }

    /*
    private void handleTwoValuesOnlyAvailableInTwoSquares(List<List<SudokuSquare>> listOfSquares) {
        listOfSquares.forEach(this::handleTwoValuesOnlyAvailableInTwoSquaresOneList);
    }

    private void handleTwoValuesOnlyAvailableInTwoSquaresOneList(List<SudokuSquare> listOfSquares) {
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

     */
}
