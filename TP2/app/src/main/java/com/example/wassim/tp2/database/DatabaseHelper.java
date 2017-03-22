package com.example.wassim.tp2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gamyot on 2017-03-22.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.GroupTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.UserTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.RoleTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.PlaceTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.ScoreTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.EventTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DatabaseContract.GroupTable.DELETE_TABLE);
        db.execSQL(DatabaseContract.UserTable.DELETE_TABLE);
        db.execSQL(DatabaseContract.RoleTable.DELETE_TABLE);
        db.execSQL(DatabaseContract.PlaceTable.DELETE_TABLE);
        db.execSQL(DatabaseContract.ScoreTable.DELETE_TABLE);
        db.execSQL(DatabaseContract.EventTable.DELETE_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
