package com.gorkarevilla.mubalooteam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Instance of the SQLite
 *
 * @Author Gorka Revilla
 */
public class MubalooReaderDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Mubaloo.db";
    public static final int DATABASE_VERSION = 1;


    public MubalooReaderDbHelper(Context context){
        super(context,
                DATABASE_NAME,//String name
                null,//factory
                DATABASE_VERSION//int version
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Member Table
        db.execSQL(MubalooSQLite.CREATE_MEMBER_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Not necessary
    }
}
