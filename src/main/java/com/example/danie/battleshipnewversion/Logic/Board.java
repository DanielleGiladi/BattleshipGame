package com.example.danie.battleshipnewversion.Logic;


import java.util.Random;

public class Board {

    private int MaxPoints;
    private int lostPoints;
    private String type;
    private int size;
    public enum TileState {
        NONE,X,O ,S ,D;

        @Override
        public String toString() {
            switch(this) {
                case NONE:
                default:
                    return "";
                case X:
                    return "X";
                case O:
                    return "O";
                case S:
                    return "S";
                case D:
                    return "D";

            }
        }
    }

    private Tile mTiles[] ;

    // optional start index for ships example pos2_3 means: 2 - len of ship 3 - size of the board
    private int pos3_3[] = {0 , 3 , 6 };
    private int pos2_3[] = {0,1 ,3 ,4 , 6 ,7 };
    private int pos3_4[] = {0,1,4,5,8,9,12,13};
    private int pos2_4[] = {0 ,1 ,2 ,4,5,6,8,9,10,12,13,14};
    private int pos3_5[]={0,1,2,5,6,7,10,11,12,15,16,17,20,21,22};
    private int pos2_5 [] = {0,1,2,3,5,6,7,8,10,11,12,13,15,16,17,18,20,21,22,23};
    private Ship allShip[] ;
    private int destroyedShips = 0;
    final String direction[] = {"right" , "down"};



    public Board(int size , String type) {

        this.type=type;
        this.size = size;
        this.mTiles = new Tile[size*size];
        for(int i = 0 ; i< mTiles.length ; i++) {
            mTiles[i] = new Tile();
        }

        if(size == 3 ){
            allShip = new Ship[2];
            randShipPos(pos3_3, pos2_3 ,getBoardSize());
            MaxPoints= 500;
            lostPoints = 50;
        }
        else if(size == 4 ){
            allShip = new Ship[3];
            randShipPos(pos3_4 , pos2_4,  getBoardSize());
            MaxPoints= 600;
            lostPoints = 30;
        }
        else {
            allShip = new Ship[3];
            randShipPos(pos3_5 , pos2_5 ,  getBoardSize());
            MaxPoints= 700;
            lostPoints = 30;
        }
    }

    public String getType(){
        return type;
    }

    public Tile[] getmTiles(){
        return mTiles;
    }

    public void randShipPos(int pop3[] , int pop2[] , int num){
        Random random = new Random();
        int dir = random.nextInt(2);
        int pos=0;
        int startPos = random.nextInt(pop3.length);
        if(direction[dir] == "right")
            pos = pop3[startPos];
        else
            pos=startPos;
        this.allShip[0] = new Ship( pos, 3 , direction[dir] , size);
        setTileToS(allShip[0]);


        dir = random.nextInt(2);
        startPos = random.nextInt(pop2.length);

        boolean isValid = false;
        int count = 0;
        while (!isValid ){
            count=0;
            dir = random.nextInt(2);
            startPos = random.nextInt(6);
            if(direction[dir] == "right")
                pos = pop2[startPos];
            else
                pos=startPos;
            this.allShip[1] = new Ship( pos, 2 , direction[dir] , size);
            for(int i = 0 ; i < 3 ; i++){
                for (int j = 0 ; j<2 ; j++){
                    if(allShip[0].getPosition()[i] == allShip[1].getPosition()[j]) {
                        isValid = false;
                        count += 1;
                    }
                }
            }
            if (count==0) {
                isValid = true;
            }
        }
        setTileToS(allShip[1]);


        if(num > 9){
            isValid = false;
            count = 0;
            while (!isValid ) {
                count = 0;
                dir = random.nextInt(2);
                startPos = random.nextInt(num);
                pos = startPos;
                for (int m = 0; m < 2; m++) {
                    for (int i = 0; i < allShip[m].getPosition().length; i++) {
                        if (allShip[m].getPosition()[i] == pos) {
                            isValid = false;
                            count += 1;
                        }
                    }
                }
                if (count == 0) {
                    isValid = true;
                }
            }
            this.allShip[2] = new Ship(pos, 1, "right", size);
            setTileToS(allShip[2]);

        }

    }



    public Ship[] getShips(){
        return allShip;
    }


    public int getBoardSize() {
        return mTiles.length;
    }

    public Tile getTile(int position) {
        return mTiles[position];
    }


