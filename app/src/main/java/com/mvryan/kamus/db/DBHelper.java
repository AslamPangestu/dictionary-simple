package com.mvryan.kamus.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.mvryan.kamus.db.DBContract.KamusColumns.MEANS;
import static com.mvryan.kamus.db.DBContract.KamusColumns.WORD;
import static com.mvryan.kamus.db.DBContract.TABLE_NAME_ENID;
import static com.mvryan.kamus.db.DBContract.TABLE_NAME_IDEN;

/**
 * Created by mvryan on 02/09/18.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbkamus";

    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static String CREATE_TBL_KAMUS_ENID = "create table "+TABLE_NAME_ENID+
            " ("+
            WORD+" text not null, " +
            MEANS+" text not null);";

    public static String CREATE_TBL_KAMUS_IDEN = "create table "+TABLE_NAME_IDEN+
            " ("+
            WORD+" text not null, " +
            MEANS+" text not null);";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TBL_KAMUS_ENID);
        db.execSQL(CREATE_TBL_KAMUS_IDEN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_ENID);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_IDEN);
        onCreate(db);
    }
}
