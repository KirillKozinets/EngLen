package com.example.englen.Layouts.TaskAnswer;

import android.app.Activity;
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
import com.example.englen.utils.rememberWord;

import java.io.Console;
import java.util.Arrays;
import java.util.Timer;

public abstract class TaskAnswerFragment extends Fragment {

    protected DataBaseHelper mDBHelper;

    protected TextView qestion;
    protected RadioButton Answer[] = new RadioButton[4];
    protected int listButtonID[] = {R.id.b1, R.id.b2, R.id.b3, R.id.b4};
    protected TextView Table;
    protected RadioGroup radioGroup;

    protected chandgeTaskAnswer mListener;
    protected int TrueAnswer;
    protected  Button Next;
    protected String[] Result;
    protected int UserAnsver = -1;
    protected int i;
    protected String view;


    //Читает информацию из базы данных
    protected boolean ReadBD(Bundle savedInstanceState , int Type) {
        if (savedInstanceState == null) {
            mDBHelper = new DataBaseHelper(getActivity());
            try {
                ReadResult(Type);
                // С уровнем сложности A1
            } catch (Exception ex) {
                Toast toast = Toast.makeText(getContext(),
                        "Вы уже " + view + " все слова",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                LeanWord cF = (LeanWord) getParentFragment();
                cF.LeanWord(); // Закрывает текущий фрагмент и показывает информацию о уровне
                return false;
            }
        } else {
            ReadSaveInformations(savedInstanceState);
        }
        return true;
    }

    protected void ReadResult(int Type) throws Exception {
        if(Type == 1)
            Result = ReadTask.readTask(mDBHelper, 8, "A1", rememberWord.getRememberWord(), false); // Читает из бызы данных записи
        else if (Type == 2)
            Result = ReadTask.readTask(mDBHelper, 8, "A1", LearnWord.getCurrentID(), true); // Читает из бызы данных записи
        else
            throw new Exception();
    }

    protected void ReadSaveInformations(Bundle savedInstanceState)
    {
        // Считывает сохраненную информацию
        Result = savedInstanceState.getStringArray("Result");
        active = savedInstanceState.getBoolean("active");
        UserAnsver = savedInstanceState.getInt("UserAnsver");
    }


    // Сохраняет информацию
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        if (outState != null) {
            outState.putStringArray("Result", Result); // Сохраняет информацию из базыданных
            outState.putBoolean("active", active);// Сохраняет информацию о том нажал ли пользователь кнопку далее
            outState.putInt("UserAnsver", UserAnsver);// СОхраняет выбранный пользователем ответ
        }

        super.onSaveInstanceState(outState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_answer, null);

            for (i = 0; i < Answer.length; i++) { // Находит все RadioButton
                Answer[i] = view.findViewById(listButtonID[i]);
                Answer[i].setText(Result[i + 1]);
            }

            radioGroup = view.findViewById(R.id.radioGroup);
            radioGroup.clearCheck();

            Table = view.findViewById(R.id.Table);
            Next = view.findViewById(R.id.b6);
            qestion = view.findViewById(R.id.b7);

            qestion.setText(Result[0]);
            TrueAnswer = Integer.parseInt(Result[5]);

            // Вызывается при клики на один из RadioButton
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    Next.setEnabled(true);
                    Next.setBackgroundResource(R.drawable.nextbuttonstyle);
                    // Запоминаем ответ пользователя
                    UserAnsver = Arrays.binarySearch(listButtonID, checkedId);
                }
            });

            // Вызывается при нажатие на кнопку далее
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
                    RadioSetActive(false);
                }
            }
        return view;
    }

    // Выводит информацию о ответе
    protected void TrueAndFalseAnswer() {
        // Если ответ правильный цвет зелёный , неправильный - красный
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

    // Отмечает неправильный вариант
    protected void Falseanswer() {
        Answer[UserAnsver].setBackgroundResource(R.drawable.falseanser);
    }

    // Отмечает правильный вариант
    protected void Trueanswer() {
        Answer[UserAnsver].setBackgroundResource(R.drawable.trueanswer);
    }

    Boolean active = false;

    @NonNull
    // Отключает все RadioButton
    protected void RadioSetActive(boolean state) {
        for (int i = 0; i < Answer.length; i++)
            Answer[i].setClickable(state);
    }

    // Отвечает за смену фрагмента
    protected void Exit() {
        RadioSetActive(false);
        mListener = (chandgeTaskAnswer) getParentFragment();
    }



}
