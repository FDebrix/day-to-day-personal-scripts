package main.java.algorithms;

import main.java.SudokuAlgorithm;
import main.java.SudokuHelper;
import main.java.SudokuRegion;
import main.java.SudokuSquare;

import java.util.List;

import static main.java.SudokuHelper.SOP;

public abstract class SudokuAbstractAlgorithm implements SudokuAlgorithm {

    @Override
    public boolean runAlgorithm(List<SudokuRegion> regions) {
        if(regions == null || regions.isEmpty())
            return false;

        if(SOP) System.out.println(this.getClass().getName() + ": The runAlgorithm function was call with " + regions.size() + " regions.");

        boolean findAWinner = true;

        for(SudokuRegion region : regions) {
            boolean findAWinnerOneRun = runAlgorithm(region);
            findAWinner &= findAWinnerOneRun;
        }

        return findAWinner;
    }

    @Override
    public boolean runAlgorithm(SudokuRegion region) {
        boolean needToRunTheAlgorithm = needToRunTheAlgorithm(region);
        if ( !needToRunTheAlgorithm ) return false;

        if(SOP) System.out.println(this.getClass().getName() + ": The runAlgorithm function is run with 1 region " + region.getRegionType() + ".");

        return internalRunAlgorithm(region);
    }

    protected abstract boolean internalRunAlgorithm(SudokuRegion region) ;


    protected List<List<SudokuSquare>> getSquaresPerPossibleValues(SudokuRegion region) {
        return SudokuHelper.getInstance().getSquaresPerPossibleValues(region);
    }

    private boolean needToRunTheAlgorithm(SudokuRegion region) {
        if(region == null || SudokuHelper.getInstance().allWinnerFound(region))
            return false;

        return true;
    }
}
