package main.java;

import java.util.List;

/**
 * In a row or a column, if a value is only possible in the same subgrid,
 * this value cannot be possible for the other squares of the subgrid.
 *
 * Below the first left columns of a real sudoku. For each square, the remaining possible values.
 *          | ----------------------------------------- |
 *          | COLUMN A       COLUMN B        COLUMN C   |
 *          | ----------------------------------------- |
 * ROW A    | [1]	    	[2,4,7]		    [2,4,7,9]   |
 * ROW B    | [8]		    [3,7]		    [7,9]       |
 * ROW C    | [2,3,6]	    [2,3,6]		    [5]         |
 *          | ----------------------------------------- |
 * ROW D    | [3,6,7]	    [5]			    [6,7,8]     |
 * ROW E    | [2,3,4,6]	    [2,3,4,6]	    [1]         |
 * ROW F    | [2,7]		    [9]			    [2,7,8]     |
 *          | ----------------------------------------- |
 * ROW G    | [4,6,9]  	    [1,4,6,8]	    [4,6,8,9]   |
 * ROW H    | [2,6,7,9]	    [1,2,6,7,8]	    [3]         |
 * ROW I    | [5]	        [1,2,4]		    [2,4,9]     |
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
 * ROW A    | [1]	    	[2,4,7]		    [2,4,7,9]   |
 * ROW B    | [8]		    [3,7]		    [7,9]       |
 * ROW C    | [2,3,6]	    [2,3,6]		    [5]         |
 *          | ----------------------------------------- |
 * ROW D    | [3,6,7]	    [5]			    [6,7,8]     |
 * ROW E    | [2,3,4,6]	    [2,3,4,6]	    [1]         |
 * ROW F    | [2,7]		    [9]			    [2,7,8]     |
 *          | ----------------------------------------- |
 * ROW G    | [4,6,9]  	    [1,4,6,8]	    [4,6]       |
 * ROW H    | [2,6,7,9]	    [1,2,6,7,8]	    [3]         |
 * ROW I    | [5]	        [1,2,4]		    [2,4]       |
 */
public class ValueInHorizontalOrVerticalOfASubGrid implements SudokuAlgorithm {
    @Override
    public boolean runAlgorithm(List<SudokuRegion> regions) {
        if(regions == null)
            return false;

        return false;
    }

    @Override
    public boolean runAlgorithm(SudokuRegion region) {
        if(region == null)
            return false;

        region.getSudokuSquares().getFirst().getBroadcastWinner();

        return false;
    }
}
