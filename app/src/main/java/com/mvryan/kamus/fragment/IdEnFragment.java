package com.mvryan.kamus.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mvryan.kamus.R;
import com.mvryan.kamus.WordDetail;
import com.mvryan.kamus.db.WordHelperEnId;
import com.mvryan.kamus.db.WordHelperIdEn;
import com.mvryan.kamus.model.Word;
import com.mvryan.kamus.utils.adapter.WordAdapter;
import com.mvryan.kamus.utils.listener.WordListener;

import java.util.ArrayList;

/**
 * Created by mvryan on 29/08/18.
 */

public class IdEnFragment extends Fragment implements WordListener{

    Button findBtnId;
    EditText wordTxtId;

    WordHelperIdEn wordHelperIdEn;

    RecyclerView recyclerView;
    private ArrayList<Word> words;
    WordAdapter wordAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_iden, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findBtnId = view.findViewById(R.id.find_btn_id);
        wordTxtId = view.findViewById(R.id.word_edt_id);
        recyclerView = view.findViewById(R.id.word_list_en);

        findBtnId.setText(R.string.find_iden);

        findBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = wordTxtId.getText().toString();
                findWord(word);
            }
        });
    }

    public void findWord(String word) {
        wordHelperIdEn = new WordHelperIdEn(getContext());
        wordAdapter = new WordAdapter(getContext(), words, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(wordAdapter);

        wordHelperIdEn.open();
        // Ambil semua data mahasiswa di database
        words = wordHelperIdEn.getDataByWord(word);
        wordHelperIdEn.close();
        wordAdapter.setWords(words);
    }

    @Override
    public void onClick(Word word) {
        Intent intent = new Intent(getActivity(), WordDetail.class);
        intent.putExtra("Word", word.getWord());
        intent.putExtra("Means", word.getMeans());
        startActivity(intent);
    }
}
