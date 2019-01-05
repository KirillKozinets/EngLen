package com.example.englen.Layouts.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.englen.Data.DataBase.ReadTask;
import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.R;


public class LearnGrammary extends Fragment {

    private Button button;
    LinearLayout subLayout;
    private FrameLayout linearLayout1;
    private RelativeLayout linearLayout;
    private boolean isViewInfo = false;
    private Button backButton;
    int x;
    View b;
    Animation animation;
    LinearLayout.LayoutParams linnear_lay;
    private final int USERID = 6000;
    private int countID;
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
        b = inflater.inflate(R.layout.selectmenu, null);

        linearLayout = view.findViewById(R.id.linearLayout);
        linearLayout1 = view.findViewById(R.id.linearLayout1);

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.close);
                b.startAnimation(animation);
                linearLayout1.removeView(b);
            }
        });

        final String[] ArraysResult = new String[3];
        DataBaseHelper helper = new DataBaseHelper(getActivity().getApplicationContext());
        ReadTask.updataDataBase(helper);// Обновляем базу данных
        SQLiteDatabase mDb = helper.getWritableDatabase();// Читаем базу данных

        Cursor cursor = mDb.rawQuery("SELECT * FROM TheGrammaryList", null); // Читаем из базы данных определенные записи
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.move(i);

            if (cursor.moveToFirst()) {
                for (int q = 0; q < cursor.getColumnCount() - 1; q++) {
                    ArraysResult[q] = cursor.getString(q + 1);
                }
            }

            button = new Button(getActivity().getApplicationContext());
            button.setText(ArraysResult[1]);

            linnear_lay = new LinearLayout.LayoutParams(button.getHeight(), button.getWidth());
            linnear_lay.setMargins(100, x + 150, 100, 60);
            x = x + 150;
            linnear_lay.width = ViewGroup.LayoutParams.MATCH_PARENT;
            linnear_lay.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            button.setLayoutParams(linnear_lay);

            button.setId(USERID + countID);
            button.setOnClickListener(click);
            linearLayout.addView(button);
            countID++;
        }


        subLayout = new LinearLayout(getActivity().getApplicationContext());
        subLayout.setBackgroundResource(R.color.Lite);
        subLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        );
        subLayout.setOrientation(LinearLayout.VERTICAL);

        // Первая кнопка
        Button b = new Button(getActivity().getApplicationContext());
        Paris.styleBuilder(b)
                .add(R.style.GreenButton)
                .apply();

        b.setText("теория");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CF.onCloseFragment(Theory.newInstance(ArraysResult[1], "<html>\n" +
                        "<head>\n" +
                        "<title>HTML код таблицы, примеры</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<table border=\"1\">\n" +
                        "<tr>\n" +
                        "<td>ячейка 1, первый ряд</td>\n" +
                        "<td>ячейка 2, первый ряд</td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td>ячейка 1, второй ряд</td>\n" +
                        "<td>ячейка 2, второй ряд</td>\n" +
                        "</tr>\n" +
                        "</table> \n" +
                        "</body>\n" +
                        "</html>"));
            }
        });

        LinearLayout.LayoutParams LP1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LP1.leftMargin = 40;
        LP1.rightMargin = 40;
        LP1.topMargin = 80;
        b.setLayoutParams(LP1);

        b.setId(USERID + countID);
        subLayout.addView(b);
        countID++;

        // Вторая кнопка
        Button b2 = new Button(getActivity().getApplicationContext());
        Paris.styleBuilder(b2)
                .add(R.style.GreenButton)
                .apply();

        b2.setText("тест");
        LinearLayout.LayoutParams LP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LP.leftMargin = 40;
        LP.rightMargin = 40;
        LP.topMargin = 40;
        LP.bottomMargin = 40;
        b2.setId(USERID + countID);
        b2.setLayoutParams(LP);
        subLayout.addView(b2);
        countID++;

        return view;
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (backButton == v) {// Если 2 раза нажали на одну и ту же кнопку
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.close);
                subLayout.startAnimation(animation);
                backButton = null;
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        linearLayout1.removeView(subLayout);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                return;
            } // Удаляем старый
            if (isViewInfo) {
                linearLayout1.removeView(subLayout);
            }

            // Задаём новые коардинаты
            LinearLayout.LayoutParams linnear_lay = new LinearLayout.LayoutParams(b.getHeight(), b.getWidth());
            linnear_lay.setMargins(100, v.getTop() + 150, 100, 60);
            linnear_lay.width = ViewGroup.LayoutParams.MATCH_PARENT;
            linnear_lay.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            subLayout.setLayoutParams(linnear_lay);

            // Указываем id
            subLayout.setId(USERID + countID);

            //Анимация
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.open);
            subLayout.startAnimation(animation);

            //Показываем
            linearLayout1.addView(subLayout);

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
}