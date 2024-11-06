package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a list of SudokuRegion. Decorator on SudokuRegion.
 */
public class SudokuRegions implements BroadcastWinner {

    private List<BroadcastWinner> broadcastWinners;


    public SudokuRegions () {
        broadcastWinners = new ArrayList<>();
    }

    @Override
    public void broadcastWinner(SudokuSquare square) {
        if(broadcastWinners.isEmpty())
            return;

        for(BroadcastWinner broadcastWinner : broadcastWinners)
            broadcastWinner.broadcastWinner(square);
    }

    @Override
    public List<SudokuRegion> getRegions() {
        List<SudokuRegion> regions = new ArrayList<>();
        for(BroadcastWinner broadcastWinner : broadcastWinners) {
            regions.addAll(broadcastWinner.getRegions());
        }
        return regions;
    }

    public void addBroadcastWinner(BroadcastWinner broadcastWinner) {
        if(broadcastWinner == null)
            throw new IllegalArgumentException("The input BroadcastWinner cannot be null");

        broadcastWinners.add(broadcastWinner);
    }
}
