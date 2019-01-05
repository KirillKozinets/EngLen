package com.example.englen.Layouts.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.R;
import com.example.englen.utils.AnalyticsApplication;
import com.example.englen.utils.LearnWord;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class MainFragment extends Fragment implements OnBackPressedListener {

    ChandgeFragment Fragment;
    Tracker mTracker;
    Word word = new Word();
    LearnGrammary LearnG = new LearnGrammary();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mTracker = ((AnalyticsApplication) getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName(this.getClass().getCanonicalName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button but = view.findViewById(R.id.butWord);
        Button gram = view.findViewById(R.id.butGram);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment.onCloseFragment(word);
            }
        });
        gram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Fragment.onCloseFragment(LearnG
            );
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