package com.example.englen.Layouts;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englen.Interface.chandgeTaskAnswer;
import com.example.englen.R;

public class LearnNewWords extends Fragment implements chandgeTaskAnswer {

Fragment youFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            youFragment = new TaskAnswerFragment();
            FragmentManager fragmentManager = getChildFragmentManager();
            fragmentManager.beginTransaction()          // получаем экземпляр FragmentTransaction
                    .replace(R.id.Fr1, youFragment)
                    .commit();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn_new_words, container, false);
        return view;
    }


    @Override
    public void onCloseFragment() {
            youFragment = new TaskAnswerFragment();
        FragmentManager fragmentManager = getChildFragmentManager();
            fragmentManager.beginTransaction()          // получаем экземпляр FragmentTransaction
                    .replace(R.id.Fr1, youFragment)
                    .commit();
    }
}
