package com.example.FlowFree.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kristinn on 18.9.2014.
 * Reference: Yngvi-ADBTest
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "FlowsDB";
    public static final int DB_VERSION = 1;

    public static final String TableFlows = "flows";
    public static final String[] TableFlowsCols = { "_id","fid" ,"size","finished", "flow1", "flow2", "flow3", "flow4", "flow5", "flow6" };

    private static final String sqlCreateTableFlows =
                    "CREATE TABLE flows(" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " fid INTEGER NOT NULL," +
                    " size INTEGER," +
                    " finished INTEGER," +
                    " flow1 TEXT," +
                    " flow2 TEXT," +
                    " flow3 TEXT," +
                    " flow4 TEXT," +
                    " flow5 TEXT," +
                    " flow6 TEXT " +
                    ");";

    private static final String sqlDropTableStudents = "DROP TABLE IF EXISTS flows;";

    public DBHelper(Context context) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( sqlCreateTableFlows );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( sqlDropTableStudents );
        onCreate( db );
    }
}