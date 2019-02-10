package com.example.englen.view.Activitis;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.englen.utils.LastTopicCovered;
import com.example.englen.view.Fragments.MainFragment;
import com.example.englen.utils.AnalyticsApplication;
import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.R;
import com.example.englen.utils.ExperienceControl;
import com.example.englen.utils.LearnWord;
import com.example.englen.utils.rememberWord;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements ChandgeFragment {

    Fragment youFragment;
    Tracker mTracker;

    @Override
    protected void onPause() {
        super.onPause();

        ExperienceControl.Save();
        LearnWord.Save();
        rememberWord.Save();
        LastTopicCovered.Save();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName(this.getClass().getCanonicalName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        Fabric.with(this, new Crashlytics());
        ExperienceControl.Load(this);
        LearnWord.Load(this);
        rememberWord.Load(this);
        LastTopicCovered.Load(this);

        if (savedInstanceState == null) {
            youFragment = new MainFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()          // получаем экземпляр FragmentTransaction
                    .replace(R.id.Fr, youFragment)
                    .commit();
        }

      /*  Button options = findViewById(R.id.options);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(intent);
            }
        });*/

        mTracker =  ((AnalyticsApplication)getApplication()).getDefaultTracker();
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action1")
                .setAction("Share1")
                .build());
    }

    // Меняет фрагмент на другой
    @Override
    public void onCloseFragment(Fragment fragment) {
        youFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()          // получаем экземпляр FragmentTransaction
                .replace(R.id.Fr, youFragment)
                .addToBackStack("stack")
                .commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        OnBackPressedListener backPressedListener = null;
        for (Fragment fragment: fm.getFragments()) {
            if (fragment instanceof  OnBackPressedListener) {
                backPressedListener = (OnBackPressedListener) fragment;
                break;
            }
        }

        if (backPressedListener != null) {
            backPressedListener.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
