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

    private PopUpLayout popUpLayout; // Всплывающее меню
    private FrameLayout frameLayout1; // В этом контейнере хранится всплювающее меню
    private boolean isViewInfo = false; // При нажатие на тему становится true ,
    // нужен для удаления старого всплывающего меню выбора

    private View backButton; // Прошлая нажатая тема
    private Animation animation; // Анимация
    private ChandgeFragment CF; // Интерфейс для связи с активностью
    private ArrayList<ItemTheory> itemTheoryList = new ArrayList<ItemTheory>(); // Список с темами
    private Theory theory; // Фрагмент с теорией
    private TestTheory testTheory; // Фрагмент с тестом
    private String[][] ArraysResult; // База данных

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
        if (savedInstanceState == null)
        {
            popUpLayout = new PopUpLayout(getContext());

            // Открывается фрагмент с теорией
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

            // Открывается фрагмент с тестом
            popUpLayout.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    testTheory = TestTheory.newInstance(
                            ArraysResult[popUpLayout.getId()-1][2]
                    );
                    CF.onCloseFragment(testTheory);
                }
            });

            frameLayout1 = view.findViewById(R.id.linearLayout1);

            // Чтение из базы данных
            DataBaseHelper helper = new DataBaseHelper(getActivity().getApplicationContext());
            ArraysResult = ReadFromDataBase.readAllDataFromBD(helper, "TheGrammaryList");

            // Заново наполняем теорией ListView
            itemTheoryList.removeAll(itemTheoryList);
            for (int i = 0; i < ArraysResult.length; i++) {
                itemTheoryList.add(new ItemTheory(ArraysResult[i][1], i + 1));
            }

            RoundButtonLayouts boxAdapter = new RoundButtonLayouts(getContext(), itemTheoryList);

            // настраиваем список
            ListView lvMain = view.findViewById(R.id.linearLayout);

            lvMain.setAdapter(boxAdapter);

            // Вызывается при клике на теорию
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
                    // Удалить , после завершения анимации
                    frameLayout1.removeView(popUpLayout);
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
            frameLayout1.removeView(popUpLayout);
        }

        popUpLayout.chandgeInfo(ReadFromDataBase.readSpecificColumnFromBD(new DataBaseHelper(getContext()), ID, "TheGrammaryList", "Name"), ID);

        // Задаём новые коардинаты
        LinearLayout.LayoutParams linnear_lay = new LinearLayout.LayoutParams(popUpLayout.getWidth(), popUpLayout.getHeight());
        linnear_lay.setMargins(100, v.getTop() + (v.getHeight() / 2), 100, 60);
        linnear_lay.width = ViewGroup.LayoutParams.MATCH_PARENT;
        linnear_lay.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        popUpLayout.setLayoutParams(linnear_lay);

        //Анимация
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.open);
        popUpLayout.startAnimation(animation);

        //Показываем
        frameLayout1.addView(popUpLayout);

        isViewInfo = true;
        backButton = v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CF = (ChandgeFragment) context;
    }

    // При нажатие назад меняем фрагмент
    @Override
    public void onBackPressed() {
        CF.onCloseFragment(new MainFragment());
    }
}
