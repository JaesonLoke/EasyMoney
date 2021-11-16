package com.example.easymoney;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "FinanceDetails.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_finance";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_AMOUNT_TYPE = "amountType";


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
                COLUMN_DATE + " TEXT, " +
                COLUMN_AMOUNT_TYPE + " TEXT); ";
        sqldb.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqldb, int i, int i1) {
        sqldb.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqldb);
    }

    void addFinance(String category, Float amount, String date , String amountType){
        SQLiteDatabase sqldb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_AMOUNT, amount);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_AMOUNT_TYPE, amountType);

        long result = sqldb.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }


    public String getSum() {
        String rv = "0";
        int income = 0;
        int expenses = 0;

         income = Integer.parseInt(getIncome());
         expenses = Integer.parseInt(getExpenses());

        int sum = income - expenses;
        rv = String.valueOf(sum);

        return rv;
    }

    public String getIncome() {
        SQLiteDatabase db = this.getReadableDatabase();
        String rv = "0";
        Cursor res = db.rawQuery( "select (SUM(amount)) from " + TABLE_NAME + " WHERE amountType = 'income'", null );
        if (res.moveToFirst()) {
            rv = res.getString(0);
        }
        res.close();
        if (rv == null){
            rv = "0";
        }
        return rv;
    }

    public String getExpenses() {
        SQLiteDatabase db = this.getReadableDatabase();
        String rv = "0";
        Cursor res = db.rawQuery( "select (SUM(amount)) from " + TABLE_NAME + " WHERE amountType = 'expense'", null );
        if (res.moveToFirst()) {
            rv = res.getString(0);
        }
        res.close();
        if (rv == null){
            rv = "0";
        }
        return rv;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    Cursor readThisMonthData(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String sDate = dtf.format(now);


        String query = "SELECT * FROM " + TABLE_NAME + " WHERE date LIKE '%" + sDate + "' ORDER BY date DESC, _id DESC";
        SQLiteDatabase sqldb = this.getReadableDatabase();

        Cursor cursor = null;
        if(sqldb != null){
            cursor = sqldb.rawQuery(query,null);
        }
        return cursor;
    }

    void updateData(String row_id, String titleEdit, String amountEdit, String dateEdit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CATEGORY, titleEdit);
        cv.put(COLUMN_AMOUNT, amountEdit);
        cv.put(COLUMN_DATE, dateEdit);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to update.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteData(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});

        if(result == -1){
            Toast.makeText(context, "Failed to delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();
        }
    }

}
