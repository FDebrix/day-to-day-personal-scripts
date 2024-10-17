package main.java;

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
                this.sudokuSquares[i][j] = new SudokuSquare(nbPossibleValues);
                this.sudokuSquares[i][j].setInitialValue(sudokuToResolve[i][j]);
            }
        }
        System.out.println("AFTER THE CONVERSION - BEGIN");
        printlnFoundValues();
        System.out.println("AFTER THE CONVERSION - BEGIN\n\n\n");

        return this.sudokuSquares;
    }

    public void letDoAFirstRun() {
        int nbRows = sudokuToResolve.length;
        int nbColumns = sudokuToResolve[0].length;

        for(int k = 0 ; k < 10 ; k++) {
        for(int i = 0 ; i < nbRows ; i++) {
            for(int j = 0 ; j < nbColumns ; j++) {
                //System.out.println(k + ", " + i + ", " + j + ", toto: " + sudokuSquares[2][1].toString());

                //if(k==1 && i==1 && j==3)
                //    printlnRemainingPossibleValues();

                if(sudokuSquares[i][j].isWinnerValueFound()
                        && ! sudokuSquares[i][j].winnerValueDigestedAtTheSudokuLayer()) {
                    int winnerValue = sudokuSquares[i][j].getWinnerValue();
                    //System.out.println("\tNeed to digest the value "+winnerValue);
                    setLoserValueToOtherSquare(winnerValue, i, j);
                    sudokuSquares[i][j].setWinnerValueDigestedAtTheSudokuLayer();
                }
            }
        }
            System.out.println("\n\nk:" + k);
        printlnRemainingPossibleValues();
        }
    }

    private void setLoserValueToOtherSquare(int loserValue, int rowId, int columnId) {
        int nbRows = sudokuToResolve.length;
        int nbColumns = sudokuToResolve[0].length;

        for(int i = 0 ; i < nbRows ; i++) {
            if( i != rowId ) {
                /*
                if((i ==2 && columnId==1) || (i ==2 && columnId==2)) {
                    System.out.println("\t\tLoser " + loserValue + " in " + i + ", " + columnId);
                    System.out.println("\t\t" + sudokuSquares[i][columnId].toString());
                }
                 */
                sudokuSquares[i][columnId].setLoserValue(loserValue);
            }
        }
        //System.out.println("\t\tDone with the Loser vertical");
        for(int i = 0; i < nbColumns ; i++) {
            if( i != columnId) {
                /*
                if((rowId ==2 && i==1) || (rowId ==2 && i==2)) {
                    System.out.println("\t\tLoser " + loserValue + " in " + rowId + ", " + i);
                    System.out.println("\t\t"+ sudokuSquares[rowId][i].toString());
                }
                */
                sudokuSquares[rowId][i].setLoserValue(loserValue);
            }
        }
        //System.out.println("\t\tDone with the Loser horizontal");
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
