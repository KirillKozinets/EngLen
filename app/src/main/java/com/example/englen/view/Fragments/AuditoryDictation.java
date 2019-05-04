package com.example.englen.view.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englen.Data.DataBase.DataBaseHelper;
import com.example.englen.Data.DataBase.ReadFromDataBase;
import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.Interface.LeanWord;
import com.example.englen.R;
import com.example.englen.utils.ExperienceControl;
import com.example.englen.utils.LearnWord;
import com.example.englen.utils.Statistics.Statistics;
import com.example.englen.view.Activitis.MainActivity;

import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuditoryDictation extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.listWord)
    TextView listWord;
    @BindView(R.id.trueAnswer)
    TextView trueAnswer;
    @BindView(R.id.repeatAudio)
    Button repeatAudio;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.b8)
    Button b8;
    @BindView(R.id.b6)
    Button b6;

    private String trueAnswerStr;
    private int rememberWord;
    private ChandgeFragment CF;
    TextToSpeech TTS;
    DataBaseHelper mDBHelper;
    boolean active = true;
    int True;

    public AuditoryDictation() {
        // Required empty public constructor
    }

    private boolean readBD(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            if (rememberWord >= 10) {
                LevelInfo LI = new LevelInfo();
                Bundle bundle = new Bundle();
                bundle.putString("tM","Повторение слов завершено. Получено " + True * 50 + " опыта");
                LI.setArguments(bundle);
                ExperienceControl.addExperience(True * 50);
                CF.onCloseFragment(LI); // Закрывает текущий фрагмент и показывает информацию о уровне
            }

            try {
                if (LearnWord.getCurrentID() > 10) {
                    Random random = new Random();
                    int rand = random.nextInt(LearnWord.getCurrentID());
                    String[] result = ReadFromDataBase.readDataFromBD(mDBHelper, rand, "TaskAnswersList"); // Читает из бызы данных записи
                    trueAnswerStr = result[8];
                } else
                    throw new ArrayIndexOutOfBoundsException();
            } catch (Exception ex) {
                Toast toast = Toast.makeText(getContext(),
                        "Нужно выучить больше слов",
                        Toast.LENGTH_SHORT);

                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return false;
            }
        } else {
            // Считывает сохраненную информацию
            trueAnswerStr = savedInstanceState.getString("trueAnswerStr");
            editText.setText(savedInstanceState.getString("answerStr"));
            rememberWord = savedInstanceState.getInt("rememberWord");
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("answerStr", editText.getText().toString());
        outState.putString("trueAnswerStr", trueAnswerStr);
        outState.putInt("rememberWord", rememberWord);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mDBHelper = new DataBaseHelper(getActivity());
        if (readBD(savedInstanceState)) {
            View view = inflater.inflate(R.layout.fragment_auditory_dictation, container, false);
            unbinder = ButterKnife.bind(this, view);
            TTS = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int initStatus) {
                    if (initStatus == TextToSpeech.SUCCESS) {
                        TTS.setLanguage(Locale.ENGLISH);

                        TTS.setPitch(1.f);
                        TTS.setSpeechRate(0.7f);
                    } else if (initStatus == TextToSpeech.ERROR) {
                        Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_LONG).show();
                    }
                }
            });

            repeatAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TTS.speak(trueAnswerStr, TextToSpeech.QUEUE_FLUSH, null);
                }
            });

            b6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (active == true) {
                        rememberWord++;
                        listWord.setText(rememberWord + " / 10");
                        trueAnswer.setVisibility(View.VISIBLE);
                        trueAnswer.setText("Правильный вариант " + trueAnswerStr + "\n" + "Нажмите <Далее> чтобы продолжить");
                        if (toLowerCase(trueAnswerStr).equals(toLowerCase(editText.getText().toString()))) {
                            trueAnswer.setBackgroundResource(R.drawable.trueanswer);
                            True++;
                        } else {
                            trueAnswer.setBackgroundResource(R.drawable.falseanser);
                        }
                        active = false;
                    } else {
                        editText.setText("");
                        trueAnswer.setVisibility(View.GONE);

                        readBD(savedInstanceState);
                        active = true;
                    }
                }
            });
            return view;
        }
        CF.onCloseFragment(new word_list());
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            editText.setText(savedInstanceState.getString("answerStr"));
            trueAnswerStr = savedInstanceState.getString("trueAnswer");
            rememberWord = savedInstanceState.getInt("rememberWord");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CF = (ChandgeFragment) context;
    }

    private String toLowerCase(String string) {
        string = string.replaceAll("\\s+", "");
        StringBuilder sb = new StringBuilder(string);
        for (int index = 0; index < string.length(); index++) {
            char c = string.charAt(index);
            sb.setCharAt(index, Character.toLowerCase(c));
        }

        return sb.toString();
    }

}
