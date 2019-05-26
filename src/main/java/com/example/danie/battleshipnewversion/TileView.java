package com.example.danie.battleshipnewversion;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.danie.battleshipnewversion.Logic.*;


public class TileView extends LinearLayout {

    TextView text;


    public TileView(Context context , int size , Board board) {
        super(context);

        this.setOrientation(VERTICAL);


        text = new TextView(context);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        text.setLayoutParams(layoutParams);
        text.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        text.setGravity(Gravity.CENTER_VERTICAL);
        if(size == 3)
            text.setTextSize(45);
        else if(size == 4)
            text.setTextSize(35);
        else
            text.setTextSize(20);

        text.setTextColor(0xFF353535);
        setBackgroundColor(0xFF4682B4);

        text.setVisibility(INVISIBLE);
        addView(text);

    }

    /*public void setBackgroundResource1(XmlResourceParser r){
        setBackgroundResource(r);
         AnimationDrawable am= (AnimationDrawable)getBackground();
        am.setOneShot(true);
        am.start();

    }*/
}

