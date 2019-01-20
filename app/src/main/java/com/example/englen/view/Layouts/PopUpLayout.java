package com.example.englen.view.Layouts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.englen.R;

public class PopUpLayout extends LinearLayout {

    TextView textButton;

    public PopUpLayout(Context context) {
        super(context);
        initComponent();
    }

    public void chandgeInfo(String text) {
        textButton.setText(text);
    }

    private void initComponent() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.popuplayout, this);
        this.textButton = findViewById(R.id.text);
    }

}
