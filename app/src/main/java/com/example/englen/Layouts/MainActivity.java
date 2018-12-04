package com.example.englen.Layouts;


import android.content.BroadcastReceiver;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.englen.Interface.chandgeFragment;
import com.example.englen.R;

public class MainActivity extends AppCompatActivity implements chandgeFragment {

    TaskAnswerFragment youFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            youFragment = new TaskAnswerFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()          // получаем экземпляр FragmentTransaction
                    .replace(R.id.Fr, youFragment)
                    .commit();

        }
    }

    @Override
    public void onCloseFragment(Fragment fragment) {
        youFragment = new TaskAnswerFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()          // получаем экземпляр FragmentTransaction
                .replace(R.id.Fr, youFragment)
                .commit();

    }
}
