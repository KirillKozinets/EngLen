package com.example.englen.view.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.englen.Data.Retrofit.RetrofitNet;
import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends Fragment implements OnBackPressedListener {

    ChandgeFragment Fragment;
    LearnGrammary LearnG = new LearnGrammary();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button gram = view.findViewById(R.id.butGram);
        Button button = view.findViewById(R.id.NewWord);
        Button buttonTranslate = view.findViewById(R.id.translate);

        gram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment.onCloseFragment(LearnG
                );
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment.onCloseFragment(word_list.newInstance());
            }
        });
        buttonTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment.onCloseFragment(TranslateFragment.newInstance());
            }
        });


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment = (ChandgeFragment) context;
    }

    @Override
    public void onBackPressed() {
        getActivity().finish();
    }
}