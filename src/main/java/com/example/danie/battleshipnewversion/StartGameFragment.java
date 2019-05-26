package com.example.danie.battleshipnewversion;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import static com.example.danie.battleshipnewversion.MainActivity.sharedPreferences;


public class StartGameFragment extends Fragment {


    interface onButtonTouchedListener1 {

        void onButtonTouchedView();
    }
    private onButtonTouchedListener1 mListener;


    public StartGameFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_start_game, container, false);


        int num = sharedPreferences.getInt("DIFFICULTY" , 0);
        RadioButton re = view.findViewById(R.id.easy);
        RadioButton rm = view.findViewById(R.id.meduim);
        RadioButton rh = view.findViewById(R.id.hard);
        if(num != 0) {
            if(num == R.id.easy)
                re.setChecked(true);
            else if(num == R.id.meduim)
                rm.setChecked(true);
            else
                rh.setChecked(true);

        }


        final Button b = (Button)view.findViewById(R.id.table);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "BUTTON PRESSED");

                if(StartGameFragment.this.mListener != null) {
                    StartGameFragment.this.mListener.onButtonTouchedView();

                }
            }
        });
         return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

            try {
                onButtonTouchedListener1 listener = (onButtonTouchedListener1) context;

                if (listener != null)
                    this.registerListener(listener);
            } catch (ClassCastException e) {

                throw new ClassCastException(context.toString() + " must implement onButtonTouchedListener");

            }

        }

        public void registerListener(onButtonTouchedListener1 listener) {
            this.mListener = listener;
        }

    }




