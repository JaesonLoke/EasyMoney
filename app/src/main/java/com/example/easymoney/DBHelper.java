package com.example.easymoney;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "FinanceDetails.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_finance";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DATE = "date";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_AMOUNT + " NUMERIC, " +
                COLUMN_DATE + " TEXT); ";
        sqldb.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqldb, int i, int i1) {
        sqldb.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqldb);
    }

    void addFinance(String category, Float amount, String date){
        SQLiteDatabase sqldb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_AMOUNT, amount);
        cv.put(COLUMN_DATE, date);
        long result = sqldb.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqldb = this.getReadableDatabase();

        Cursor cursor = null;
        if(sqldb != null){
            cursor = sqldb.rawQuery(query,null);
        }
        return cursor;
    }

}
