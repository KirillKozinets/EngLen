package com.example.englen.Layouts;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.englen.Interface.OnBackPressedListener;
import com.example.englen.Interface.chandgeFragment;
import com.example.englen.R;


public class Word extends Fragment implements OnBackPressedListener {
    chandgeFragment mListener;
    LearnNewWords LNW;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word, container, false);
        Button button = view.findViewById(R.id.NewWord);
        Button button1 = view.findViewById(R.id.but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLearnNewWord(true);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startLearnNewWord(false);
            }
        });
        if(savedInstanceState == null)
            LNW = new LearnNewWords();
        return view;
    }

    private void startLearnNewWord(Boolean isNew)
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isNew",isNew);
        LNW.setArguments(bundle);
        mListener.onCloseFragment(LNW);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof chandgeFragment) {
            mListener = (chandgeFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onBackPressed() {
        chandgeFragment cF  = (chandgeFragment)getActivity();
        cF.onCloseFragment(new MainFragment());
    }
}
