package com.example.englen;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.englen.Data.DataBaseHelper;
import com.example.englen.Data.ReadTask;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TaskAnswerFragment extends Fragment {

    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    TextView qestion;

    boolean Active;
    Button Answer1;
    Button Answer2;
    Button Answer3;
    Button Answer4;

    TextView TextAnswer;

    private OnFragment1DataListener mListener;

    int TrueAnswer;
    Button Next;
    Button Back;
    String[] Result;
    Timer timer;
    int UserAnsver = -1;


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putStringArray("Result", Result);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_answer, null);
        Answer1 = (Button) view.findViewById(R.id.b1);
        Answer2 = (Button) view.findViewById(R.id.b2);
        Answer3 = (Button) view.findViewById(R.id.b3);
        Answer4 = (Button) view.findViewById(R.id.b4);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        TextAnswer = (TextView) view.findViewById(R.id.TextAnswer);
        Next = (Button) view.findViewById(R.id.b6);
        qestion = (TextView) view.findViewById(R.id.b7);

        if (savedInstanceState == null) {
            mDBHelper = new DataBaseHelper(getActivity());
            Result = ReadTask.readTask(mDBHelper, 6, "A1");
        } else
            Result = savedInstanceState.getStringArray("Result");

        qestion.setText(Result[0]);
        Answer1.setText(Result[1]);
        Answer2.setText(Result[2]);

        Answer3.setText(Result[3]);
        Answer4.setText(Result[4]);

        TrueAnswer = Integer.parseInt(Result[5]);

        Answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAnsver = 1;
            }
        });
        Answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAnsver = 2;
            }
        });
        Answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAnsver = 3;
            }
        });
        Answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAnsver = 4;
            }
        });


        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Next.setEnabled(true);
                Next.setBackgroundResource(R.drawable.nextbuttonstyle);
                Active = true;
                switch (checkedId) {
                    case R.id.b1:
                        UserAnsver = 1;
                        break;
                    case R.id.b2:
                        UserAnsver = 2;
                        break;
                    case R.id.b3:
                        UserAnsver = 3;
                        break;
                    case R.id.b4:
                        UserAnsver = 4;
                        break;
                }
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrueAndFalseAnswer();
                Exit();
            }
        });

        return view;
    }

    private void TrueAndFalseAnswer() {
        if (UserAnsver == TrueAnswer) {
            Trueanswer();
        } else {
            Falseanswer();
        }
    }

    private void Falseanswer() {
        switch (UserAnsver) {
            case 1:
                Answer1.setBackgroundResource(R.drawable.falseanser);
                break;
            case 2:
                Answer2.setBackgroundResource(R.drawable.falseanser);
                break;
            case 3:
                Answer3.setBackgroundResource(R.drawable.falseanser);
                break;
            case 4:
                Answer4.setBackgroundResource(R.drawable.falseanser);
                break;
        }
    }

    void Trueanswer() {
        switch (TrueAnswer) {
            case 1:
                Answer1.setBackgroundResource(R.drawable.trueanswer);
                break;
            case 2:
                Answer2.setBackgroundResource(R.drawable.trueanswer);
                break;
            case 3:
                Answer3.setBackgroundResource(R.drawable.trueanswer);
                break;
            case 4:
                Answer4.setBackgroundResource(R.drawable.trueanswer);
                break;
        }
    }

Boolean active = false;

    private void Exit() {
        Answer1.setClickable(false);
        Answer2.setClickable(false);
        Answer3.setClickable(false);
        Answer4.setClickable(false);

       if(active == true)
           mListener.onCloseFragment();
       else
           active = true;


    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment1DataListener) {
            mListener = (OnFragment1DataListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragment1DataListener");
        }
    }

}
