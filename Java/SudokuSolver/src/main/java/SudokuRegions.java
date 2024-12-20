package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a list of SudokuRegion. Decorator on SudokuRegion.
 */
public class SudokuRegions implements BroadcastWinner {

    private List<SudokuRegion> regions;


    public SudokuRegions () {
        regions = new ArrayList<>();
    }

    @Override
    public void broadcastWinner(SudokuSquare square) {
        if(regions.isEmpty())
            return;

        for(SudokuRegion region : regions)
            region.broadcastWinner(square);
    }

    public List<SudokuRegion> getRegions() {
        return regions;
    }

    public void addRegion(SudokuRegion region) {
        if(region == null)
            throw new IllegalArgumentException("The input region cannot be null");

        regions.add(region);
    }
}
