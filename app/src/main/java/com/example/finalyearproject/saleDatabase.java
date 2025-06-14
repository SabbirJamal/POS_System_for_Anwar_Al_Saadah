package com.example.finalyearproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class saleDatabase extends SQLiteOpenHelper {
    public static final String saledb="sale.db";
    public static  final String saletbl="saleDB";
    public static final String saleid="Sale_ID";
    public static  final String phno="Phone_Number";
    public static final String custname="Customer_Name";
    public static final String orderitems="Ordered_Items";
    public final String totalamt="Total_Amount";
    public final String saledate="Sale_Date";
    public static final String empid="Employee_ID";
    public static final String empname="Employee_Name";

    public saleDatabase(Context context){
        super(context,saledb,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //String query="CREATE TABLE "+saletbl+"("+saleid+" TEXT PRIMARY KEY , "+phno+" TEXT, "+custname+" TEXT, "+orderitems+" VARCHAR(500), "+totalamt+" TEXT, "+saledate+" TEXT, "+empid+" TEXT, "+empname+"TEXT)";
        String query = "CREATE TABLE " + saletbl + "("
                + saleid + " TEXT PRIMARY KEY, "
                + phno + " TEXT, "
                + custname + " TEXT, "
                + totalamt + " TEXT, "
                + saledate + " TEXT, "
                + empid + " TEXT, "
                + empname + " TEXT)";
        try {
            db.execSQL(query);
        } catch (SQLException e) {
            Log.e("DB_ERROR", "Error in CREATE TABLE: " + e.getMessage());
        }
        try {
            db.execSQL(query);
        } catch (SQLException e) {
            Log.e("DB_ERROR", "Error in CREATE TABLE: " + e.getMessage());
        }
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+saletbl);
        onCreate(db);
    }


    public String generateNextSaleID() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + saleid + " FROM " + saletbl + " ORDER BY " + saleid + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        String newID = "S01";
        if (cursor.moveToFirst()) {
            String lastID = cursor.getString(0); // e.g. S05
            int lastNum = Integer.parseInt(lastID.substring(1));
            int nextNum = lastNum + 1;
            newID = String.format("S%02d", nextNum);
        }
        cursor.close();
        return newID;
    }

public boolean addnewsale(String Phone_Number, String Customer_Name, String Total_Amount, String Sale_Date, String Employee_ID, String Employee_Name) {
    SQLiteDatabase db = this.getWritableDatabase();

    // Get last Sale_ID from database
    String lastId = null;
    String query = "SELECT " + saleid + " FROM " + saletbl + " ORDER BY " + saleid + " DESC LIMIT 1";
    Cursor cursor = db.rawQuery(query, null);

    if (cursor.moveToFirst()) {
        lastId = cursor.getString(0); // e.g. "S05"
    }
    cursor.close();

    // Generate new Sale_ID
    String newId = "S01"; // default first ID
    if (lastId != null) {
        // Extract number, increment, format with leading zero
        int num = Integer.parseInt(lastId.substring(1)); // remove 'S'
        num++;
        newId = String.format("S%02d", num); // e.g. "S06"
    }

    ContentValues contentValues = new ContentValues();
    contentValues.put(saleid, newId);
    contentValues.put(phno, Phone_Number);
    contentValues.put(custname, Customer_Name);
    contentValues.put(totalamt, Total_Amount);
    contentValues.put(saledate, Sale_Date);
    contentValues.put(empid, Employee_ID);
    contentValues.put(empname, Employee_Name);

    long result = db.insert(saletbl, null, contentValues);
    db.close();

    return result != -1;
}
    // Report method
    public List<ReportModel> getSaleReportByDate(String date) {
        List<ReportModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Sale_ID, Total_Amount, Sale_Date FROM " + saletbl + " WHERE Sale_Date = ?", new String[]{date});
        while (cursor.moveToNext()) {
            String id = "S" + cursor.getString(0);
            String amt = cursor.getString(1);
            String d = cursor.getString(2);
            list.add(new ReportModel(id, amt, d));
        }
        cursor.close();
        return list;
    }




}
