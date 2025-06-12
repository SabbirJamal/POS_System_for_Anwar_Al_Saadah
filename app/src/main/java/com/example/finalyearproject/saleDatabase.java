package com.example.finalyearproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        String query="CREATE TABLE "+saletbl+"("+saleid+" INTEGER PRIMARY KEY AUTOINCREMENT, "+phno+" TEXT, "+custname+" TEXT, "+orderitems+" VARCHAR(500), "+totalamt+" TEXT, "+saledate+" TEXT, "+empid+" TEXT, "+empname+"TEXT)";
        try {
            db.execSQL(query);
        } catch (SQLException e) {
            Log.e("DB_ERROR", "Error in CREATE TABLE: " + e.getMessage());
        }
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+saletbl);
    }

    public boolean addnewsale(String Phone_Number,String Customer_Name,String Ordered_Items,String Total_Amount,String Sale_Date,String Employee_ID,String Employee_Name){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(phno,Phone_Number);
        contentValues.put(custname,Customer_Name);
        contentValues.put(orderitems,Ordered_Items);
        contentValues.put(totalamt,Total_Amount);
        contentValues.put(saledate,Sale_Date);
        contentValues.put(empid,Employee_ID);
        contentValues.put(empname,Employee_Name);
        long result=db.insert(saletbl,null,contentValues);
        db.close();;
        if(result==-1)
            return false;
        else
            return true;
    }

}
