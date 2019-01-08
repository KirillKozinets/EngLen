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

public class TaskAnswerFragmentNewWord extends TaskAnswerFragment {

    protected String view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = "выучили";
    }

    @Override
    protected void Exit() {
        super.Exit();
        if (active == true) {
            active = false;
            BackToStartStation();
        } else {
            active = true;
            mListener.LearnNewWord();
            LearnWord.addNewWord();
        }
    }

    //Читает информацию из базы данных
    protected boolean ReadBD(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mDBHelper = new DataBaseHelper(getActivity());
            try {
                Result = ReadFromDataBase.readDataFromBD(mDBHelper, LearnWord.getCurrentID(),"TaskAnswersList"); // Читает из бызы данных записи
                // С уровнем сложности A1
            } catch (ArrayIndexOutOfBoundsException ex) {
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
        return null;
    }
}
