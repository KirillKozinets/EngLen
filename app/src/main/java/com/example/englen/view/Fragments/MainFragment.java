package com.example.englen.view.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.R;

public class MainFragment extends Fragment implements OnBackPressedListener {

    ChandgeFragment Fragment;
    LearnGrammary LearnG = new LearnGrammary();
    LearnNewWords LNW = new LearnNewWords();

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
        Button button1 = view.findViewById(R.id.but);
        gram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Fragment.onCloseFragment(LearnG
            );
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLearnNewWord(true);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLearnNewWord(false);
            }
        });
        return view;
    }

    private void startLearnNewWord(Boolean isNew)
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isNew",isNew);
        LNW.setArguments(bundle);
        Fragment.onCloseFragment(LNW);
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