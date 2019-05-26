package com.example.danie.battleshipnewversion.Logic;

import android.util.Log;


enum Turn {
    PLAYER1,PLAYER2;
}

public class Game  {


    public Board getmBoardComputer() {
        return mBoardComputer;
    }

    public void setmBoard(Board mBoard) {
        this.mBoardComputer = mBoard;
    }

    public Board getmBoardPlayer() {
        return mBoardPlayer;
    }

    public void setmBoardPlayer(Board mBoard) {
        this.mBoardPlayer = mBoard;
    }

    private Board mBoardComputer;
    private Board mBoardPlayer;


    public Turn getmTurn() {
        return mTurn;
    }

    public void setmTurn(Turn mTurn) {
        this.mTurn = mTurn;
    }

    private Turn mTurn;
    private int boardSize;


    private ComputerPlayer mComputerPlayer;

    public Game(int boardSize) {
        boardSize = boardSize;
        mTurn = Turn.PLAYER1;
        mComputerPlayer = new ComputerPlayer();
        mBoardPlayer = new Board(boardSize ,"player");
        mBoardComputer = new Board(boardSize , "computer");

    }

    public void toggleTurn() {

        if(mTurn == Turn.PLAYER1)
            mTurn = Turn.PLAYER2;
        else
            mTurn = Turn.PLAYER1;

    }

    public boolean playTile(int position) {

        boolean playerPlayed = false;

        switch(mTurn) {

            case PLAYER1:
            default:
                playerPlayed =mBoardComputer.setTile(position);
                mBoardComputer.setTileToD(mBoardComputer.getShips());

                break;
            case PLAYER2:
                playerPlayed =mBoardPlayer.setTile(position);
                mBoardPlayer.setTileToD(mBoardPlayer.getShips());
                break;


        }

        if(playerPlayed == true){
            boolean winner;
            if(mTurn.ordinal()==0){
                winner = mBoardComputer.boardWon();
            }
            else
                winner = mBoardPlayer.boardWon();

            if(winner ==false) {
                toggleTurn();

            } else {
                Log.e("GAME", "Winner " + winner);


            }


        }
        return playerPlayed;

    }

    public boolean gameWon(Board board) {
        return board.boardWon();
    }

    public int getBoardSize(){
        return boardSize;
    }

    public void playComputer() {

        int positionToPlay = mComputerPlayer.playTurn(mBoardPlayer);

        playTile(positionToPlay);

    }

}
