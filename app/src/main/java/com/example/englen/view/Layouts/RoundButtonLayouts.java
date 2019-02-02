package com.example.englen.view.Layouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.englen.R;
import com.example.englen.utils.ItemTheory;

import java.util.ArrayList;

public class RoundButtonLayouts extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ItemTheory> objects;

    public RoundButtonLayouts(Context context, ArrayList<ItemTheory> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;

        if (view == null) {
            view = lInflater.inflate(R.layout.roundbuttonlayouts, parent, false);
        }

        ItemTheory p = getProduct(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.text)).setText(p.getText());

        return view;
    }

    // товар по позиции
    ItemTheory getProduct(int position) {
        return (ItemTheory)getItem(position);
    }

}