    package com.example.englen.view.Fragments;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.englen.utils.AnalyticsApplication;
import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.R;
import com.example.englen.utils.ExperienceControl;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class LevelInfo extends Fragment implements OnBackPressedListener {
    private TextView text;
    private Button ok;
    ProgressBar mProgress;
    Tracker mTracker;

    @Override
    public void onResume() {
        super.onResume();
        mTracker = ((AnalyticsApplication) getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName(this.getClass().getCanonicalName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level_info, null);
        text = view.findViewById(R.id.progressBarinsideText);
        ok = view.findViewById(R.id.ok);
        mProgress = view.findViewById(R.id.progressBar);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChandgeFragment chandge = (ChandgeFragment) getActivity();
                chandge.onCloseFragment(new MainFragment());
            }
        });

        int EX = ExperienceControl.getExperience();
        int MaxEX = ExperienceControl.getexperienceForNewLevel();
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular);
        mProgress.setMax(MaxEX);
        mProgress.setSecondaryProgress(MaxEX);
        mProgress.setProgress(EX);
        mProgress.setProgressDrawable(drawable);
        text.setText(Integer.toString(ExperienceControl.getLevel()));
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        ChandgeFragment cF = (ChandgeFragment) getActivity();
        cF.onCloseFragment(new MainFragment());
    }
}
