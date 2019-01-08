package com.example.englen.Layouts.Fragments.TaskAnswer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.englen.Data.DataBase.DataBaseHelper;
import com.example.englen.Data.DataBase.ReadFromDataBase;
import com.example.englen.Interface.LeanWord;
import com.example.englen.R;
import com.example.englen.utils.LearnWord;
import com.example.englen.utils.rememberWord;

public class TaskAnswerFragmentRememberWord extends TaskAnswerFragment {

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
            rememberWord.addNewRememberWord();
        }
    }

    //Читает информацию из базы данных
    protected boolean ReadBD(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mDBHelper = new DataBaseHelper(getActivity());
            try {
                if (LearnWord.getCurrentID() > rememberWord.getRememberWord())
                    Result = ReadFromDataBase.readDataFromBD(mDBHelper, rememberWord.getRememberWord(),"TaskAnswersList"); // Читает из бызы данных записи
            } catch (ArrayIndexOutOfBoundsException ex) {

                if (rememberWord.getRememberWord() != 1) {
                    rememberWord.resetRepeatedWords();
                    ReadBD(savedInstanceState);
                    return true;
                } else {
                    Toast toast = Toast.makeText(getContext(),
                            "Вы ещё не выучили ни одного слова",
                            Toast.LENGTH_SHORT);

                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
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
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        LeanWord cF = (LeanWord) getParentFragment();
        cF.LeanWord();

        return null;
    }

}