    public void setTileToD(Ship ship[]){
        for (int k = 0 ; k < ship.length ; k++) {
            int count = 0;
            for (int i = 0; i < ship[k].getPosition().length; i++) {
                if (mTiles[ship[k].getPosition()[i]].getStatus() != TileState.O) {
                    count += 1;
                }
            }
            if (count == 0) {
                for (int i = 0; i < ship[k].getPosition().length; i++) {
                    mTiles[ship[k].getPosition()[i]].setStatus(TileState.D);
                }
                destroyedShips += 1;
            }
        }
    }


    public void setTileToS(Ship ship) {
        for ( int i = 0 ; i < ship.getPosition().length ; i++){
            mTiles[ship.getPosition()[i]].setStatus(TileState.S);
        }
    }




    public boolean setTile(int position){
        if(mTiles[position].getStatus() == TileState.NONE) {
            mTiles[position].setStatus(TileState.X);
            MaxPoints = MaxPoints - lostPoints;
            return true;
        }
        else if(mTiles[position].getStatus() == TileState.S) {
            mTiles[position].setStatus(TileState.O);
            return true;
        }

        return false;
    }

    public boolean boardWon(){
        if(destroyedShips == allShip.length)
            return true;
        return false;
    }

    public int getScore(){
        return MaxPoints;
    }

    public void setTileToNone(Ship ship) {
        for ( int i = 0 ; i < ship.getPosition().length ; i++){
            mTiles[ship.getPosition()[i]].setStatus(TileState.NONE);
        }
    }



    public void moveShips( int boardSize){
        boolean shipMove = false;

        Random random = new Random();

        int pop3 [];
        int pop2 [];
        int num ;
        if(boardSize == 3){
            pop3 = pos3_3;
            pop2 = pos2_3;
            num = 9;
        }
        else if ( boardSize == 4){
            pop3 = pos3_4;
            pop2 = pos2_4;
            num = 16;
        }
        else{
            pop3 = pos3_5;
            pop2 = pos2_5;
            num = 25;
        }
        if(!shipMove && mTiles[allShip[0].getPosition()[0]].getStatus() != TileState.D) {

            int dir = random.nextInt(2);
            int pos = 0;
            int startPos = random.nextInt(pop3.length);
            if (direction[dir] == "right")
                pos = pop3[startPos];
            else
                pos = startPos;
            Ship newShip = new Ship(pos, 3, direction[dir], size);
            if (cheakIfPossible(newShip)) {
                setTileToNone(allShip[0]);
                allShip[0] = newShip;
                setTileToS(allShip[0]);
                shipMove = true;
            }
        }
           if( !shipMove && mTiles[allShip[1].getPosition()[0]].getStatus() != TileState.D){
              int  dir = random.nextInt(2);
              int startPos = random.nextInt(pop2.length);
              int pos = 0;


                dir = random.nextInt(2);
                startPos = random.nextInt(6);
                if(direction[dir] == "right")
                    pos = pop2[startPos];
                else
                    pos=startPos;



            Ship newShip = new Ship( pos, 2 , direction[dir] , size);
            if(cheakIfPossible( newShip)){
                setTileToNone(allShip[1]);
                allShip[1] = newShip;
                setTileToS(allShip[1]);
                shipMove= true;
            }
        }

        if(!shipMove) {
            if (num > 9 && mTiles[allShip[2].getPosition()[0]].getStatus() != TileState.D) {
             int pos = 0;

             int dir = random.nextInt(2);
             int startPos = random.nextInt(num);
             pos = startPos;

             Ship newShip = new Ship(pos, 1, "right", size);
             if(cheakIfPossible( newShip)){
                 setTileToNone(allShip[2]);
                 allShip[2] = newShip;
                 setTileToS(allShip[2]);
                 shipMove = true;

                }
            }
        }

    }

    public boolean cheakIfPossible(Ship ship){

        for(int i = 0 ;  i< ship.getPosition().length ; i++) {
            if (mTiles[ship.getPosition()[i]].getStatus() == TileState.D)
                return false;
        }
            for(int i=0 ; i< ship.getPosition().length ; i++){
                for (int j = 0 ; j< allShip.length ; j++){
                    for (int m= 0 ; m< allShip[j].getPosition().length ; m++){
                        if(ship.getPosition()[i] == allShip[j].getPosition()[m]){
                            return false;
                        }
                    }
                }

        }
        return true;
    }

}

