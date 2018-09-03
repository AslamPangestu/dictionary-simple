package com.mvryan.kamus.utils.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mvryan.kamus.R;
import com.mvryan.kamus.model.Word;
import com.mvryan.kamus.utils.listener.WordListener;

import java.util.ArrayList;

/**
 * Created by mvryan on 03/09/18.
 */

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private Context context;
    private ArrayList<Word> words;
    private WordListener wordListener;

    public ArrayList<Word> getWords() {
        return words;
    }

    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }

    public WordAdapter(Context context, ArrayList<Word> words, WordListener wordListener) {
        this.context = context;
        this.words = words;
        this.wordListener = wordListener;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, final int position) {
        holder.wordTv.setText(getWords().get(position).getWord());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordListener.onClick(words.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return getWords().size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView wordTv;
        public WordViewHolder(View itemView) {
            super(itemView);
            wordTv = itemView.findViewById(R.id.word_tv);
            linearLayout = itemView.findViewById(R.id.word_item);
        }
    }
}
