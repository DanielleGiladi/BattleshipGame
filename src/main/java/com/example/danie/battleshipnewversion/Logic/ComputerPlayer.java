package com.example.danie.battleshipnewversion.Logic;

import java.util.Random;

public class ComputerPlayer {

    public void think() {
        try {
            ///
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int playTurn(Board board) {

        think();

        Random random = new Random();
        int num = board.getBoardSize();
        int positionToPlay = random.nextInt(num);

        while(board.getTile(positionToPlay).getStatus() != Board.TileState.NONE &&
                board.getTile(positionToPlay).getStatus() != Board.TileState.S ) {

            positionToPlay = random.nextInt(num);

        }

        return positionToPlay;
    }

}


