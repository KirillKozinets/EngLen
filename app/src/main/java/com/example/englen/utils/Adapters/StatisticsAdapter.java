package com.example.englen.utils.Adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.englen.R;

import java.util.ArrayList;

public class StatisticsAdapter extends ArrayAdapter<DataStatistic>{
        public StatisticsAdapter(Context context, ArrayList<DataStatistic> dataList) {
            super(context, 0, dataList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            DataStatistic data = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.statistics_element_layout, parent, false);
            }

            TextView column1 =  convertView.findViewById(R.id.col1);
            TextView column2 =  convertView.findViewById(R.id.col2);
            TextView column3 =  convertView.findViewById(R.id.col3);
            column1.setGravity(Gravity.LEFT);
            column1.setGravity(Gravity.CENTER);

            column1.setText((String)data.field1);
            column2.setText(Integer.toString((int) data.field2));
            column3.setText((String)data.field3);

            return convertView;
        }
    }