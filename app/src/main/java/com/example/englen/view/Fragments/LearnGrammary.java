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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.airbnb.paris.Paris;
import com.example.englen.Data.DataBase.DataBaseHelper;
import com.example.englen.Data.DataBase.ReadFromDataBase;
import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.R;
import com.example.englen.utils.LastTopicCovered;
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
    private Theory theory; // Фрагмент с теорией
    private TestTheory testTheory; // Фрагмент с тестом
    private String[][] ArraysResult; // База данных
    private int lastId; // id последнего нажатого элемента
    int x = 50;
    RelativeLayout linLayout;
    int heightItem;
    int b;
    ScrollView scrol;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learn_grammary, container, false);
        if (savedInstanceState == null) {

            container.post(new Runnable() {
                @Override
                public void run() {
                    b = linLayout.getHeight();
                }
            });

            scrol = view.findViewById(R.id.scroll);

            popUpLayout = new PopUpLayout(getContext());

            // Открывается фрагмент с теорией
            popUpLayout.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    theory = Theory.newInstance(
                            ArraysResult[popUpLayout.getID() - 1][2],
                            ArraysResult[popUpLayout.getID() - 1][3],
                            lastId
                    );
                    CF.onCloseFragment(theory);
                }
            });

            // Открывается фрагмент с тестом
            popUpLayout.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    testTheory = TestTheory.newInstance(
                            ArraysResult[popUpLayout.getID() - 1][2],
                            lastId
                    );
                    CF.onCloseFragment(testTheory);
                }
            });

            frameLayout1 = view.findViewById(R.id.linearLayout1);

            // Чтение из базы данных
            DataBaseHelper helper = new DataBaseHelper(getActivity().getApplicationContext());
            ArraysResult = ReadFromDataBase.readAllDataFromBD(helper, "TheGrammaryList");

            // настраиваем список
            linLayout = view.findViewById(R.id.linearLayout);


            linLayout.removeAllViews(); // Очищаем
            for (int i = 0; i < ArraysResult.length; i++) {
                RoundButtonLayouts rb = new RoundButtonLayouts(getContext(), i + 1); // Создаем новый LinerLayout
                ImageView image = rb.findViewById(R.id.image); // Изображение

                // Если уже пройдено , то картинка зелёная , иначе серая
                if (Boolean.parseBoolean(ArraysResult[i][4]) == true)
                    image.setImageResource(R.drawable.circle);
                else
                    image.setImageResource(R.drawable.circlegray);

                // Вызывается при клике
                ((TextView) rb.findViewById(R.id.text)).setText(ArraysResult[i][1]);
                rb.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        click(view, view.getId());
                    }
                });

                RelativeLayout.LayoutParams linnear_lay = new RelativeLayout.LayoutParams(linLayout.getWidth(), linLayout.getWidth());
                linnear_lay.width = ViewGroup.LayoutParams.MATCH_PARENT;
                linnear_lay.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                linnear_lay.addRule(RelativeLayout.BELOW, i + 999);
                rb.setLayoutParams(linnear_lay);


                rb.findViewById(R.id.button).setId(i + 1);
                rb.setId(i + 1000);

                linLayout.addView(rb);
            }
        }

        scrol.post(new Runnable() {
            @Override
            public void run() {
                linLayout.setFocusable(false);
                int a = (int) ((int) b * LastTopicCovered.getlastTopicCoveredID() / (ArraysResult.length));
                scrol.scrollTo(0,a);
            }
        });

        return view;
    }


    private void click(View v, int id) {
        lastId = id;
        if (backButton == v) {// Если 2 раза нажали на одну и ту же кнопку
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.close);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // Удалить , после завершения анимации
                    linLayout.removeView(popUpLayout);
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
            linLayout.removeView(popUpLayout);
        }

        popUpLayout.chandgeInfo(ReadFromDataBase.readSpecificColumnFromBD(new DataBaseHelper(getContext()), v.getId(), "TheGrammaryList", "Name"), id);


        //Анимация
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.open);
        popUpLayout.startAnimation(animation);

        //Показываем
        linLayout.addView(popUpLayout);

        // Задаём новые коардинаты
        RelativeLayout.LayoutParams linnear_lay = (RelativeLayout.LayoutParams) popUpLayout.getLayoutParams();
        int a = (int) ((int) b * (v.getId() - 0.5) / (ArraysResult.length));
        linnear_lay.setMargins(100, a, 100, 60);
        linnear_lay.width = ViewGroup.LayoutParams.MATCH_PARENT;
        linnear_lay.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        popUpLayout.setLayoutParams(linnear_lay);

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
