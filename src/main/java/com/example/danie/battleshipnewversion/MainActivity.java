package com.example.danie.battleshipnewversion;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.danie.battleshipnewversion.Logic.*;




public class MainActivity extends AppCompatActivity implements StartGameFragment.onButtonTouchedListener1 ,
        ServiceConnection , ServiceCallbacks {


    private LocalService s;

    DatabaseHelper db;
    EditText name;
    TextView score;
    Button saveName;
    private boolean checkIfSave;

    static SharedPreferences sharedPreferences;
    Game mGame;
    private GridView mGrid1;
    private GridView mGrid2;
    final int easyLevel = 3;
    final int medLevel = 4;
    final int hardLevel = 5;
    public static int boardSize;
    private int counter, total;
    public static boolean startPlay = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("com.example.danie.battleshipnewversion" , MODE_PRIVATE);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.thefragment , new StartGameFragment());
        ft.commit();


        Intent intent = new Intent(getApplicationContext(), LocalService.class);
        getApplicationContext().startService(intent);

        }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent= new Intent(this, LocalService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        LocalService.MyBinder b = (LocalService.MyBinder) binder;
        s = b.getService();
        Toast.makeText(MainActivity.this, R.string.Service_Connected, Toast.LENGTH_SHORT).show();
        s.setCallbacks(MainActivity.this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        s = null;
    }




    public void startGame(View view) {

        RadioGroup gameLevel = (RadioGroup) (findViewById(R.id.radioGroup));

        int level = gameLevel.getCheckedRadioButtonId();
        switch (level) {
            case R.id.easy:
                boardSize = easyLevel;
                break;
            case R.id.meduim:
                boardSize = medLevel;
                break;
            case R.id.hard:
                boardSize = hardLevel;
                break;
        }


        sharedPreferences.edit().putInt("DIFFICULTY" , level).apply();



        Thread t;
        setContentView(R.layout.activity_game);
        startPlay =true;

        ((ProgressBar) findViewById(R.id.progressBar)).setVisibility(View.INVISIBLE);
        mGame = new Game(boardSize);
        mGrid1 = (GridView) findViewById(R.id.grid1);
        mGrid2 = (GridView) findViewById(R.id.grid2);
        mGrid1.setNumColumns(boardSize);
        mGrid2.setNumColumns(boardSize);




        mGrid1.setAdapter(new TileAdapter(getApplicationContext(), mGame.getmBoardPlayer(), boardSize));
        mGrid2.setAdapter(new TileAdapter(getApplicationContext(), mGame.getmBoardComputer(), boardSize));
        mGrid2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Thread t;
            boolean flag = false;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                flag = mGame.playTile(position);
                ((TileAdapter) mGrid2.getAdapter()).notifyDataSetChanged();
                if (mGame.gameWon(mGame.getmBoardComputer())) {
                     setContentView(R.layout.activity_playerwin);
                     checkIfSave = false;
                    TextViewAnimation("" + mGame.getmBoardPlayer().getScore());
                    saveScore();
                    startPlay =false;
                } else {
                    if (flag) {
                        ((TextView) findViewById(R.id.whoTurn)).setText(R.string.computer_Turn);
                        ((ProgressBar) findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);
                        t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mGame.playComputer();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), R.string.ComputerFinihsedhisturn, Toast.LENGTH_SHORT).show();
                                        ((TileAdapter) mGrid1.getAdapter()).notifyDataSetChanged();
                                        if (mGame.gameWon(mGame.getmBoardPlayer())) {
                                            setContentView(R.layout.activity_computerwin);
                                            startPlay =false;
                                        }
                                        else {
                                            ((TextView) findViewById(R.id.whoTurn)).setText(R.string.player_turn);
                                            ((ProgressBar) findViewById(R.id.progressBar)).setVisibility(View.INVISIBLE);
                                        }
                                    }
                                });
                            }

                        });

                        t.start();
                    }
                }

            }
        });


    }



    public void restartGame(View view) {
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.thefragment , new StartGameFragment());
        ft.commit();

    }


    public void saveScore(){
        db= new DatabaseHelper(this);
        name = (EditText)findViewById(R.id.editText_name);
        score = (TextView)findViewById(R.id.score);
        saveName = (Button)findViewById(R.id.saveScore);
        SaveName();

    }


    public void SaveName(){
        saveName.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = false;
                        boolean flag = false;
                        String name = MainActivity.this.name.getText().toString();
                        while (checkIfSave == false && !name.equals("")) {
                            if (boardSize == easyLevel) {
                                isInserted = db.addScoreEASY(name, score.getText().toString());
                            } else if (boardSize == medLevel)
                                isInserted = db.addScoreMED(name, score.getText().toString());
                            else
                                isInserted = db.addScoreHARD(name, score.getText().toString());
                            checkIfSave = true;
                            flag = true;

                            if (isInserted == true  )
                                Toast.makeText(MainActivity.this, R.string.Data_Inserted, Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(MainActivity.this, R.string.Data_not_Inserted, Toast.LENGTH_SHORT).show();


                        }
                        if(name.equals("")){
                            Toast.makeText(MainActivity.this, R.string.cantsave, Toast.LENGTH_SHORT).show();
                        }

                        if(checkIfSave && !flag)
                            Toast.makeText(MainActivity.this, R.string.Score_already_saved, Toast.LENGTH_SHORT).show();

                    }


                }
        );
    }




    @Override
    public void onButtonTouchedView() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.thefragment ,new HighScoreFragment() );
        ft.addToBackStack("back");
        ft.commit();

    }

    private void TextViewAnimation(String finalScore ){
         counter = 0;
         total = Integer.parseInt(finalScore);
        score = (TextView)findViewById(R.id.score);
        new Thread(new Runnable() {
            public void run() {
                while (counter < total){
                    try {
                        Thread.sleep(5);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    score.post(new Runnable() {
                        public void run() {
                            score.setText(""+ counter);
                        }
                    });
                    counter++;
                }

            }
        }).start();
    }

    @Override
    public void moveTheShips() {
        mGame.getmBoardComputer().moveShips(boardSize);
        ((TileAdapter) mGrid2.getAdapter()).notifyDataSetChanged();
        ((TextView) findViewById(R.id.replaceShip)).setVisibility(View.VISIBLE);

    }

    @Override
    public void stopMoveShips() {
        ((TextView) findViewById(R.id.replaceShip)).setVisibility(View.INVISIBLE);
    }


}
