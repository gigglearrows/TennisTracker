package com.example.android.tennistracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    private static final String TAG = "DatabaseHandler";

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "TennisMatches";

    // Table name
    private static final String TABLE_MATCH = "Match";

    // Score Table Columns names
    private static final String KEY_ID_MATCH = "_id";
    private static final String KEY_FINISHED = "match_finished";
    private static final String KEY_NAMEPLAYERA = "name_playerA";
    private static final String KEY_SETPLAYERA = "set_playerA";
    private static final String KEY_SETPOINTSPLAYERA = "setpoints_playerA";
    private static final String KEY_POINTSPLAYERA = "points_playerA";
    private static final String KEY_NAMEPLAYERB = "name_playerB";
    private static final String KEY_SETPLAYERB = "set_playerB";
    private static final String KEY_SETPOINTSPLAYERB = "setpoints_playerB";
    private static final String KEY_POINTSPLAYERB = "points_playerB";
    private Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // App context
        this.context = context.getApplicationContext();
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MATCH_TABLE = "CREATE TABLE " + TABLE_MATCH + "("
                + KEY_ID_MATCH + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_FINISHED + " INTEGER DEFAULT 0, "
                + KEY_NAMEPLAYERA + " TEXT DEFAULT '" + this.context.getString(R.string.player_a_name) + "', "
                + KEY_SETPLAYERA + " TEXT, "
                + KEY_SETPOINTSPLAYERA + " INTEGER DEFAULT 0, "
                + KEY_NAMEPLAYERB + " TEXT DEFAULT '" + this.context.getString(R.string.player_b_name) + "', "
                + KEY_SETPLAYERB + " TEXT, "
                + KEY_SETPOINTSPLAYERB + " INTEGER DEFAULT 0" + ")";

        db.execSQL(CREATE_MATCH_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH);

        // Create tables again
        onCreate(db);
    }

    // Adding match to database
    public void addMatch(boolean isFinished, String namePlayerA, ArrayList<Integer> setA, int setPointsA, String namePlayerB, ArrayList<Integer> setB, int setPointsB) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();

            String jStringA = new JSONArray(setA).toString();
            String jStringB = new JSONArray(setB).toString();
            values.put(KEY_FINISHED, isFinished ? 1 : 0); // game finished or not
            values.put(KEY_NAMEPLAYERA, namePlayerA); // playerA name
            values.put(KEY_SETPLAYERA, jStringA); // setA value
            values.put(KEY_SETPOINTSPLAYERA, setPointsA);
            values.put(KEY_NAMEPLAYERB, namePlayerB); // playerB name
            values.put(KEY_SETPLAYERB, jStringB); // setB value
            values.put(KEY_SETPOINTSPLAYERB, setPointsB);

            // Inserting Values
            db.insertOrThrow(TABLE_MATCH, null, values);

            db.close();
        } catch (Exception e) {
            Log.e(TAG, e + " ");
        }
    }
/*
    // Getting All Matches
    public String[][] getAllMatches() {

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MATCH;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        int i = 0;

        String[][] data = new String[cursor.getCount()][];

        while (cursor.moveToNext()) {
            data[i][0] = cursor.getString(0);


            i++;
        }
        cursor.close();
        db.close();
        // return score array
        return data;
    }*/

    public ArrayList<String[]> getAllMatches() {
        ArrayList<String[]> array_list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_MATCH;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String s[] = {
                    cursor.getString(cursor.getColumnIndex(KEY_ID_MATCH)),
                    cursor.getString(cursor.getColumnIndex(KEY_FINISHED)),
                    cursor.getString(cursor.getColumnIndex(KEY_NAMEPLAYERA)),
                    cursor.getString(cursor.getColumnIndex(KEY_SETPLAYERA)),
                    cursor.getString(cursor.getColumnIndex(KEY_SETPOINTSPLAYERA)),
                    cursor.getString(cursor.getColumnIndex(KEY_NAMEPLAYERB)),
                    cursor.getString(cursor.getColumnIndex(KEY_SETPLAYERB)),
                    cursor.getString(cursor.getColumnIndex(KEY_SETPOINTSPLAYERB))
            };
            array_list.add(s);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return array_list;
    }
}