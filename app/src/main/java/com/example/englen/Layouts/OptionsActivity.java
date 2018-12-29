package com.example.englen.Layouts;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.englen.R;

public class OptionsActivity extends AppCompatActivity {

    UsageStatistics US;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        if (savedInstanceState == null) {
            US = new UsageStatistics();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()          // получаем экземпляр FragmentTransaction
                    .replace(R.id.FR, US)
                    .commit();
        }
    }
}
