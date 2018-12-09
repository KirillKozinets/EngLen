package com.example.englen.Layouts;


import android.content.BroadcastReceiver;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.Interface.chandgeFragment;
import com.example.englen.Interface.stateApp;
import com.example.englen.R;
import com.example.englen.utils.ExperienceControl;

public class MainActivity extends AppCompatActivity implements chandgeFragment {

    Fragment youFragment;
    stateApp start;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        start.Close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = new ExperienceControl();
        start.Create();

        if (savedInstanceState == null) {
            youFragment = new MainFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()          // получаем экземпляр FragmentTransaction
                    .replace(R.id.Fr, youFragment)
                    .commit();
        }
    }

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
