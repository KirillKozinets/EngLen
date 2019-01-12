package com.example.englen.view.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.englen.utils.AnalyticsApplication;
import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.R;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class Word extends Fragment implements OnBackPressedListener {
    ChandgeFragment mListener;
    LearnNewWords LNW;
Tracker mTracker;

    @Override
    public void onResume() {
        super.onResume();
        mTracker = ((AnalyticsApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName(this.getClass().getCanonicalName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        LNW = new LearnNewWords();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word, container, false);
        Button button = view.findViewById(R.id.NewWord);
        Button button1 = view.findViewById(R.id.but);
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
        mListener.onCloseFragment(LNW);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChandgeFragment) {
            mListener = (ChandgeFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onBackPressed() {
        ChandgeFragment cF  = (ChandgeFragment)getActivity();
        cF.onCloseFragment(new MainFragment());
    }
}
