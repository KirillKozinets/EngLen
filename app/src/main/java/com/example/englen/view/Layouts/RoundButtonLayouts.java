
// Класс для показа информации о теме

package com.example.englen.view.Layouts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.englen.R;

import java.util.ArrayList;

public class RoundButtonLayouts extends LinearLayout {
    Context ctx;
    LayoutInflater lInflater;
    public int id;

    public RoundButtonLayouts(Context context, int id) {
        super(context);
        init(null, 0);
        this.id = id;
    }

    public RoundButtonLayouts(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RoundButtonLayouts(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        LayoutInflater inflater = (LayoutInflater)  getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.roundbuttonlayouts, this);
    }
}