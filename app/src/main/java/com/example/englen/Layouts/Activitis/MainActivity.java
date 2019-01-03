package com.example.englen.Layouts.Activitis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.englen.Layouts.Fragments.LearnGrammary;
import com.example.englen.Layouts.Fragments.MainFragment;
import com.example.englen.Layouts.Fragments.TestTheory;
import com.example.englen.Layouts.Fragments.Theory;
import com.example.englen.utils.AnalyticsApplication;
import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.R;
import com.example.englen.utils.ExperienceControl;
import com.example.englen.utils.LearnWord;
import com.example.englen.utils.rememberWord;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class MainActivity extends AppCompatActivity implements ChandgeFragment {

    Fragment youFragment;
    Tracker mTracker;

    @Override
    protected void onPause() {
        super.onPause();

        ExperienceControl.Save();
        LearnWord.Save();
        rememberWord.Save();
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

        setContentView(R.layout.activity_main);

        ExperienceControl.Load(this);
        LearnWord.Load(this);
        rememberWord.Load(this);

        if (savedInstanceState == null) {
            youFragment = new MainFragment();
            Bundle b = new Bundle();
            b.putString("param" +
                    "1","\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<title>HTML код таблицы, примеры</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<table border=\"1\">\n" +
                    "<tr>\n" +
                    "<td>ячейка 1, первый ряд</td>\n" +
                    "<td>ячейка 2, первый ряд</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td>ячейка 1, второй ряд</td>\n" +
                    "<td>ячейка 2, второй ряд</td>\n" +
                    "</tr>\n" +
                    "</table> \n" +
                    "</body>\n" +
                    "</html>");
            youFragment.setArguments(b);
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
