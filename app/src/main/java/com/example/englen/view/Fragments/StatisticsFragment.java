package com.example.englen.view.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.englen.R;
import com.example.englen.utils.Adapters.DataStatistic;
import com.example.englen.utils.Adapters.StatisticsAdapter;
import com.example.englen.utils.LearnWord;
import com.example.englen.utils.Statistics.Statistics;

import java.util.ArrayList;

public class StatisticsFragment extends Fragment {


    public StatisticsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        ListView listView = view.findViewById(R.id.statistics);

        int[] array2 = Statistics.toArray();
        String[] array1 = new String[]{
                "Потрачено ",
                "Изучено ",
                "Повторено ",
                "Верно пройдено ",
                "Верно повторено ",
                "Верно пройдено ",
                "Уровень "
    };
        String[] array3 = new String[]{
                " минут",
                " слов",
                " слов",
                " тестов",
                "% слов",
                "% тестов",
                ""
        };

        ArrayList<DataStatistic> list = new ArrayList<>();

        for(int i = 0; i < array1.length; i++)
            list.add(new DataStatistic(array1[i],array2[i] , array3[i]));

        StatisticsAdapter adapter2  = new StatisticsAdapter(getContext(), list);

        listView.setAdapter(adapter2);

        return view;
    }

}
