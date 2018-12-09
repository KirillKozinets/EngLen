package com.example.englen.Layouts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.Interface.chandgeFragment;
import com.example.englen.R;
import com.example.englen.utils.ExperienceControl;


public class LevelInfo extends Fragment implements OnBackPressedListener {
    private ProgressDialog mDialog;
    private TextView text;
    private Button ok;
    ProgressBar mProgress;

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
                chandgeFragment chandge = (chandgeFragment) getActivity();
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
        chandgeFragment cF = (chandgeFragment)getActivity();
        cF.onCloseFragment(new MainFragment());
    }
}