package com.example.englen.view.Activitis;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.englen.utils.LastTopicCovered;
import com.example.englen.utils.Statistics.Statistics;
import com.example.englen.utils.Statistics.Time;
import com.example.englen.view.Fragments.MainFragment;
import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.R;
import com.example.englen.utils.ExperienceControl;
import com.example.englen.utils.LearnWord;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements ChandgeFragment {

    Fragment youFragment;


    @Override
    protected void onPause() {
        super.onPause();

        Time.stopTime = System.currentTimeMillis();
        float a = Time.stopTime - Time.startTime;
        Statistics.addMinutes((int)((a) / 60000));
        ExperienceControl.Save();
        LearnWord.Save();
        LastTopicCovered.Save();
        Statistics.Save();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Time.startTime = System.currentTimeMillis();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Только вертикальная ориентация
        setContentView(R.layout.activity_main);

        // Загрузка всей информации
        Fabric.with(getApplicationContext(), new Crashlytics());
        ExperienceControl.Load(getApplicationContext());
        LearnWord.Load(getApplicationContext());
        LastTopicCovered.Load(getApplicationContext());
        Statistics.Load(getApplicationContext());


        if (savedInstanceState == null) {
            youFragment = new MainFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()          // получаем экземпляр FragmentTransaction
                    .replace(R.id.Fr, youFragment)
                    .commit();
        }
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

    // Обработка нажатия тоски назад
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        OnBackPressedListener backPressedListener = null;
        for (Fragment fragment : fm.getFragments()) {
            if (fragment instanceof OnBackPressedListener) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
