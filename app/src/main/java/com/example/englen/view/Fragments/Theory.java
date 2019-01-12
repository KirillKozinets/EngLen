package com.example.englen.view.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.R;


public class Theory extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_HTML = "param1";
    private static final String ARG_BDNAME = "param2";

    private ChandgeFragment CF;

    private String HTML;
    private String DBname;

    public static Theory newInstance(String DBname , String HTML) {
        Theory fragment = new Theory();
        Bundle args = new Bundle();
        args.putString(ARG_BDNAME, DBname);
        args.putString(ARG_HTML, HTML);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            HTML = getArguments().getString(ARG_HTML);
            DBname = getArguments().getString(ARG_BDNAME);
        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theory, container, false);

        Button next = view.findViewById(R.id.next);
        WebView htmlView = view.findViewById(R.id.theory);
        htmlView.getSettings().setJavaScriptEnabled(true);

        htmlView.loadDataWithBaseURL("", HTML, "text/html", "UTF-8", "");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CF.onCloseFragment(TestTheory.newInstance(DBname));
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CF = (ChandgeFragment) context;
    }
}
