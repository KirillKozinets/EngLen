
// Информация о текущем уровне

package com.example.englen.view.Fragments;

import android.annotation.SuppressLint;
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

import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.R;
import com.example.englen.utils.ExperienceControl;


public class LevelInfo extends Fragment implements OnBackPressedListener {
    private TextView text;
    private Button ok;
    private ProgressBar mProgress;
    private TextView message;
    private TextView info;
    private String textMessage;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level_info, null);
        Bundle args = getArguments();
        if(args != null) {
            // Информация для вывода на экран
            textMessage = args.getString("tM");
        }
        text = view.findViewById(R.id.progressBarinsideText);
        ok = view.findViewById(R.id.ok);
        mProgress = view.findViewById(R.id.progressBar);
        message = view.findViewById(R.id.massege);
        info = view.findViewById(R.id.info);

        message.setText(textMessage);

        info.setText("Остается " + (ExperienceControl.getexperienceForNewLevel() - ExperienceControl.getExperience()) + " опыта до следующего уровня");

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
