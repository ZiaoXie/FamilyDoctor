package com.example.administrator.familydoctor.JiangTang;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017-09-21.
 */

public class ArticleDatabaseHelper extends SQLiteOpenHelper {
    public static final String  TABLE_NAME="jiangtang";
    public static final String CREAT_RECORD="create table IF NOT EXISTS "+
            TABLE_NAME+
            "(id int primary key not null," +
            "title text," +
            "Abstract text)";
    private Context mContext;
    public ArticleDatabaseHelper(Context context, String name,
                                 SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_RECORD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }
}
