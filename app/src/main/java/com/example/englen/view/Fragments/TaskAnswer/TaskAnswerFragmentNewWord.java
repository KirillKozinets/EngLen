package com.example.englen.view.Fragments.TaskAnswer;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class TaskAnswerFragmentNewWord extends TaskAnswerFragment {

    protected String view;
    private TextView text;
    private int learnWord = 0;

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
            if (userAnsver + 1 == trueAnswer) {
                mListener.LearnNewWord();
                LearnWord.addNewWord();
            }
        }
    }

    //Читает информацию из базы данных
    protected boolean ReadBD(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mDBHelper = new DataBaseHelper(getActivity());
            try {
                Result = ReadFromDataBase.readDataFromBD(mDBHelper,  Integer.parseInt(ReadFromDataBase.getTheFirstIdSpecificColumn(new DataBaseHelper(getActivity()),"TaskAnswersList","Learn","NULL"))-1, "TaskAnswersList"); // Читает из бызы данных записи
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
            // Считывает сохраненную информацию
            Result = savedInstanceState.getStringArray("Result");
            active = savedInstanceState.getBoolean("active");
            userAnsver = savedInstanceState.getInt("UserAnsver");
            learnWord =  savedInstanceState.getInt("learnWord");
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("learnWord",learnWord);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (ReadBD(savedInstanceState)) {
            View view = super.onCreateView(inflater, container, savedInstanceState);

            text = view.findViewById(R.id.listWord);
            text.setVisibility(View.VISIBLE);
            text.setText("Изучено " + learnWord + " слов");
            return view;
        }
        return null;
    }

    @Override
    protected void TrueAndFalseAnswer() {
        super.TrueAndFalseAnswer();
        if (active == false) {
            int id = LearnWord.getCurrentID();
            if (userAnsver + 1 == trueAnswer) {
                learnWord++;
                text.setText("Изучено " + learnWord + " слов");
                Fabric.enterLevel("LearnNewWord_" + id, 100, true);
                ReadFromDataBase.writeToDataBase(new DataBaseHelper(getContext()), Integer.parseInt(Result[0]), "Learn", "TRUE", "TaskAnswersList");
            } else
                Fabric.enterLevel("LearnNewWord_" + id, 0, false);
        }
    }
}
