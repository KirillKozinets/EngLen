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


public class SearchResultAdapter extends ArrayAdapter<DataObj> {

    public SearchResultAdapter(Context context, ArrayList<DataObj> dataList) {
        super(context, 0, dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DataObj data = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.element_layout, parent, false);
        }

        TextView column1 = convertView.findViewById(R.id.col1);
        TextView column2 = convertView.findViewById(R.id.col2);

        column2.setText((String)data.field1);
        column1.setText((String)data.field2);


        return convertView;
    }
}