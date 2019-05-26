package com.example.danie.battleshipnewversion;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;




/**
 * A simple {@link Fragment} subclass.
 */
public class HighScoreFragment extends Fragment {

private DatabaseHelper db;
    Button showAll , showM , showH;
    final String easyTableData = " Highscore Easy Level ";
    final String medTableData = " Highscore Medium Level ";
    final String hardTableData = " Highscore Hard Level ";


    public HighScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_bottom_table, container, false);
        db = new DatabaseHelper(getContext());
        showAll = view.findViewById(R.id.easyTable);
        showM= view.findViewById(R.id.medTable);
        showH =  view.findViewById(R.id.hardTable);
        ViewAllEasy();
        ViewAllMed();
        ViewAllHard();

        return view;

    }

    public void ViewAllEasy(){
        showAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = db.getAllDataEASY();
                        int count = 0;
                        if (res.getCount() == 0) {
                            showMas(easyTableData , "No Score Saved");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext() && count <10) {
                            buffer.append("Name: " + res.getString(1) + " ---> Score: " + res.getString(2) + "\n");
                            count++;
                        }
                        showMas(easyTableData , buffer.toString());


                    }
                }
        );

    }

    public void ViewAllMed(){
        showM.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = db.getAllDataMED();
                        int count = 0;
                        if (res.getCount() == 0) {
                            showMas(medTableData, "No Score Saved");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext() && count <10) {
                            buffer.append("  Name: " + res.getString(1) + " ---> Score:  "+ res.getString(2) + "\n");
                            count++;
                        }
                        showMas(medTableData , buffer.toString());


                    }
                }
        );

    }

    public void ViewAllHard(){
        showH.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = db.getAllDataHARD();
                        int count = 0;
                        if (res.getCount() == 0) {
                            showMas(hardTableData, "No Score Saved");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext() && count <10) {
                            buffer.append(" Name: "+ res.getString(1) + " ---> Score: " + res.getString(2) + "\n");
                            count++;
                        }
                        showMas(hardTableData , buffer.toString());

                    }
                }
        );

    }
    public void showMas(String title , String Mes){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Mes);
        builder.show();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }



}
