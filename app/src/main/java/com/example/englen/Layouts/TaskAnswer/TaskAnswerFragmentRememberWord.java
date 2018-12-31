package com.example.englen.Layouts.TaskAnswer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.englen.Data.DataBase.DataBaseHelper;
import com.example.englen.Data.DataBase.ReadTask;
import com.example.englen.Interface.LeanWord;
import com.example.englen.R;
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
            backToStartStation();
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
                Result = ReadTask.readTask(mDBHelper, 8, "A1", rememberWord.getRememberWord(), false); // Читает из бызы данных записи
                // С уровнем сложности A1
            } catch (Exception ex) {
                Toast toast;

                if (rememberWord.getRememberWord() != 1) {
                    rememberWord.resetRepeatedWords();
                    ReadBD(savedInstanceState);
                    return true;
                } else {
                    toast = Toast.makeText(getContext(),
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

    protected void backToStartStation() {
        ReadBD(null);
        active = false;
        RadioSetActive(true);

        FillAnswer();

        answer[userAnsver].setBackgroundResource(R.drawable.radiobuttonstyle);
        radioGroup.clearCheck();

        next.setEnabled(false);
        next.setBackgroundResource(R.drawable.nextbuttonoactive);
        qestion.setText(Result[0]);
        trueAnswer = Integer.parseInt(Result[5]);

        table.setVisibility(View.GONE);
    }
}
