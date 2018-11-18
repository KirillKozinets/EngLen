package com.example.englen;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
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

    Button Next;
    Button Back;

    int UserAnsver;

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

        mDBHelper = new DataBaseHelper(getActivity());

        String[] Result = ReadTask.readTask(mDBHelper,6,"A1");

        qestion.setText(Result[0]);
        Answer1.setText(Result[1]);
        Answer2.setText(Result[2]);

        Answer3.setText(Result[3]);
        Answer4.setText(Result[4]);

        final int TrueAnswer = Integer.parseInt(Result[5]);

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
                if (UserAnsver == TrueAnswer && Active == true) {
                    TextAnswer.setBackgroundResource(R.drawable.trueanswer);
                    TextAnswer.setText("Правильно!");
                    TextAnswer.setVisibility(View.VISIBLE);
                } else if(Active == true){
                    TextAnswer.setBackgroundResource(R.drawable.falseanser);
                    TextAnswer.setText("Не правильно!");
                    TextAnswer.setVisibility(View.VISIBLE);
                }
                if(Active == true)
                {
                    Answer1.setEnabled(false);
                    Answer2.setEnabled(false);
                    Answer3.setEnabled(false);
                    Answer4.setEnabled(false);
                }

            }
        });



        return view;
        }


    }
