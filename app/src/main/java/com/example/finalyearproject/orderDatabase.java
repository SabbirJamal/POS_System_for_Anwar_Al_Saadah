package com.example.finalyearproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class orderDatabase extends SQLiteOpenHelper {

    public static final String orderdatabase="inventory.db";

    //the below table is for adding company's
    public static final String ordertbl ="orderDB";
    public static final String orderid="Order_ID";
    public static final String phnno="Phone_Number";
    public static final String custname="Customer_Name";
    public static final String design="Design_Number";
    public static final String cloth="Cloth_Type";
    public static final String height="Height";
    public static final String widthc="Width(Chest)";
    public static final String widthh="Width_Hip";
    public static final String shoulders="Shoulders";
    public static final String back="Back";
    public static final String hand="Hand";
    public static final String arms="Arms";
    public static final String waist="Waist";
    public static final String addinfo="Additional_Info";
    public static final String totamt="Total_Amount";
    public static final String advance="Advance_Amount";
    public static final String balance="Balance_Amount";
    public static final String deliverydate="Delivery_Date";
    public static final String empid="Employee_ID";
    public static final String empname="Employee_Name";
    public static final String status="Status";
    public static final String tailor="Tailor";

    public orderDatabase(Context context){
        super(context,orderdatabase,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query="CREATE TABLE "+ordertbl+"("+orderid+" TEXT PRIMARY KEY, "+phnno+" TEXT, "+custname+" TEXT, "+design+" TEXT, "+cloth+" TEXT, "+height+" TEXT, "+widthc+" TEXT, "+widthh+" TEXT, "+shoulders+" TEXT, "+back+" TEXT, "+hand+" TEXT, "+arms+" TEXT, "+waist+" TEXT, "+addinfo+" TEXT, "+totamt+" TEXT, "+advance+" TEXT, "+balance+" TEXT, "+deliverydate+" TEXT, "+empid+" TEXT, "+empname+" TEXT, "+status+" TEXT, "+tailor+" TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ordertbl);
    }
    public boolean addneworder(String Order_ID, String Phone_Number,String Customer_Name,String  Design_Number,String Cloth_Type,String Height,String Widthchest,String Widthhip,String Shoulders,String Back,String Hand,String Arms,String Waist,String Addinfo, String Totamt, String Advanceamt, String Balanceamt,String DeliveryDate, String Empid, String Empname, String Status, String Tailor){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(orderid,Order_ID);
        contentValues.put(phnno,Phone_Number);
        contentValues.put(custname,Customer_Name);
        contentValues.put(design,Design_Number);
        contentValues.put(cloth,Cloth_Type);
        contentValues.put(height,Height);
        contentValues.put(widthc,Widthchest);
        contentValues.put(widthh,Widthhip);
        contentValues.put(shoulders,Shoulders);
        contentValues.put(back,Back);
        contentValues.put(hand,Hand);
        contentValues.put(arms,Arms);
        contentValues.put(waist,Waist);
        contentValues.put(addinfo,Addinfo);
        contentValues.put(totamt,Totamt);
        contentValues.put(advance,Advanceamt);
        contentValues.put(balance,Balanceamt);;
        contentValues.put(deliverydate,DeliveryDate);
        contentValues.put(empid,Empid);
        contentValues.put(empname,Empname);
        contentValues.put(status,Status);
        contentValues.put(tailor,Tailor);
        long result=db.insert(ordertbl,null,contentValues);
        db.close();;
        if(result==-1)
            return false;
        else
            return true;
    }

    public String generateNextOid() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Order_ID FROM orderDB ORDER BY Order_ID DESC LIMIT 1", null);
        String newOid = "oid1";

        if (cursor.moveToFirst()) {
            String lastOid = cursor.getString(0);  // e.g., "oid3"
            int num = Integer.parseInt(lastOid.replace("oid", ""));
            newOid = "oid" + (num + 1);            // e.g., "oid4"
        }

        cursor.close();
        return newOid;
    }

}
