package com.example.englen.Layouts.Fragments.TaskAnswer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.englen.Data.DataBase.DataBaseHelper;
import com.example.englen.Data.DataBase.ReadFromDataBase;
import com.example.englen.Interface.PassedTheAnswer;
import com.example.englen.R;

import java.util.Arrays;

public class TaskAnswerFragmentTest extends TaskAnswerFragment {
    private static final String ARG_IDTEST = "param1";
    PassedTheAnswer passedTheAnswer;
    private String DBName;
    private int ID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            DBName = getArguments().getString(ARG_IDTEST);
        }
    }

    @Override
    protected void Exit() {
        RadioSetActive(false);
        if (active == true) {
            active = false;
            backToStartStation();
        } else {
            active = true;
        }
    }

    //Читает информацию из базы данных
    protected boolean ReadBD(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mDBHelper = new DataBaseHelper(getActivity());
            try {
                final String[] ArraysResult = new String[3];
                DataBaseHelper helper = new DataBaseHelper(getActivity().getApplicationContext());
                ReadFromDataBase.updataDataBase(helper);// Обновляем базу данных
                SQLiteDatabase mDb = helper.getWritableDatabase();// Читаем базу данных
                Cursor cursor = mDb.rawQuery("SELECT * FROM " + DBName, null); // Читаем из базы данных определенные записи
                cursor.move(ID);
                if (ID >= cursor.getCount())
                    throw new Exception();
                ID++;

                Result = new String[9];
                if (cursor.moveToFirst()) {
                    for (int q = 0; q < cursor.getColumnCount() - 1; q++) {
                        Result[q] = cursor.getString(q + 1);
                    }
                }

                // С уровнем сложности A1
            } catch (Exception ex) {
                passedTheAnswer.Exit();
                return false;
            }
        } else {
            // Считывает сохраненную информацию
            Result = savedInstanceState.getStringArray("Result");
            active = savedInstanceState.getBoolean("active");
            userAnsver = savedInstanceState.getInt("UserAnsver");
        }
        return true;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        passedTheAnswer = (PassedTheAnswer) getParentFragment();
    }

    protected void backToStartStation() {
        ReadBD(null);
        active = false;
        RadioSetActive(true);

        super.FillAnswer();

        answer[userAnsver].setBackgroundResource(R.drawable.radiobuttonstyle);
        radioGroup.clearCheck();

        next.setEnabled(false);
        next.setBackgroundResource(R.drawable.nextbuttonoactive);
        qestion.setText(Result[0]);
        trueAnswer = Integer.parseInt(Result[5]);

        table.setVisibility(View.GONE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!ReadBD(savedInstanceState)) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_task_answer, null);

        for (int i = 0; i < answer.length; i++) { // Находит все RadioButton
            answer[i] = view.findViewById(listButtonID[i]);
            answer[i].setText(Result[i + 1]);
        }

        radioGroup = view.findViewById(R.id.radioGroup);
        table = view.findViewById(R.id.Table);
        next = view.findViewById(R.id.b6);
        qestion = view.findViewById(R.id.b7);

        radioGroup.clearCheck();
        qestion.setText(Result[1]);
        trueAnswer = Integer.parseInt(Result[5]);

        // Вызывается при клики на один из RadioButton
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                next.setEnabled(true);
                next.setBackgroundResource(R.drawable.nextbuttonstyle);
                // Запоминаем ответ пользователя
                userAnsver = Arrays.binarySearch(listButtonID, checkedId);
            }
        });

        // Вызывается при нажатие на кнопку далее
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(active == false)
                TrueAndFalseAnswer();
                Exit();
            }
        });

        if (savedInstanceState != null) {
            if (userAnsver != -1)
                next.setEnabled(true);
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
        boolean TrueAnswer;
        if (userAnsver + 1 == trueAnswer) {
            Trueanswer();
            table.setBackgroundResource(R.drawable.trueanswer);
            TrueAnswer = true;
        } else {
            Falseanswer();
            table.setBackgroundResource(R.drawable.falseanser);
            TrueAnswer = false;
        }

        passedTheAnswer.PassedTheAnswer(TrueAnswer);

        table.setText(Result[7] + " переводится как " + Result[trueAnswer] + "\n" + "Нажмите <Далее> чтобы продолжить");
        table.setVisibility(View.VISIBLE);
    }
}
