package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a list of SudokuRegion. Decorator on SudokuRegion.
 */
public class SudokuRegions implements BroadcastWinner {

    private List<BroadcastWinner> broadcastWinners;


    public SudokuRegions () {
        this.broadcastWinners = new ArrayList<>();
    }

    @Override
    public void broadcastWinner(SudokuSquare square) {
        if( this.broadcastWinners.isEmpty())
            return;

        for(BroadcastWinner broadcastWinner : this.broadcastWinners)
            broadcastWinner.broadcastWinner(square);
    }

    public void addBroadcastWinner(BroadcastWinner broadcastWinner) {
        if(broadcastWinner == null)
            throw new IllegalArgumentException("The input BroadcastWinner cannot be null");

        this.broadcastWinners.add(broadcastWinner);
    }
}
