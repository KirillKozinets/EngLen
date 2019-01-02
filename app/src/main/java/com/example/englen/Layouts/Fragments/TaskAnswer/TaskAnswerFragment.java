package com.example.englen.Layouts.Fragments.TaskAnswer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.englen.utils.AnalyticsApplication;
import com.example.englen.Data.DataBase.DataBaseHelper;
import com.example.englen.Interface.ChandgeTaskAnswer;
import com.example.englen.R;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Arrays;

public abstract class TaskAnswerFragment extends Fragment {

    private Tracker mTracker;

    protected DataBaseHelper mDBHelper;

    protected TextView qestion;
    protected RadioButton answer[] = new RadioButton[4];
    protected TextView table;
    protected RadioGroup radioGroup;
    protected Button next;

    protected int listButtonID[] = {R.id.b1, R.id.b2, R.id.b3, R.id.b4};
    protected ChandgeTaskAnswer mListener;
    protected int trueAnswer;
    protected String[] Result;
    protected int userAnsver = -1;


    @Override
    public void onResume() {
        super.onResume();
        mTracker = ((AnalyticsApplication) getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName(this.getClass().getCanonicalName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    // Сохраняет информацию
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        if (outState != null) {
            outState.putStringArray("Result", Result); // Сохраняет информацию из базыданных
            outState.putBoolean("active", active);// Сохраняет информацию о том нажал ли пользователь кнопку далее
            outState.putInt("UserAnsver", userAnsver);// СОхраняет выбранный пользователем ответ
        }

        super.onSaveInstanceState(outState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        qestion.setText(Result[0]);
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
        if (userAnsver + 1 == trueAnswer) {
            Trueanswer();
            table.setBackgroundResource(R.drawable.trueanswer);
        } else {
            Falseanswer();
            table.setBackgroundResource(R.drawable.falseanser);
        }
        table.setText(Result[7] + " переводится как " + Result[trueAnswer] + "\n" + "Нажмите <Далее> чтобы продолжить");
        table.setVisibility(View.VISIBLE);
    }

    // Отмечает неправильный вариант
    protected void Falseanswer() {
        answer[userAnsver].setBackgroundResource(R.drawable.falseanser);
    }

    // Отмечает правильный вариант
    protected void Trueanswer() {
        answer[userAnsver].setBackgroundResource(R.drawable.trueanswer);
    }

    Boolean active = false;

    @NonNull
    // Отключает все RadioButton
    protected void RadioSetActive(boolean state) {
        for (int i = 0; i < answer.length; i++)
            answer[i].setClickable(state);
    }

    // Отвечает за смену фрагмента
    protected void Exit() {
        RadioSetActive(false);
        mListener = (ChandgeTaskAnswer) getParentFragment();
    }

    protected void FillAnswer() {
        for (int i = 0; i < answer.length; i++) {
            answer[i].setText(Result[i + 1]);
        }
    }


}
