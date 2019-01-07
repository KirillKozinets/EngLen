package com.example.englen.Layouts.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.Interface.PassedTheAnswer;
import com.example.englen.Layouts.Fragments.TaskAnswer.TaskAnswerFragmentTest;
import com.example.englen.R;
import com.example.englen.utils.ExperienceControl;


public class TestTheory extends Fragment implements PassedTheAnswer {
    private static final String ARG_IDTEST = "param1";
    private int Hp = 3;
    private ProgressBar progress;
    private String DBname;
    private ChandgeFragment CF;
    private LevelInfo learn = new LevelInfo();
    private Button[] button = new Button[3];
    private ConstraintLayout CL;

    public static TestTheory newInstance(String DBname) {
        TestTheory fragment = new TestTheory();
        Bundle args = new Bundle();
        args.putString(ARG_IDTEST, DBname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("HP",Hp);
        outState.putInt("progress",progress.getProgress());

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            DBname = getArguments().getString(ARG_IDTEST);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_theory, container, false);
        progress = view.findViewById(R.id.progress);

        button[0] = view.findViewById(R.id.butBack);
        button[1] = view.findViewById(R.id.butBack1);
        button[2] = view.findViewById(R.id.butBack3);

        CL = view.findViewById(R.id.const1);
        Fragment youFragment = new TaskAnswerFragmentTest();
        Bundle b = new Bundle();
        b.putString("param1", DBname);
        youFragment.setArguments(b);
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()          // получаем экземпляр FragmentTransaction
                .replace(R.id.frame, youFragment)
                .commit();

        if(savedInstanceState != null)
        {
            Hp = savedInstanceState.getInt("HP");
            for(int i = 3; i > Hp ; i--)
            {
                CL.removeView(button[i - 1]);
            }
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CF = (ChandgeFragment) context;
    }

    @Override
    public void PassedTheAnswer(@Nullable boolean trueAnswer) {
        if (Hp <= 0) {
            Toast.makeText(getActivity().getApplicationContext(), "Вы потратили все попытки", Toast.LENGTH_LONG).show();
            getActivity().onBackPressed();
            return;
        }
        if (!trueAnswer) {
            CL.removeView(button[Hp - 1]);
            Hp--;
        }
        progress.setProgress(progress.getProgress() + 10);
    }

    @Override
    public void Exit() {
        ExperienceControl.addExperience(100);
        CF.onCloseFragment(learn);
    }
}
