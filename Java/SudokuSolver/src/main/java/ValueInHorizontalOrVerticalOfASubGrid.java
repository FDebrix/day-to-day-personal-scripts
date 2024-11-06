package main.java;

import java.util.List;

import static main.java.SudokuHelper.SOP;

/**
 * In a row or a column, if a value is only possible in the same subgrid,
 * this value cannot be possible for the other squares of the subgrid.
 *
 * Below the first left columns of a real sudoku. For each square, the remaining possible values.
 *          | ----------------------------------------- |
 *          | COLUMN A       COLUMN B        COLUMN C   |
 *          | ----------------------------------------- |
 * ROW A    | [1]           [2,4,7]         [2,4,7,9]   |
 * ROW B    | [8]           [3,7]           [7,9]       |
 * ROW C    | [2,3,6]       [2,3,6]         [5]         |
 *          | ----------------------------------------- |
 * ROW D    | [3,6,7]       [5]             [6,7,8]     |
 * ROW E    | [2,3,4,6]     [2,3,4,6]       [1]         |
 * ROW F    | [2,7]         [9]             [2,7,8]     |
 *          | ----------------------------------------- |
 * ROW G    | [4,6,9]       [1,4,6,8]       [4,6,8,9]   |
 * ROW H    | [2,6,7,9]     [1,2,6,7,8]     [3]         |
 * ROW I    | [5]           [1,2,4]         [2,4,9]     |
 *
 * We have 3 subgrids: [AA-CC], [DA-FC], [GA-IC].
 *
 * In the column A, we must have the value 9. This value is only possible in
 * the bottom subgrid. That means we must have a 9 in [GA] or in [HA].
 * Then we can conclude that this is not possible to have a 9 in [GC] and in [IC].
 *
 * In the column B, we must have the value 8. This value is only possible in
 * the bottom subgrid. That means we cannot have an 8 in [GC].
 *
 * After the cleanup, we have:
 *          | ----------------------------------------- |
 *          | COLUMN A       COLUMN B        COLUMN C   |
 *          | ----------------------------------------- |
 * ROW A    | [1]           [2,4,7]         [2,4,7,9]   |
 * ROW B    | [8]           [3,7]           [7,9]       |
 * ROW C    | [2,3,6]       [2,3,6]         [5]         |
 *          | ----------------------------------------- |
 * ROW D    | [3,6,7]       [5]             [6,7,8]     |
 * ROW E    | [2,3,4,6]     [2,3,4,6]       [1]         |
 * ROW F    | [2,7]         [9]             [2,7,8]     |
 *          | ----------------------------------------- |
 * ROW G    | [4,6,9]       [1,4,6,8]       [4,6]       |
 * ROW H    | [2,6,7,9]     [1,2,6,7,8]     [3]         |
 * ROW I    | [5]           [1,2,4]         [2,4]       |
 */
public class ValueInHorizontalOrVerticalOfASubGrid implements SudokuAlgorithm {
    @Override
    public boolean runAlgorithm(List<SudokuRegion> regions) {
        if(regions == null)
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
        if(region == null)
            return false;

        if(SudokuHelper.getInstance().allWinnerFound(region))
            return false;

        // This algorithm is run only on the horizontal or vertical region
        if( ! regionIsHorizontalOrVertical(region)) {
            return false;
        }

        if(SOP) System.out.println(this.getClass().getName() + ": The runAlgorithm function is run with 1 region " + region.getRegionType() + ".");

        boolean setLoserValue = false;

        List<List<SudokuSquare>> squaresPerPossibleValue = getSquaresPerPossibleValues(region);

        for(int i = 1 ; i < squaresPerPossibleValue.size() ; i++) {
            List<SudokuSquare> squaresForOnePossibleValue = squaresPerPossibleValue.get(i);

            SudokuRegion subgrid = getRegionWithAtLess2ValuesAndValuePossibleInSameSubgrid(squaresForOnePossibleValue);

            if(subgrid != null) {
                for(SudokuSquare squareOfTheSubgrid : subgrid.getSudokuSquares()) {
                    if( ! region.getSudokuSquares().contains(squareOfTheSubgrid)) {
                        squareOfTheSubgrid.setLoserValue(i);
                    }
                }
                setLoserValue = true;
            }
        }
        return setLoserValue;
    }

    private SudokuRegion getRegionWithAtLess2ValuesAndValuePossibleInSameSubgrid(List<SudokuSquare> squaresForOnePossibleValue) {
        if(asOneUniqueSquare(squaresForOnePossibleValue))
            return null;

        SudokuRegion subgridRegionOneTheFirstRegion = getSubGridRegion (squaresForOnePossibleValue.getFirst());

        for(SudokuSquare square : squaresForOnePossibleValue) {
            SudokuRegion subgrid = getSubGridRegion(square);

            if(subgrid != subgridRegionOneTheFirstRegion) {
                return null;
            }
        }

        return subgridRegionOneTheFirstRegion;
    }

    private SudokuRegion getSubGridRegion(SudokuSquare square) {
        List<SudokuRegion> regionsOfTheSquare = square.getRegions().getRegions();
        SudokuRegion subGridRegion = null;

        for(SudokuRegion region : regionsOfTheSquare) {
            if(region.getRegionType() == SudokuRegion.SudokuRegionType.SUB_GRID) {
                subGridRegion = region;
                break;
            }
        }

        if(subGridRegion == null) {
            throw new IllegalStateException("A square does not have a subgrid");
        }

        return subGridRegion;
    }

    // Or this is the winner value
    // Or this is the last possible value
    private boolean asOneUniqueSquare(List<SudokuSquare> squaresForOnePossibleValue) {
        return squaresForOnePossibleValue.size() == 1;
    }

    private List<List<SudokuSquare>> getSquaresPerPossibleValues(SudokuRegion region) {
        return SudokuHelper.getInstance().getSquaresPerPossibleValues(region);
    }

    private static boolean regionIsHorizontalOrVertical(SudokuRegion region) {
        return (region.getRegionType() == SudokuRegion.SudokuRegionType.HORIZONTAL)
                || (region.getRegionType() == SudokuRegion.SudokuRegionType.VERTICAL);
    }
}