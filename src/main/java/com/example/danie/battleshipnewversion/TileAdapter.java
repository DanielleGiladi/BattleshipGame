package com.example.danie.battleshipnewversion;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;


import com.example.danie.battleshipnewversion.Logic.*;

public class TileAdapter extends  BaseAdapter {


    private Context mContext;
    private Board mBoard;
    private int boardSize;
    AnimationDrawable animationDrawable;


    public TileAdapter(Context context,Board board , int size) {
        mBoard = board;
        mContext = context;
        boardSize = size;


    }

    @Override
    public int getCount() {
        return mBoard.getBoardSize();
    }

    @Override
    public Object getItem(int position) {
        return mBoard.getTile(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {
        TileView tileView;
        if (convertView == null) {
            Log.e("Tile Adapter", "Not RECYCLED");
            tileView = new TileView(mContext , boardSize , mBoard);
            if(boardSize==3){
                tileView.setLayoutParams(new GridView.LayoutParams(340, 200));///
            }
            else if(boardSize==4){
                tileView.setLayoutParams(new GridView.LayoutParams(250,160));///
            }
            else{
                tileView.setLayoutParams(new GridView.LayoutParams(200, 120));///
            }

            tileView.setPadding(8, 8, 8, 8);
        } else {
            tileView = (TileView) convertView;
            Log.e("Tile Adapter", "RECYCLED-- YAY!!!!!");

        }

        tileView.text.setText(mBoard.getTile(position).getStatus().toString());
        AnimationDrawable am;


        if (mBoard.getTile(position).getStatus() == Board.TileState.S && mBoard.getType().equals("player") ){
           tileView.setBackground(mContext.getResources().getDrawable(R.drawable.ship));
        }


        else if(mBoard.getTile(position).getStatus() == Board.TileState.O){
            tileView.setBackgroundResource(R.drawable.boomanimation);
             animationDrawable = (AnimationDrawable) tileView.getBackground();
             animationDrawable.start();

        }

        else if(mBoard.getTile(position).getStatus() == Board.TileState.X){
            tileView.setBackgroundResource(R.drawable.missanimation);
            animationDrawable = (AnimationDrawable) tileView.getBackground();
            animationDrawable.start();
         //   tileView.setBackground(mContext.getResources().getDrawable(R.drawable.ship));

        }

        else if(mBoard.getTile(position).getStatus() == Board.TileState.D){
            tileView.setBackgroundResource(R.drawable.shipdownanimation);
            animationDrawable = (AnimationDrawable) tileView.getBackground();
            animationDrawable.start();

        }

        else if(mBoard.getTile(position).getStatus() == Board.TileState.NONE){
            tileView. setBackgroundColor(0xFF4682B4);



        }

        return tileView;
    }
}
