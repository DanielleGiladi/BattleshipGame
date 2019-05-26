package com.example.danie.battleshipnewversion.Logic;

public class Ship {
    private int position[];

    public Ship( int pos , int ShipSize , String dir , int boardSize){
        this.position = new int[ShipSize];

        if (dir == "right"){
            for(int i = 0 ; i< ShipSize; i++){
                position[i] = pos;
                pos+=1;
            }

        }
        else if (dir == "down"){
            for(int i = 0 ; i< ShipSize ; i++){
                position[i] = pos;
                pos +=boardSize;

            }

        }

    }

    public int[] getPosition() {
        return position;
    }
}
