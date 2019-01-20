package com.example.englen.view.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.airbnb.paris.Paris;
import com.example.englen.Data.DataBase.DataBaseHelper;
import com.example.englen.Data.DataBase.ReadFromDataBase;
import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.R;
import com.example.englen.view.Layouts.PopUpLayout;


public class LearnGrammary extends Fragment implements OnBackPressedListener {

    private Button button;
    PopUpLayout popUpLayout;
    private FrameLayout linearLayout1;
    private RelativeLayout linearLayout;
    private boolean isViewInfo = false;
    private Button backButton;
    int x;
    View b;
    Animation animation;
    LinearLayout.LayoutParams linnear_lay;
    private final int USERID = 6000;
    private int countID = 1;
    private ChandgeFragment CF;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learn_grammary, container, false);


        linearLayout = view.findViewById(R.id.linearLayout);
        linearLayout1 = view.findViewById(R.id.linearLayout1);

        DataBaseHelper helper = new DataBaseHelper(getActivity().getApplicationContext());
        final String[][] ArraysResult = ReadFromDataBase.readAllDataFromBD(helper,"TheGrammaryList");

        for (int i = 0; i < ArraysResult.length; i++) {

            button = new Button(getActivity().getApplicationContext());
            button.setText(ArraysResult[i][2]);

            linnear_lay = new LinearLayout.LayoutParams(button.getHeight(), button.getWidth());
            linnear_lay.setMargins(100, x + 150, 100, 60);
            x = x + 150;
            linnear_lay.width = ViewGroup.LayoutParams.MATCH_PARENT;
            linnear_lay.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            button.setLayoutParams(linnear_lay);

            button.setId(countID);
            button.setOnClickListener(click);
            linearLayout.addView(button);
            countID++;
        }

        popUpLayout = new PopUpLayout(getContext());

        return view;
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (backButton == v) {// Если 2 раза нажали на одну и ту же кнопку
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.close);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        linearLayout1.removeView(popUpLayout);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                popUpLayout.startAnimation(animation);
                backButton = null;
                return;
            } // Удаляем старый
            if (isViewInfo) {
                linearLayout1.removeView(popUpLayout);
            }

            popUpLayout.chandgeInfo(ReadFromDataBase.readSpecificColumnFromBD(new DataBaseHelper(getContext()),v.getId(),"TheGrammaryList","Name"));

            // Задаём новые коардинаты
            LinearLayout.LayoutParams linnear_lay = new LinearLayout.LayoutParams(200,200);
            linnear_lay.setMargins(100, v.getTop() + 150, 100, 60);
            linnear_lay.width = ViewGroup.LayoutParams.MATCH_PARENT;
            linnear_lay.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            popUpLayout.setLayoutParams(linnear_lay);

            //Анимация
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.open);
            popUpLayout.startAnimation(animation);

            //Показываем
            linearLayout1.addView(popUpLayout);

            countID++;
            isViewInfo = true;
            backButton = (Button) v;
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CF = (ChandgeFragment) context;
    }

    @Override
    public void onBackPressed() {
        CF.onCloseFragment(new MainFragment());
    }
}
