package com.mvryan.kamus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by mvryan on 01/09/18.
 */

public class WordDetail extends AppCompatActivity {

    String word, means;
    TextView word_txt, means_txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_detail);

        word_txt = findViewById(R.id.word_title);
        means_txt = findViewById(R.id.word_means);

        word = getIntent().getStringExtra("Word");
        means = getIntent().getStringExtra("Means");

        word_txt.setText(word);
        means_txt.setText(getString(R.string.means)+"\n"+means);
    }
}
