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

public class orderDatabase extends SQLiteOpenHelper {

    public static final String orderdb="order.db";
    public static final String ordertbl ="orderDB";
    public static final String orderid="Order_ID";
    public static final String phnno="Phone_Number";
    public static final String custname="Customer_Name";
    public static final String design="Design_Number";
    public static final String cloth="Cloth_Type";
    public static final String height="Height";
    public static final String widthc="Width_Chest";
    public static final String widthh="Width_Hip";
    public static final String shoulders="Shoulders";
    public static final String back="Back";
    public static final String hand="Hand";
    public static final String arms="Arms";
    public static final String waist="Waist";
    public static final String addinfo="Additional_Info";
    public static final String totamt="Total_Amount";
    public static final String odate="Order_Date";
    public static final String deliverydate="Delivery_Date";
    public static final String empid="Employee_ID";
    public static final String empname="Employee_Name";
    public static final String status="Status";
    public static final String tailor="Tailor";
    public static final String isle="Isle_Location";

   public orderDatabase(Context context){super(context,orderdb,null,1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+ordertbl+" ("+orderid+" TEXT PRIMARY KEY, "+phnno+" TEXT, "+custname+" TEXT, "+design+" TEXT, "+cloth+" TEXT, "+height+" TEXT, "+widthc+" TEXT, "+widthh+" TEXT, "+shoulders+"  TEXT, "+back+" TEXT, "+hand+" TEXT, "+arms+"  TEXT, "+waist+" TEXT, "+addinfo+" TEXT, "+totamt+" TEXT, "+odate+" TEXT, "+deliverydate+" CHAR(10), "+empid+" TEXT, "+empname+" TEXT, "+status+" TEXT, "+tailor+" TEXT, "+isle+" TEXT)";
        try {
            db.execSQL(query);
        } catch (SQLException e) {
            Log.e("DB_ERROR", "Error in CREATE TABLE: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ordertbl);
        onCreate(db);
    }

    public String generateNextOrderID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + orderid + " FROM " + ordertbl + " ORDER BY " + orderid + " DESC LIMIT 1", null);

        String nextID = "O1";
        if (cursor != null && cursor.moveToFirst()) {
            String lastID = cursor.getString(0); // e.g. "O12"
            try {
                int num = Integer.parseInt(lastID.substring(1)); // remove 'O'
                nextID = "O" + (num + 1);
            } catch (NumberFormatException e) {
                nextID = "O1"; // fallback
            }
            cursor.close();
        }
        return nextID;
    }

    public boolean addneworder(String Phone_Number, String Customer_Name, String Design_Number, String Cloth_Type,
                               String Height, String Width_Chest, String Width_Hip, String Shoulders,
                               String Back, String Hand, String Arms, String Waist, String Addinfo,
                               String Totamt, String Order_Date, String DeliveryDate, String Empid,
                               String Empname, String Status, String Tailor, String Isle) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Generate custom Order_ID (O1, O2, O3...)
        String generatedOrderID = generateNextOrderID();

        contentValues.put(orderid, generatedOrderID);
        contentValues.put(phnno, Phone_Number);
        contentValues.put(custname, Customer_Name);
        contentValues.put(design, Design_Number);
        contentValues.put(cloth, Cloth_Type);
        contentValues.put(height, Height);
        contentValues.put(widthc, Width_Chest);
        contentValues.put(widthh, Width_Hip);
        contentValues.put(shoulders, Shoulders);
        contentValues.put(back, Back);
        contentValues.put(hand, Hand);
        contentValues.put(arms, Arms);
        contentValues.put(waist, Waist);
        contentValues.put(addinfo, Addinfo);
        contentValues.put(totamt, Totamt);
        contentValues.put(odate, Order_Date);
        contentValues.put(deliverydate, DeliveryDate);
        contentValues.put(empid, Empid);
        contentValues.put(empname, Empname);
        contentValues.put(status, Status);
        contentValues.put(tailor, Tailor);
        contentValues.put(isle, Isle);

        long result = db.insert(ordertbl, null, contentValues);
        db.close();

        return result != -1;
    }




    public Cursor viewepecificorders(String phn)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ordertbl+" WHERE Status='Cut' AND Employee_ID="+phn,null);
        return cursor;
    }

    public Cursor vieworderstostitch(String phn)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ordertbl+" WHERE Status='Stitching' AND Tailor="+phn,null);
        return cursor;
    }

    public Cursor viewallorderstostitchTAILOR(String phn)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ordertbl+" WHERE Tailor="+phn,null);
        return cursor;
    }

    public Cursor viewepecificorders2(String phn, String oid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ordertbl +
                " WHERE Status='Cut' AND Employee_ID='" + phn + "' AND Order_ID='" + oid + "'", null);
        return cursor;
    }


    public Cursor viewepecificordersstitch(String oid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ordertbl +
                " WHERE Order_ID='" + oid + "'", null);
        return cursor;
    }

    public Cursor viewepecificorders3(String phn, String oid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ordertbl +
                " WHERE Employee_ID='" + phn + "' AND Order_ID='" + oid + "'", null);
        return cursor;
    }


    public Cursor viewallorders(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT  * FROM "+ordertbl,null);
        return cursor;
    }

    public Cursor viewordersbyEMPID(String phn)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ordertbl+" WHERE Employee_ID="+phn,null);
        return cursor;
    }




    public boolean updatestatus(String oid, String stat, String t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(status, stat);
        contentValues.put(tailor, t);

        // Update only using Order_ID
        int rowsAffected = db.update("orderDB", contentValues, "Order_ID = ?", new String[]{oid});

        return rowsAffected > 0;
    }


    public boolean updatestatusorderStITCH(String oid, String stat, String t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(status, stat);
        contentValues.put(isle, t);

        // Update only using Order_ID
        int rowsAffected = db.update("orderDB", contentValues, "Order_ID = ?", new String[]{oid});

        return rowsAffected > 0;
    }

    public Cursor vieworderbyid(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ordertbl+" WHERE Order_ID="+id,null);
        return  cursor;
    }

    public Cursor vieworderbyphonenumber(String phn)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ordertbl+" WHERE Phone_Number="+phn,null);
        return  cursor;
    }


    public Integer deleteOrder(String oid){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(ordertbl,"Order_ID=?",new String []{oid});
    }
    public List<ReportModel> getOrderReportByDate(String date) {
        List<ReportModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Order_ID, Total_Amount, Order_Date FROM orderDB WHERE Order_Date = ?", new String[]{date});
        while (cursor.moveToNext()) {
            String id = "O" + cursor.getString(0);
            String amt = cursor.getString(1);
            String d = cursor.getString(2);
            list.add(new ReportModel(id, amt, d));
        }
        cursor.close();
        return list;
    }

}
