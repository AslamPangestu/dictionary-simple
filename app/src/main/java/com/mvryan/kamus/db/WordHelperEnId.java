package com.mvryan.kamus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mvryan.kamus.model.Word;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.mvryan.kamus.db.DBContract.KamusColumns.MEANS;
import static com.mvryan.kamus.db.DBContract.KamusColumns.WORD;
import static com.mvryan.kamus.db.DBContract.TABLE_NAME_ENID;

/**
 * Created by mvryan on 02/09/18.
 */

public class WordHelperEnId {

    private Context context;
    private DBHelper dbHelper;

    private SQLiteDatabase database;

    public WordHelperEnId(Context context) {
        this.context = context;
    }

    public WordHelperEnId open() throws SQLException{
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public ArrayList<Word> getDataByWord(String word){
        String result = "";
        Cursor cursor = database.query(TABLE_NAME_ENID,new String[] { WORD,MEANS},WORD+" LIKE ?",new String[]{word},null,null,null,null);
        cursor.moveToFirst();
        ArrayList<Word> arrayList = new ArrayList<>();
        Word wordModel;
        if (cursor.getCount()>0) {
            do {
                wordModel = new Word();
                wordModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                wordModel.setMeans(cursor.getString(cursor.getColumnIndexOrThrow(MEANS)));

                arrayList.add(wordModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Word> getAllData(){
        Cursor cursor = database.query(TABLE_NAME_ENID,null,null,null,null,null,null,null);
        cursor.moveToFirst();
        ArrayList<Word> arrayList = new ArrayList<>();
        Word wordModel;
        if (cursor.getCount()>0) {
            do {
                wordModel = new Word();
                wordModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                wordModel.setMeans(cursor.getString(cursor.getColumnIndexOrThrow(MEANS)));

                arrayList.add(wordModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Word wordModel){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(WORD, wordModel.getWord());
        initialValues.put(MEANS, wordModel.getMeans());
        return database.insert(TABLE_NAME_ENID, null, initialValues);
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransaction(Word wordModel){
        String sql = "INSERT INTO "+TABLE_NAME_ENID+" ("+WORD+", "+MEANS
                +") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, wordModel.getWord());
        stmt.bindString(2, wordModel.getMeans());
        stmt.execute();
        stmt.clearBindings();

    }
}
