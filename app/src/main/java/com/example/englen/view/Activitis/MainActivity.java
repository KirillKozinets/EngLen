package com.example.englen.view.Activitis;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.englen.utils.LastTopicCovered;
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

        ExperienceControl.Save();
        LearnWord.Save();
        LastTopicCovered.Save();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        Fabric.with(getApplicationContext(), new Crashlytics());
        ExperienceControl.Load(getApplicationContext());
        LearnWord.Load(getApplicationContext());
        LastTopicCovered.Load(getApplicationContext());


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
}
