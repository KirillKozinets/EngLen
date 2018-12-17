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
import com.example.englen.utils.LearnWord;
import com.example.englen.utils.rememberWord;

public class TaskAnswerFragmentRememberWord extends TaskAnswerFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = "повторили";
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (ReadBD(savedInstanceState,1)) {
            return super.onCreateView(inflater,container,savedInstanceState);
        }
        return null;
    }

    protected void backToStartStation() {
        ReadBD(null,1);
        active = false;
        RadioSetActive(true);

        for (i = 0; i < Answer.length; i++) {
            Answer[i].setText(Result[i + 1]);
        }

        Answer[UserAnsver].setBackgroundResource(R.drawable.radiobuttonstyle);
        radioGroup.clearCheck();

        Next.setEnabled(false);
        Next.setBackgroundResource(R.drawable.nextbuttonoactive);
        qestion.setText(Result[0]);
        TrueAnswer = Integer.parseInt(Result[5]);

        Table.setVisibility(View.GONE);
    }
}
