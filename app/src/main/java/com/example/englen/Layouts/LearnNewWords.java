package com.example.englen.Layouts;

import android.content.Context;
import android.net.Uri;
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
import com.example.englen.Interface.chandgeFragment;
import com.example.englen.Interface.chandgeTaskAnswer;
import com.example.englen.R;
import com.example.englen.utils.ExperienceControl;

public class LearnNewWords extends Fragment implements chandgeTaskAnswer, OnBackPressedListener,LeanWord {

    Fragment youFragment;
    chandgeFragment chandge;
    int LearnWord;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("LW",LearnWord);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            youFragment = new TaskAnswerFragment();
            FragmentManager fragmentManager = getChildFragmentManager();
            fragmentManager.beginTransaction()          // получаем экземпляр FragmentTransaction
                    .replace(R.id.Fr1, youFragment)
                    .commit();
        }else
            LearnWord = savedInstanceState.getInt("LW");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn_new_words, container, false);
        Button but = view.findViewById(R.id.butBack);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back();
            }
        });
        return view;
    }

    void Back() {
        ExperienceControl.addExperience(LearnWord * 20);
        LearnWord = 0;
        chandge = (chandgeFragment) getActivity();
        chandge.onCloseFragment(new LevelInfo());
    }

    @Override
    public void onCloseFragment() {
        System.gc();
        LearnWord++;
        youFragment = new TaskAnswerFragment();
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()          // получаем экземпляр FragmentTransaction
                .replace(R.id.Fr1, youFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        Back();
    }

    @Override
    public void LeanWord() {
        ExperienceControl.addExperience(LearnWord * 20);
        chandge = (chandgeFragment) getActivity();
        if(LearnWord == 0)
            chandge.onCloseFragment(new Word());
        else
            chandge.onCloseFragment(new LevelInfo());
    }
}
