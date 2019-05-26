package com.example.danie.battleshipnewversion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;




public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DATABASE_NAME = "scores";


    // high scores table
    private static final String TABLE_HIGHSCORES_EASY = "highscores";
    private static final String TABLE_HIGHSCORES_MED = "highscores_med";
    private static final String TABLE_HIGHSCORES_HARD = "highscores_hard";
    // high scores columns
    private static final String KEY_TARGET_ID = "ID";
    private static final String KEY_HIGHSCORES_SCORE = "score";
    private static final String KEY_HIGHSCORES_NAME = "name";





    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createHighscoresEasy(db);
        createHighscoresMed(db);
        createHighscoresHard(db);

    }

    public void createHighscoresEasy(SQLiteDatabase db){
        String CREATE_HIGHSCORES_TABLE = "CREATE TABLE " + TABLE_HIGHSCORES_EASY + "(" + KEY_TARGET_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + KEY_HIGHSCORES_NAME + " TEXT," + KEY_HIGHSCORES_SCORE + " TEXT " + ")";
        db.execSQL(CREATE_HIGHSCORES_TABLE);
        Log.d("created highscores ", "highscores");
    }

    public void createHighscoresMed(SQLiteDatabase db){
        String CREATE_HIGHSCORES_TABLE_M = "CREATE TABLE " + TABLE_HIGHSCORES_MED + "(" + KEY_TARGET_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_HIGHSCORES_NAME + " TEXT," + KEY_HIGHSCORES_SCORE + " TEXT " + ")";
        db.execSQL(CREATE_HIGHSCORES_TABLE_M);
        Log.d("created highscores ", "highscores");
    }

    public void createHighscoresHard(SQLiteDatabase db){
        String CREATE_HIGHSCORES_TABLE_H = "CREATE TABLE " + TABLE_HIGHSCORES_HARD + "(" + KEY_TARGET_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_HIGHSCORES_NAME + " TEXT," + KEY_HIGHSCORES_SCORE + " TEXT " + ")";
        db.execSQL(CREATE_HIGHSCORES_TABLE_H);
        Log.d("created highscores ", "highscores");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        wipeDb();
        // Create tables again
        onCreate(db);

    }

    // cleans the Db
    public void wipeDb(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORES_EASY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORES_MED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORES_HARD);


    }

    public boolean addScoreEASY(String name , String score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_HIGHSCORES_NAME, name);
        values.put(KEY_HIGHSCORES_SCORE, score);

       long result =  db.insert(TABLE_HIGHSCORES_EASY, null, values);
        if(result  == -1){
            return false;
        }

        return true;


    }

    public boolean addScoreMED(String name , String score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_HIGHSCORES_NAME, name);
        values.put(KEY_HIGHSCORES_SCORE, score);

        long result =  db.insert(TABLE_HIGHSCORES_MED, null, values);
        if(result  == -1){
            return false;
        }

        return true;


    }

    public boolean addScoreHARD(String name , String score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_HIGHSCORES_NAME, name);
        values.put(KEY_HIGHSCORES_SCORE, score);

        long result =  db.insert(TABLE_HIGHSCORES_HARD, null, values);
        if(result  == -1){
            return false;
        }

        return true;


    }

    public Cursor getAllDataEASY(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_HIGHSCORES_EASY +
                " ORDER BY " + KEY_HIGHSCORES_SCORE + " DESC ", null);
        return res;

    }

    public Cursor getAllDataMED(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_HIGHSCORES_MED +
                 " ORDER BY " + KEY_HIGHSCORES_SCORE + " DESC ", null);
        return res;

    }

    public Cursor getAllDataHARD(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_HIGHSCORES_HARD +
                " ORDER BY " + KEY_HIGHSCORES_SCORE + " DESC ", null);
        return res;

    }


}
