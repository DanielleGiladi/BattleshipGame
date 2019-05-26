package com.example.danie.battleshipnewversion.Logic;




public class Tile  {

    public Board.TileState getStatus() {
        return mStatus;
    }

    public void setStatus(Board.TileState status) {
        this.mStatus = status;
    }

    private Board.TileState mStatus;

    public Tile() {

        mStatus = Board.TileState.NONE;


    }


}