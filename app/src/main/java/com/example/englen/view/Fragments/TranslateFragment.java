package com.example.englen.view.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.englen.Data.Retrofit.RetrofitNet;
import com.example.englen.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TranslateFragment extends Fragment {

    TextView textView;
    EditText editText;
    RetrofitNet.RetrofitSend result1;
    @BindView(R.id.b1)
    AppCompatRadioButton b1;
    Unbinder unbinder;
    @BindView(R.id.b2)
    AppCompatRadioButton b2;

    public TranslateFragment() {

    }

    public static TranslateFragment newInstance() {
        return new TranslateFragment();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("getTranslate", editText.getText().toString());
        outState.putBoolean("state", b1.isChecked());
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translate, container, false);
        result1 = new RetrofitNet.RetrofitSend(getContext());
        unbinder = ButterKnife.bind(this, view);
        editText = view.findViewById(R.id.input);
        textView = view.findViewById(R.id.result);
        RadioButton ru_en = view.findViewById(R.id.b1);
        RadioButton en_ru = view.findViewById(R.id.b2);

        ru_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result1.setCode("ru-en");
                getTranslate();
            }
        });
        en_ru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result1.setCode("en-ru");
                getTranslate();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                result1.empty = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                getTranslate();
            }
        });


        if (savedInstanceState != null) {
            if (!savedInstanceState.getBoolean("state")) {
                b2.setChecked(true);
                result1.setCode("en-ru");
            }
            editText.setText(savedInstanceState.getString("getTranslate"));
        }


        return view;
    }

    private void getTranslate() {
        String result = editText.getText().toString();
        result1.send(textView, result);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
