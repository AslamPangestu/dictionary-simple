package com.mvryan.kamus.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mvryan on 01/09/18.
 */

public class Word implements Parcelable {

    private String word;
    private String means;

    public Word(String word, String means) {
        this.word = word;
        this.means = means;
    }


    public String getWord() {
        return word;
    }

    public String getMeans() {
        return means;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setMeans(String means) {
        this.means = means;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.word);
        dest.writeString(this.means);
    }

    public Word() {
    }

    protected Word(Parcel in) {
        this.word = in.readString();
        this.means = in.readString();
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel source) {
            return new Word(source);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
}
