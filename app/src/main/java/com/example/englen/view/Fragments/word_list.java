package com.example.englen.view.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.englen.Data.DataBase.DataBaseHelper;
import com.example.englen.Data.DataBase.ReadFromDataBase;
import com.example.englen.Interface.ChandgeFragment;
import com.example.englen.R;
import com.example.englen.utils.Adapters.DataObj;
import com.example.englen.utils.Adapters.SearchResultAdapter;
import com.example.englen.utils.LearnWord;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class word_list extends Fragment {

    ChandgeFragment Fragment;
    LearnNewWords LNW = new LearnNewWords();
    @BindView(R.id.audio)
    Button audio;
    Unbinder unbinder;

    public static word_list newInstance() {
        word_list fragment = new word_list();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private String[] convertStr(String[][] input, String[] id, int q) {
        String[] result = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = input[i][Integer.parseInt(id[i]) + q];
        }
        return result;
    }

    private String[] convertStr(String[][] input, int id) {
        String[] result = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = input[i][id];
        }
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_word_list, container, false);

        Button button = view.findViewById(R.id.NewWord);
        Button button1 = view.findViewById(R.id.but);
        ListView list2 = view.findViewById(R.id.list2);
        audio = view.findViewById(R.id.audio);

        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment.onCloseFragment(new AuditoryDictation());
            }
        });
        String[][] BD = ReadFromDataBase.readSpecificAllFromBD(new DataBaseHelper(getContext()), "TaskAnswersList", "Learn", "TRUE");
        String[] str = convertStr(BD, convertStr(BD, 6), 1);
        String[] translate = convertStr(BD, 8);
        String[][] result = new String[2][str.length];
        result[0] = str;
        result[1] = translate;

        ArrayList<DataObj> list = new ArrayList<>();

        for (int i = 0; i < str.length && i < LearnWord.getCurrentID(); i++)
            list.add(new DataObj(str[i], translate[i]));

        ArrayAdapter<DataObj> adapter2 = new SearchResultAdapter(getContext(), list);

        list2.setAdapter(adapter2);

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
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void startLearnNewWord(Boolean isNew) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isNew", isNew);
        LNW.setArguments(bundle);
        Fragment.onCloseFragment(LNW);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment = (ChandgeFragment) context;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
