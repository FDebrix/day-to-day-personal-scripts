package main.java;

import java.util.List;

public interface SudokuAlgorithm {
    /**
     * Run the algorithm on a list of Regions.
     * @param regions The regions on which we need to run the algorithm.
     * @return true if at less one winner value was found.
     */
    public boolean runAlgorithm(List<SudokuRegion> regions);

    /**
     * Run the algorithm on one Region.
     * @param region The region on which we need to run the algorithm.
     * @return true if a one winner value was found.
     */
    public boolean runAlgorithm(SudokuRegion region);
}
