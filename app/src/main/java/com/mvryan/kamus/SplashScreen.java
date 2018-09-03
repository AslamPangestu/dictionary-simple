package com.mvryan.kamus;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.mvryan.kamus.db.AppPreferences;
import com.mvryan.kamus.db.WordHelperEnId;
import com.mvryan.kamus.db.WordHelperIdEn;
import com.mvryan.kamus.model.Word;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by mvryan on 01/09/18.
 */

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        progressBar = findViewById(R.id.progress_bar);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        WordHelperEnId wordHelperEnId;
        WordHelperIdEn wordHelperIdEn;
        AppPreferences appPreferences;
        double currentProgress;
        double maxProgress = 100;

        @Override
        protected void onPreExecute() {
            wordHelperEnId = new WordHelperEnId(SplashScreen.this);
            wordHelperIdEn = new WordHelperIdEn(SplashScreen.this);
            appPreferences = new AppPreferences(SplashScreen.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Boolean firstRun = appPreferences.getFirstRun();

            if (firstRun) {

                ArrayList<Word> wordsEnId = preLoadRaw(R.raw.english_indonesia);
                ArrayList<Word> wordsIdEn = preLoadRaw(R.raw.indonesia_english);

                wordHelperEnId.open();

                currentProgress = 30;
                publishProgress((int) currentProgress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - currentProgress) /
                        (wordsEnId.size()+wordsIdEn.size());

                wordHelperEnId.beginTransaction();

                try {
                    for (Word model : wordsEnId) {
                        wordHelperEnId.insert(model);
                        currentProgress += progressDiff;
                        publishProgress((int) currentProgress);
                    }

                    // Jika semua proses telah di set success maka akan di commit ke database
                    wordHelperEnId.setTransactionSuccess();
                } catch (Exception e) {
                    // Jika gagal maka do nothing
                    Log.e(TAG, "doInBackground: Exception");
                }

                wordHelperEnId.endTransaction();
                wordHelperEnId.close();

                wordHelperIdEn.open();
                wordHelperIdEn.beginTransaction();

                try {

                    for (Word model : wordsIdEn) {
                        wordHelperIdEn.insert(model);
                        currentProgress += progressDiff;
                        publishProgress((int) currentProgress);
                    }
                    wordHelperIdEn.setTransactionSuccess();
                }catch (Exception e){
                    // Jika gagal maka do nothing
                    Log.e(TAG, "doInBackground: Exception");
                }

                wordHelperIdEn.endTransaction();
                wordHelperIdEn.close();
                appPreferences.setFirstRun(false);
                publishProgress((int) maxProgress);

            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);

                        this.wait(2000);
                        publishProgress((int) maxProgress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private ArrayList<Word> preLoadRaw(int repo) {
        ArrayList<Word> words = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            InputStream raw_dict = getResources().openRawResource(repo);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                Word word;

                word = new Word(splitstr[0], splitstr[1]);
                words.add(word);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }
}
