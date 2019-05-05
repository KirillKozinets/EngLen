package com.example.englen.view.Fragments.TaskAnswer;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.englen.Data.DataBase.DataBaseHelper;
import com.example.englen.Data.DataBase.ReadFromDataBase;
import com.example.englen.Interface.PassedTheAnswer;
import com.example.englen.R;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TaskAnswerFragmentTest extends TaskAnswerFragment {
    private static final String ARG_IDTEST = "param1";
    PassedTheAnswer passedTheAnswer;
    @BindView(R.id.listWord)
    TextView listWord;
    Unbinder unbinder;
    private String DBName;
    private int ID = 0;
    private int[] randomNum;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putIntArray("randomNum", randomNum);
        outState.putInt("ID", ID);
        super.onSaveInstanceState(outState);
    }

    public static TaskAnswerFragmentTest newInstance() {
        TaskAnswerFragmentTest fragment = new TaskAnswerFragmentTest();
        return fragment;
    }

    // Генерация некоторого количества случайных , неповторяющихся чисел из определенного диапазона
    public static Set<Integer> generate(int max, int quantity) {
        Set<Integer> generated = new LinkedHashSet<>();
        Random r = new Random();
        while (generated.size() < quantity) {
            generated.add(r.nextInt(max));
        }
        return generated;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDBHelper = new DataBaseHelper(getActivity());
        if (getArguments() != null) {
            DBName = getArguments().getString(ARG_IDTEST);
        }
        if (savedInstanceState == null) {
            Set<Integer> result = generate(ReadFromDataBase.readCountRecord(mDBHelper, "BaseGrammary", "Name", DBName), 5);
            int i = 0;
            randomNum = new int[result.size()];
            for (Iterator<Integer> it = result.iterator(); it.hasNext(); i++) {
                Integer f = it.next();
                randomNum[i] = f;
            }
        }
    }

    @Override
    protected void Exit() {
        RadioSetActive(false);
        if (active == true) {
            active = false;
            super.BackToStartStation();
        } else {
            active = true;
        }
    }

    //Читает информацию из базы данных
    protected boolean ReadBD(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            try {
                Result = ReadFromDataBase.readSpecificAllRowFromBD(mDBHelper, randomNum[ID], "BaseGrammary", "Name", DBName);
                ID++;
            } catch (Exception ex) {
                ex.printStackTrace();
                passedTheAnswer.Exit();
                return false;
            }
        } else {
            // Считывает сохраненную информацию
            Result = savedInstanceState.getStringArray("Result");
            active = savedInstanceState.getBoolean("active");
            userAnsver = savedInstanceState.getInt("UserAnsver");
            randomNum = savedInstanceState.getIntArray("randomNum");
            ID = savedInstanceState.getInt("ID");
        }
        return true;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        passedTheAnswer = (PassedTheAnswer) getParentFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!ReadBD(savedInstanceState)) {
            return null;
        }

        View view = inflater.inflate(R.layout.fragment_task_answer, null);
        unbinder = ButterKnife.bind(this, view);
        for (int i = 0; i < answer.length; i++) { // Находит все RadioButton
            answer[i] = view.findViewById(listButtonID[i]);
            answer[i].setText(Result[i + 2]);
        }

        radioGroup = view.findViewById(R.id.radioGroup);
        table = view.findViewById(R.id.Table);
        next = view.findViewById(R.id.b6);
        qestion = view.findViewById(R.id.b7);
        exit = view.findViewById(R.id.b8);
        radioGroup.clearCheck();
        qestion.setText(Result[1]);
        trueAnswer = Integer.parseInt(Result[6]);
        listWord.setVisibility(View.GONE);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

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
                if (active == false)
                    TrueAndFalseAnswer();
                if (active == true)
                    if (userAnsver + 1 == trueAnswer)
                        passedTheAnswer.PassedTheAnswer(true, 20);
                    else
                        passedTheAnswer.PassedTheAnswer(false, 20);
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
        answer[userAnsver].setTextColor(Color.WHITE);

        table.setText("Правильный ответ - " + Result[trueAnswer + 1] + ", потому что " + Result[7]);
        table.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
