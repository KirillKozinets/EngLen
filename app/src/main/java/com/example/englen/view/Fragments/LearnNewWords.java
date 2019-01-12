package com.example.englen.view.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.englen.Interface.LeanWord;
import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.Interface.ChandgeTaskAnswer;
import com.example.englen.view.Fragments.TaskAnswer.TaskAnswerFragmentNewWord;
import com.example.englen.view.Fragments.TaskAnswer.TaskAnswerFragmentRememberWord;
import com.example.englen.R;
import com.example.englen.utils.ExperienceControl;

public class LearnNewWords extends Fragment implements ChandgeTaskAnswer, OnBackPressedListener, LeanWord {

    Word word;
    Fragment youFragment; // Фрагмент с тестом
    ChandgeFragment chandge; // Интерфейс меняющий фрагменты внутри активности
    int LearnWord; // Количество выученных слов
    int RememberWord;
    Boolean isNew;

    // Данный метод сохраняет состояние фрагмента
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("LW", LearnWord); //Сохраняем количество выученных слов
        outState.putInt("RW", RememberWord); //Сохраняем количество повторенных слов
        outState.putBoolean("isNew", isNew);
        super.onSaveInstanceState(outState);
    }

    // Метод вызывается при запуске фрагмента
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            // Если фрагмент создан в первый раз (ранее не перезапускался)
            // создаем внутри себя ещё один фрагмент отвечающий за тест

            word = new Word();

            Bundle bundle1 = this.getArguments();
            if (bundle1 != null) {
                isNew = bundle1.getBoolean("isNew", true);

                if (isNew)
                    youFragment = new TaskAnswerFragmentNewWord();
                else
                    youFragment = new TaskAnswerFragmentRememberWord();

                Bundle bundle = new Bundle();
                bundle.putBoolean("isNewWord", isNew);
                youFragment.setArguments(bundle);
                FragmentManager fragmentManager = getChildFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.Fr1, youFragment)
                        .commit();
            }else
                throw new NullPointerException();

        } else {
            // Если фрагмент перезапускался(Например менялась ориентация экрана)
            // Запоминаем скольско слов выучили
            RememberWord = savedInstanceState.getInt("RW");
            LearnWord = savedInstanceState.getInt("LW");
            isNew = savedInstanceState.getBoolean("isNew");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn_new_words, container, false);
        Button but = view.findViewById(R.id.butBack);

        // Выполняется при нажатие кнопки назад
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back();
            }
        });

        return view;
    }

    // Данный метод вызывается при окончании изучения новых слов
    void Back() {
        if (isNew)
            // Добавляется опыт
            ExperienceControl.addExperience(LearnWord * 20);
        else
            ExperienceControl.addExperience(RememberWord * 30);
        //Меняем фрагмент на фрагмент и мнформацией о опыте
        chandge = (ChandgeFragment) getActivity();
        if (LearnWord == 0 && RememberWord == 0)
            getActivity().getSupportFragmentManager().popBackStack();
        else
            chandge.onCloseFragment(new LevelInfo());
    }

    // Вызывается фрагментом с тестом для увеличения количества выученных
    // Слов на единицу
    @Override
    public void LearnNewWord() {
        LearnWord++;
    }

    @Override
    public void RememberNewWord() {
        RememberWord++;
    }

    // Метод вызывается при нажатие back
    @Override
    public void onBackPressed() {
        Back();
    }

    // Вызывается когда пользователь выучил все слова
    @Override
    public void LeanWord() {
        Back();
    }
}
