package com.amana.MpestISS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.amana.MpestISS.announcement.model.Count;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "iss";
    private static final String TABLE_TEMP = "Temp_Mas";
    private static final String KEY_ID = "id";
    private static final String KEY_COUNT = "count";

    public DBHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String CREATE_TABLE_TEMP = "CREATE TABLE " + TABLE_TEMP + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_COUNT + " TEXT"+")";


        db.execSQL(CREATE_TABLE_TEMP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if exist

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMP);

        // Create tables again
        onCreate(db);
    }

    public void insertCount(String count){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys

        ContentValues cValues = new ContentValues();

        cValues.put(KEY_COUNT, count);
        db.insert(TABLE_TEMP,null, cValues);
        db.close();
    }

    public List<Count> getTempBranch() {
        List<Count> ItemName_user = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TEMP;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(selectQuery, null);
        try {
            if (cursor1.moveToFirst()) {
                do {
                    Count location1 = new Count(cursor1.getInt(0),
                            cursor1.getString(1));

                    // Adding contact to list
                    ItemName_user.add(location1);

                } while (cursor1.moveToNext());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return ItemName_user;

    }

    public void del_temp()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String delQuery = "DELETE FROM "+ TABLE_TEMP;

        db.execSQL(delQuery);
    }
}