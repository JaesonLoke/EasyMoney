package com.example.easymoney;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelperWallet extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "WalletDetails.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_wallet";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DESC = "description";
    private static final String COLUMN_WALLET_TYPE = "walletType";


    public DBHelperWallet(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AMOUNT + " NUMERIC, " +
                COLUMN_DESC + " TEXT, " +
                COLUMN_WALLET_TYPE + " TEXT); ";
        sqldb.execSQL(query);

        ContentValues cv1 = new ContentValues();
        ContentValues cv2 = new ContentValues();
        ContentValues cv3 = new ContentValues();
        ContentValues cv4 = new ContentValues();
        ContentValues cv5 = new ContentValues();
        ContentValues cv6 = new ContentValues();


        cv1.put(COLUMN_TITLE, "Daily Expenses");
        cv1.put(COLUMN_AMOUNT, 0);
        cv1.put(COLUMN_DESC, "-");
        cv1.put(COLUMN_WALLET_TYPE, "saving");

        cv2.put(COLUMN_TITLE, "Investment");
        cv2.put(COLUMN_AMOUNT, 0);
        cv2.put(COLUMN_DESC, "-");
        cv2.put(COLUMN_WALLET_TYPE, "saving");

        cv3.put(COLUMN_TITLE, "Education");
        cv3.put(COLUMN_AMOUNT, 0);
        cv3.put(COLUMN_DESC, "School, Book, Edu...");
        cv3.put(COLUMN_WALLET_TYPE, "saving");

        cv4.put(COLUMN_TITLE, "Social life Expenses");
        cv4.put(COLUMN_AMOUNT, 0);
        cv4.put(COLUMN_DESC, "-");
        cv4.put(COLUMN_WALLET_TYPE, "saving");

        cv5.put(COLUMN_TITLE, "Long Term");
        cv5.put(COLUMN_AMOUNT, 0);
        cv5.put(COLUMN_DESC, "Travel, Iphone Saving...");
        cv5.put(COLUMN_WALLET_TYPE, "saving");

        cv6.put(COLUMN_TITLE, "Donation");
        cv6.put(COLUMN_AMOUNT, 0);
        cv6.put(COLUMN_DESC, "-");
        cv6.put(COLUMN_WALLET_TYPE, "saving");

        sqldb.insert(TABLE_NAME, null, cv2);
        sqldb.insert(TABLE_NAME, null, cv5);
        sqldb.insert(TABLE_NAME, null, cv1);
        sqldb.insert(TABLE_NAME, null, cv3);
        sqldb.insert(TABLE_NAME, null, cv4);
        sqldb.insert(TABLE_NAME, null, cv6);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqldb, int i, int i1) {
        sqldb.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqldb);
    }

    void addWallet(String title, Float amount, String desc , String walletType){
        SQLiteDatabase sqldb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AMOUNT, amount);
        cv.put(COLUMN_DESC, desc);
        cv.put(COLUMN_WALLET_TYPE, walletType);

        long result = sqldb.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }


    public String getSum() {
        String rv = "0";
        int saving = 0;
        int loan = 0;

        saving = Integer.parseInt(getSaving());
        loan = Integer.parseInt(getLoan());

        int sum = saving - loan;
        rv = String.valueOf(sum);

        return rv;
    }

    public String getSaving() {
        SQLiteDatabase db = this.getReadableDatabase();
        String rv = "0";
        Cursor res = db.rawQuery( "select (SUM(amount)) from " + TABLE_NAME + " WHERE walletType = 'saving'", null );
        if (res.moveToFirst()) {
            rv = res.getString(0);
        }
        res.close();
        if (rv == null){
            rv = "0";
        }
        return rv;
    }

    public String getLoan() {
        SQLiteDatabase db = this.getReadableDatabase();
        String rv = "0";
        Cursor res = db.rawQuery( "select (SUM(amount)) from " + TABLE_NAME + " WHERE walletType = 'loan'", null );
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

    void updateData(String row_id, String titleEdit, String amountEdit, String descEdit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, titleEdit);
        cv.put(COLUMN_AMOUNT, amountEdit);
        cv.put(COLUMN_DESC, descEdit);

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
