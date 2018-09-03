package com.mvryan.kamus.db;

import android.provider.BaseColumns;

/**
 * Created by mvryan on 02/09/18.
 */

public class DBContract {

    static String TABLE_NAME_ENID = "tbl_kamus_enid";
    static String TABLE_NAME_IDEN = "tbl_kamus_iden";

    static final class KamusColumns implements BaseColumns {

        // column word
        static String WORD = "kata";
        // colume means
        static String MEANS = "arti";

    }

}
