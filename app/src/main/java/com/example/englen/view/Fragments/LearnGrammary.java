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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.englen.Data.DataBase.DataBaseHelper;
import com.example.englen.Data.DataBase.ReadFromDataBase;
import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.R;
import com.example.englen.utils.LastTopicCovered;
import com.example.englen.view.Layouts.PopUpLayout;
import com.example.englen.view.Layouts.RoundButtonLayouts;


public class LearnGrammary extends Fragment implements OnBackPressedListener {

    private PopUpLayout popUpLayout; // Всплывающее меню
    private boolean isViewInfo = false; // При нажатие на тему становится true ,
    // нужен для удаления старого всплывающего меню выбора

    private View backButton; // Прошлая нажатая тема
    private Animation animation; // Анимация
    private ChandgeFragment CF; // Интерфейс для связи с активностью
    private Theory theory; // Фрагмент с теорией
    private TestTheory testTheory; // Фрагмент с тестом
    private String[][] ArraysResult; // База данных
    private int lastId; // id последнего нажатого элемента
    RelativeLayout containerLayout;// Контейнер
    int containerWidth; // Ширина контейнера
    ScrollView scrol;
    RoundButtonLayouts rb;
    private boolean isViewPopUpLayout;// Находится ли popUpLayout на экране
    private boolean finishedAnimation = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void selectImage(RoundButtonLayouts rb, int i) {
        ImageView image = rb.findViewById(R.id.image); // Изображение

        // Если уже пройдено , то картинка зелёная , иначе серая
        if (Boolean.parseBoolean(ArraysResult[i][4]) == true)
            image.setImageResource(R.drawable.circle);
        else
            image.setImageResource(R.drawable.circlegray);
    }

    // Заполняет контейнер
    private void FillList() {
        containerLayout.removeAllViews(); // Очищаем
        for (int i = 0; i < ArraysResult.length; i++) {
            rb = new RoundButtonLayouts(getContext(), i + 1); // Создаем новый LinerLayout
            selectImage(rb, i);

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

            // Вызывается при клике
            ((TextView) rb.findViewById(R.id.text)).setText(ArraysResult[i][1]);
            rb.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click(view, view.getId());
                }
            });

            RelativeLayout.LayoutParams linnear_lay = new RelativeLayout.LayoutParams(containerLayout.getWidth(), containerLayout.getWidth());
            linnear_lay.width = ViewGroup.LayoutParams.MATCH_PARENT;
            linnear_lay.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            linnear_lay.addRule(RelativeLayout.BELOW, i + 999);
            rb.setLayoutParams(linnear_lay);


            rb.findViewById(R.id.button).setId(i + 1);
            rb.setId(i + 1000);

            containerLayout.addView(rb);
        }
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
                    containerWidth = containerLayout.getHeight();
                }
            });

            scrol = view.findViewById(R.id.scroll);
            containerLayout = view.findViewById(R.id.linearLayout);

            popUpLayout = new PopUpLayout(getContext());

            // Чтение из базы данных
            DataBaseHelper helper = new DataBaseHelper(getActivity().getApplicationContext());
            ArraysResult = ReadFromDataBase.readAllDataFromBD(helper, "TheGrammaryList");

            containerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click(backButton, 0);
                }
            });

            FillList();

            scrol.post(new Runnable() {
                @Override
                public void run() {
                    containerLayout.setFocusable(false);
                        int a = (int) ((int) containerWidth * LastTopicCovered.getlastTopicCoveredID() / (ArraysResult.length));
                    scrol.scrollTo(0, a);
                }
            });
        }
        return view;
    }

    // Показ анимации
    private void viewAnimation() {
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.close);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                finishedAnimation = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Удалить , после завершения анимации
                containerLayout.removeView(popUpLayout);
                finishedAnimation = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        popUpLayout.startAnimation(animation);
        backButton = null;
    }

    private void click(View v, int id) {
        if(!finishedAnimation) return;
        if (backButton == v) {// Если 2 раза нажали на одну и ту же кнопку
            viewAnimation();
            checkIncreaseTheLength();
            isViewPopUpLayout = false;
            return;
        } // Удаляем старый
        if (isViewInfo) {
            containerLayout.removeView(popUpLayout);
        }

        lastId = id;
        popUpLayout.chandgeInfo(ReadFromDataBase.readSpecificColumnFromBD(new DataBaseHelper(getContext()), v.getId(), "TheGrammaryList", "Name"), id);

        //Анимация
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.open);
        popUpLayout.startAnimation(animation);

        //Показываем
        containerLayout.addView(popUpLayout);
        checkIncreaseTheLength();

        // Задаём новые коардинаты
        RelativeLayout.LayoutParams linnear_lay = (RelativeLayout.LayoutParams) popUpLayout.getLayoutParams();
        int a;
        if (containerWidth == getView().getHeight()) {
            a = (int) ((int) rb.getHeight() * (v.getId() - 0.5));
        } else {
            a = (int) ((int) containerWidth * (v.getId() - 0.5) / (ArraysResult.length));
        }
        linnear_lay.setMargins(100, a, 100, 60);
        linnear_lay.width = ViewGroup.LayoutParams.MATCH_PARENT;
        linnear_lay.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        popUpLayout.setLayoutParams(linnear_lay);

        isViewInfo = true;
        isViewPopUpLayout = true;
        backButton = v;
    }

    private void checkIncreaseTheLength() {
        containerLayout.post(new Runnable() {
            @Override
            public void run() {
                int a = containerLayout.getHeight();
                if (containerWidth < a) {
                    scrol.scrollTo(0, containerLayout.getHeight());
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CF = (ChandgeFragment) context;
    }

    // При нажатие назад меняем фрагмент
    @Override
    public void onBackPressed() {
        if (isViewPopUpLayout == true) {
            click(backButton, 0);
            return;
        }
        CF.onCloseFragment(new MainFragment());
    }
}
