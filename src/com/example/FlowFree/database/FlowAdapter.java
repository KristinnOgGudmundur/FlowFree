package com.example.FlowFree.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kristinn on 18.9.2014.
 * Reference: Yngvi-ADBTest
 */
public class FlowAdapter {

    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;

public FlowAdapter(Context c) {
        context = c;
        }

public FlowAdapter openToRead() {
        dbHelper = new DBHelper( context );
        db = dbHelper.getReadableDatabase();
        return this;
        }

public FlowAdapter openToWrite() {
        dbHelper = new DBHelper( context );
        db = dbHelper.getWritableDatabase();
        return this;
        }

public void close() {
        db.close();
        }

public long insertFlows( int fid, int Size, int Finished, String flow1, String flow2, String flow3, String flow4, String flow5, String flow6) {
        String[] cols = DBHelper.TableFlowsCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer)fid).toString() );
        contentValues.put( cols[2], ((Integer)Size).toString() );
        contentValues.put( cols[3], ((Integer)Finished).toString());
        contentValues.put( cols[4], flow1);
        contentValues.put( cols[5], flow2);
        contentValues.put( cols[6], flow3);
        contentValues.put( cols[7], flow4);
        contentValues.put( cols[8], flow5);
        contentValues.put( cols[9], flow6);
        openToWrite();
        long value = db.insert(DBHelper.TableFlows, null, contentValues );
        close();
        return value;
        }

public long updateFinished( int fid, int Finished) {
    String[] cols = DBHelper.TableFlowsCols;
    ContentValues contentValues = new ContentValues();
    contentValues.put(cols[1], ((Integer) fid).toString());
    contentValues.put( cols[3], ((Integer)Finished).toString());
    openToWrite();
    long value = db.update(DBHelper.TableFlows, contentValues, cols[1] + " = " + fid, null );
    close();
    return value;
}

public Cursor queryFlows() {
        openToRead();
        Cursor cursor = db.query( DBHelper.TableFlows,
        DBHelper.TableFlowsCols , null, null, null, null, null);
        return cursor;
        }

public Cursor queryFlows( int fid) {
        openToRead();
        String[] cols = DBHelper.TableFlowsCols;
        Cursor cursor = db.query( DBHelper.TableFlows,
        cols, cols[1] + "" + fid, null, null, null, null);
        return cursor;
        }

public long count() {
        return DatabaseUtils.queryNumEntries(db, "flows");
    }
}