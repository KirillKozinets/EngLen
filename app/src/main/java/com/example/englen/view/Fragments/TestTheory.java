package com.example.englen.view.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englen.Data.DataBase.DataBaseHelper;
import com.example.englen.Data.DataBase.ReadFromDataBase;
import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.Interface.PassedTheAnswer;
import com.example.englen.utils.Fabric;
import com.example.englen.utils.LastTopicCovered;
import com.example.englen.view.Fragments.TaskAnswer.TaskAnswerFragmentTest;
import com.example.englen.R;
import com.example.englen.utils.ExperienceControl;

import java.security.AllPermission;


public class TestTheory extends Fragment implements PassedTheAnswer, OnBackPressedListener {
    private static final String ARG_IDTEST = "param1";
    private int Hp = 3;
    private int id;
    private ProgressBar progress;
    private String DBname;
    private ChandgeFragment CF;
    private Button[] button = new Button[3];
    private ConstraintLayout CL;
    AlertDialog.Builder ad;
    private Button exit;

    public static TestTheory newInstance(String DBname, int id) {
        TestTheory fragment = new TestTheory();
        fragment.id = id;
        Bundle args = new Bundle();
        args.putString(ARG_IDTEST, DBname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("HP", Hp);
        outState.putInt("progress", progress.getProgress());

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
        if (savedInstanceState == null) {
            Fragment youFragment = TaskAnswerFragmentTest.newInstance();
            Bundle b = new Bundle();
            b.putString("param1", DBname);
            youFragment.setArguments(b);
            FragmentManager fragmentManager = getChildFragmentManager();
            fragmentManager.beginTransaction()          // получаем экземпляр FragmentTransaction
                    .replace(R.id.frame, youFragment)
                    .commit();
        }

        if (savedInstanceState != null) {
            Hp = savedInstanceState.getInt("HP");
            for (int i = 3; i > Hp; i--) {
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
    public void PassedTheAnswer(@Nullable boolean trueAnswer, int addProgress) {
        if (!trueAnswer) {
            CL.removeView(button[Hp - 1]);
            Hp--;
        }
        if (Hp <= 0) {
            Toast.makeText(getActivity().getApplicationContext(), "Вы потратили все попытки", Toast.LENGTH_LONG).show();
            returnToStartList();
            return;
        }
        progress.setProgress(progress.getProgress() + addProgress);
    }

    @Override
    public void Exit() {
        Fabric.enterLevel(DBname, 100, true);

        ExperienceControl.addExperience(100);
        LevelInfo LI = new LevelInfo();
        Bundle bundle = new Bundle();

            bundle.putString("tM", "Изучение новой темы завершено . Получено " +100 + " опыта.");

        LI.setArguments(bundle);
        CF.onCloseFragment(LI);

        ReadFromDataBase.writeToDataBase(new DataBaseHelper(getContext()), id, "Finished", "TRUE", "TheGrammaryList");
        if (id > LastTopicCovered.getlastTopicCoveredID()) {
            LastTopicCovered.setlastTopicCoveredID(id - 1);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog dialog = viewAlertDialog(
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Fabric.enterLevel(DBname, 0, false);
                        CF.onCloseFragment(new LearnGrammary());
                    }
                },
                null,
                getContext(),
                "Выйти",
                "Вы уверены , что хотите прекратить выполнение теста?");
        dialog.show();
    }

    public void returnToStartList() {
        Fabric.enterLevel(DBname, 0, false);
        CF.onCloseFragment(new LearnGrammary());
    }

    private AlertDialog viewAlertDialog(DialogInterface.OnClickListener yes, DialogInterface.OnClickListener no, Context context, String title, String message) {
        String button1String = "да";
        String button2String = "нет";

        ad = new AlertDialog.Builder(context, R.style.YourAlertDialogTheme);
        ad.setTitle(title);  // заголовок
        ad.setMessage(message); // сообщение
        ad.setPositiveButton(button1String, yes);
        ad.setNegativeButton(button2String, no);


        return ad.create();
    }

}
