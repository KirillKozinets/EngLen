package com.example.englen.view.Fragments.TaskAnswer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englen.Data.DataBase.DataBaseHelper;
import com.example.englen.Data.DataBase.ReadFromDataBase;
import com.example.englen.Interface.LeanWord;
import com.example.englen.R;
import com.example.englen.utils.Fabric;
import com.example.englen.utils.LearnWord;

import java.util.Random;

public class TaskAnswerFragmentRememberWord extends TaskAnswerFragment {

    Random random = new Random();
    int rand;
    private TextView text;
    private int rememberWord = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void Exit() {
        super.Exit();
        if (active == true) {
            active = false;
            super.BackToStartStation();
        } else {
            active = true;
            mListener.RememberNewWord();
        }
    }

    //Читает информацию из базы данных
    protected boolean ReadBD(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            if (text != null) {
                rememberWord++;
                text.setVisibility(View.VISIBLE);
                text.setText(rememberWord + " / 10");
            }
            if (rememberWord >= 10) {
                LeanWord cF = (LeanWord) getParentFragment();
                cF.LeanWord(); // Закрывает текущий фрагмент и показывает информацию о уровне
            }
            mDBHelper = new DataBaseHelper(getActivity());
            try {
                if (LearnWord.getCurrentID() > 10) {
                    rand = random.nextInt(LearnWord.getCurrentID());
                    Result = ReadFromDataBase.readDataFromBD(mDBHelper, rand, "TaskAnswersList"); // Читает из бызы данных записи
                } else
                    throw new ArrayIndexOutOfBoundsException();
            } catch (Exception ex) {
                Toast toast = Toast.makeText(getContext(),
                        "Нужно выучить больше слов",
                        Toast.LENGTH_SHORT);

                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (ReadBD(savedInstanceState)) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            text = view.findViewById(R.id.listWord);
            text.setVisibility(View.VISIBLE);
            text.setText(rememberWord + " / 10");
            return view;

        }
        LeanWord cF = (LeanWord) getParentFragment();
        cF.LeanWord();

        return null;
    }

    @Override
    protected void TrueAndFalseAnswer() {
        super.TrueAndFalseAnswer();
        int id = rand;
        if (userAnsver + 1 == trueAnswer) {
            Fabric.enterLevel("RememberWord_" + id, 100, true);
        } else
            Fabric.enterLevel("RememberWord_" + id, 0, false);
    }

}
