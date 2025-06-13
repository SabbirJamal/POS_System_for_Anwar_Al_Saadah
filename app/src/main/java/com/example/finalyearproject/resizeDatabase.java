package com.example.finalyearproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class resizeDatabase extends SQLiteOpenHelper {
    public static final String resizedb="resize.db";
    public static final String resizetbl ="resizeDB";
    public static final String resizeid="Resize_ID";
    public static final String phnno="Phone_Number";
    public static final String custname="Customer_Name";
    public static final String height="Height";
    public static final String widthc="Width_Chest";
    public static final String widthh="Width_Hip";
    public static final String shoulders="Shoulders";
    public static final String back="Back";
    public static final String hand="Hand";
    public static final String arms="Arms";
    public static final String waist="Waist";
    public static final String totamt="Total_Amount";
    public static final String odate="Order_Date";
    public static final String deliverydate="Delivery_Date";
    public static final String empid="Employee_ID";
    public static final String empname="Employee_Name";
    public static final String status="Status";
    public static final String tailor="Tailor";
    public static final String isle="Isle_Location";

    public resizeDatabase(Context context){super(context,resizedb,null,1);}


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+resizetbl+" ("+resizeid+" INTEGER PRIMARY KEY AUTOINCREMENT, "+phnno+" TEXT, "+custname+" TEXT, "+height+" TEXT, "+widthc+" TEXT, "+widthh+" TEXT, "+shoulders+"  TEXT, "+back+" TEXT, "+hand+" TEXT, "+arms+"  TEXT, "+waist+" TEXT, "+totamt+" TEXT, "+odate+" TEXT, "+deliverydate+" CHAR(10), "+empid+" TEXT, "+empname+" TEXT, "+status+" TEXT, "+tailor+" TEXT, "+isle+" TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+resizetbl);
        onCreate(db);
    }

    public boolean addnewresize(String Phone_Number,String Customer_Name,String Height,String Width_Chest,String Width_Hip,String Shoulders,String Back,String Hand,String Arms,String Waist,String Totamt, String Order_Date,String DeliveryDate, String Empid, String Empname, String Status, String Tailor,String Isle){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(phnno,Phone_Number);
        contentValues.put(custname,Customer_Name);
        contentValues.put(height,Height);
        contentValues.put(widthc,Width_Chest);
        contentValues.put(widthh,Width_Hip);
        contentValues.put(shoulders,Shoulders);
        contentValues.put(back,Back);
        contentValues.put(hand,Hand);
        contentValues.put(arms,Arms);
        contentValues.put(waist,Waist);
        contentValues.put(totamt,Totamt);
        contentValues.put(odate,Order_Date);
        contentValues.put(deliverydate,DeliveryDate);
        contentValues.put(empid,Empid);
        contentValues.put(empname,Empname);
        contentValues.put(status,Status);
        contentValues.put(tailor,Tailor);
        contentValues.put(isle,Isle);
        long result=db.insert(resizetbl,null,contentValues);
        db.close();;
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor viewepecificresize(String phn)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+resizetbl+" WHERE Status='Cut' AND Employee_ID="+phn,null);
        return cursor;
    }

    public Cursor viewepecificresizetostitch(String phn)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+resizetbl+" WHERE Status='Stitching' AND Employee_ID="+phn,null);
        return cursor;
    }


    public Cursor viewepecificresize2(String phn, String oid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + resizetbl +
                " WHERE Status='Cut' AND Employee_ID='" + phn + "' AND Resize_ID='" + oid + "'", null);
        return cursor;
    }

    public Cursor viewresizebyEMPID(String phn)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+resizetbl+" WHERE Employee_ID="+phn,null);
        return cursor;
    }


    public Integer deleteResize(String oid){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(resizetbl,"Resize_ID=?",new String []{oid});
    }

    public boolean updateresizestatus(String oid, String stat, String t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(status, stat);
        contentValues.put(tailor, t);

        // Update only using Order_ID
        int rowsAffected = db.update("resizeDB", contentValues, "Resize_ID = ?", new String[]{oid});

        return rowsAffected > 0;
    }

}
