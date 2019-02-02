package com.example.englen.view.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.airbnb.paris.Paris;
import com.example.englen.Data.DataBase.DataBaseHelper;
import com.example.englen.Data.DataBase.ReadFromDataBase;
import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.R;
import com.example.englen.utils.ItemTheory;
import com.example.englen.view.Layouts.PopUpLayout;
import com.example.englen.view.Layouts.RoundButtonLayouts;

import java.util.ArrayList;
import java.util.List;


public class LearnGrammary extends Fragment implements OnBackPressedListener {

    private Button button;
    PopUpLayout popUpLayout;
    private FrameLayout linearLayout1;
    private ListView linearLayout;
    private boolean isViewInfo = false;
    private View backButton;
    int x = 0;
    View b;
    Animation animation;
    LinearLayout.LayoutParams linnear_lay;
    private int countID = 1;
    private ChandgeFragment CF;
    ArrayList<ItemTheory> products = new ArrayList<ItemTheory>();
    RoundButtonLayouts boxAdapter;
    Theory theory;
    String[][] ArraysResult;

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
        if (savedInstanceState == null) {
            popUpLayout = new PopUpLayout(getContext());

            popUpLayout.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    theory = Theory.newInstance(
                            ArraysResult[popUpLayout.getId()-1][2],
                            ArraysResult[popUpLayout.getId()-1][3]
                    );

                    CF.onCloseFragment(theory);
                }
            });

            linearLayout = view.findViewById(R.id.linearLayout);
            linearLayout1 = view.findViewById(R.id.linearLayout1);

            DataBaseHelper helper = new DataBaseHelper(getActivity().getApplicationContext());
            ArraysResult = ReadFromDataBase.readAllDataFromBD(helper, "TheGrammaryList");

            for (int i = 0; i < ArraysResult.length; i++) {
                products.add(new ItemTheory(ArraysResult[i][1], i + 1));
            }

            RoundButtonLayouts boxAdapter = new RoundButtonLayouts(getContext(), products);

            // настраиваем список
            ListView lvMain = view.findViewById(R.id.linearLayout);
        /*lvMain.findViewById(R.id.LearnButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(view,);
            }
        });*/

            lvMain.setAdapter(boxAdapter);
            lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    click(v, position + 1);
                }
            });
        }
        return view;
    }


    private void click(View v, int ID) {
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

        popUpLayout.chandgeInfo(ReadFromDataBase.readSpecificColumnFromBD(new DataBaseHelper(getContext()), ID, "TheGrammaryList", "Name"), ID);

        // Задаём новые коардинаты
        LinearLayout.LayoutParams linnear_lay = new LinearLayout.LayoutParams(200, 200);
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
        backButton = v;
    }

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
