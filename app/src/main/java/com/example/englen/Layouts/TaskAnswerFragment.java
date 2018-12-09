package com.example.englen.Layouts;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.englen.Data.DataBase.DataBaseHelper;
import com.example.englen.Data.DataBase.ReadTask;
import com.example.englen.Interface.LeanWord;
import com.example.englen.Interface.chandgeFragment;
import com.example.englen.Interface.chandgeTaskAnswer;
import com.example.englen.R;
import com.example.englen.utils.LearnWord;

import java.io.Console;
import java.util.Arrays;
import java.util.Timer;

public class TaskAnswerFragment extends Fragment {

    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    TextView qestion;
    boolean Active;
    RadioButton Answer[] = new RadioButton[4];
    int listButtonID[] = {R.id.b1, R.id.b2, R.id.b3, R.id.b4};
    TextView Table;

    private chandgeTaskAnswer mListener ;

    int TrueAnswer;
    Button Next;
    public String[] Result;
    int UserAnsver = -1;
    int i;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putStringArray("Result", Result);
        outState.putBoolean("active", active);
        outState.putInt("UserAnsver", UserAnsver);

        super.onSaveInstanceState(outState);
    }

    private boolean ReadBD(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mDBHelper = new DataBaseHelper(getActivity());
            try {
                Result = ReadTask.readTask(mDBHelper, 8, "A1");
            }catch (Exception ex){
                Toast toast = Toast.makeText(getContext(),
                        "Вы уже выучили все слова",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                LeanWord cF = (LeanWord)getParentFragment();
                cF.LeanWord();
                return false;
            }
        } else {
            Result = savedInstanceState.getStringArray("Result");
            active = savedInstanceState.getBoolean("active");
            UserAnsver = savedInstanceState.getInt("UserAnsver");
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_answer, null);

       if(ReadBD(savedInstanceState)) {

           for (i = 0; i < Answer.length; i++) {
               Answer[i] = view.findViewById(listButtonID[i]);
               Answer[i].setText(Result[i + 1]);
           }

           RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
           radioGroup.clearCheck();

           Table = (TextView) view.findViewById(R.id.Table);
           Next = (Button) view.findViewById(R.id.b6);
           qestion = (TextView) view.findViewById(R.id.b7);

           qestion.setText(Result[0]);
           TrueAnswer = Integer.parseInt(Result[5]);

           radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(RadioGroup group, int checkedId) {
                   Next.setEnabled(true);
                   Next.setBackgroundResource(R.drawable.nextbuttonstyle);
                   Active = true;
                   UserAnsver = Arrays.binarySearch(listButtonID, checkedId);
               }
           });

           Next.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   TrueAndFalseAnswer();
                   Exit();
               }
           });

           if (savedInstanceState != null) {
               if (UserAnsver != -1)
                   Next.setEnabled(true);
               if (active == true) {
                   TrueAndFalseAnswer();
                   RadioFalseActive();
               }
           }
       }
        return view;
    }

    private void TrueAndFalseAnswer() {
        if (UserAnsver + 1 == TrueAnswer) {
            Trueanswer();
            Table.setBackgroundResource(R.drawable.trueanswer);
        } else {
            Falseanswer();
            Table.setBackgroundResource(R.drawable.falseanser);
        }
        Table.setText(Result[7] + " переводится как " + Result[TrueAnswer] + "\n" + "Нажмите <Далее> чтобы продолжить");
        Table.setVisibility(View.VISIBLE);
    }

    private void Falseanswer() {
        Answer[UserAnsver].setBackgroundResource(R.drawable.falseanser);
    }

    void Trueanswer() {
        Answer[UserAnsver].setBackgroundResource(R.drawable.trueanswer);
    }

    Boolean active = false;

    private void RadioFalseActive() {
        for (int i = 0; i < Answer.length; i++)
            Answer[i].setClickable(false);
    }

    private void Exit() {
        RadioFalseActive();
        mListener =(chandgeTaskAnswer) getParentFragment();
        if (active == true) {
            mListener.onCloseFragment();
            LearnWord.addNewWord();
        }
        else
            active = true;
    }

}
