package com.example.finalyearproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class inventoryDatabase extends SQLiteOpenHelper {

    public static final String invendb="inventory.db";

    //the below table is for adding company's
    public static final String invenacctbl ="iacc_details";
    public static final String iconum="Company_number";
    public static final String icname="company_name";
    public static final String icemail ="company_email";
    public static final String iccname ="contractor_name";


    //the below table is adding stock to inventory
    public static final String inventbl="inventory_details";
    public static final String oid="order_ID";
    public static final String cname="cloth_name";
    public static final String sname="supplier_name";
    public static final String nrolls="number_of_rolls";
    public static final String rlength="each_roll_length";
    public static final String pproll="priceperroll";
    public static final String d="date";
    public static final String totp="total_price";
    public static final String totlength="total_length";

    public inventoryDatabase(Context context){super(context,invendb,null,1);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+invenacctbl+"("+iconum+" TEXT PRIMARY KEY, "+icname+" TEXT, "+ icemail +" TEXT, "+ iccname +" TEXT)";
        db.execSQL(query);
        String query2="CREATE TABLE "+inventbl+" ("+oid+" TEXT PRIMARY KEY, "+iconum+" TEXT, "+cname+" TEXT,"+sname+" TEXT,"+nrolls+" TEXT,"+rlength+" TEXT, "+pproll+" TEXT,"+d+" TEXT,"+totp+" TEXT,"+totlength+" TEXT, FOREIGN KEY ("+iconum+") REFERENCES "+invenacctbl+" ("+iconum+"))";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int olddb, int newdb) {
        db.execSQL("DROP TABLE IF EXISTS "+invenacctbl);
        db.execSQL("DROP TABLE IF EXISTS "+inventbl);
        onCreate(db);

    }

    public boolean addnewinvenacc(String Company_number, String company_name, String company_email,String contractor_name){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(iconum,Company_number);
        contentValues.put(icname, company_name);
        contentValues.put(icemail, company_email);
        contentValues.put(iccname, contractor_name);
        long result=db.insert(invenacctbl,null,contentValues);
        db.close();;
        if(result==-1)
            return false;
            else
                return true;
    }

    public Cursor inventoryaccsdetails(String phn){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+invenacctbl+" WHERE Company_number="+phn,null);
        return cursor;
    }

    public boolean addinventory(String order_ID,String Company_number,String cloth_name,String supplier_name, String number_of_rolls,String each_roll_length,String priceperroll,String date,String total_price,String total_length){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(oid,order_ID);
        contentValues.put(iconum,Company_number);
        contentValues.put(cname,cloth_name);
        contentValues.put(sname,supplier_name);
        contentValues.put(nrolls,number_of_rolls);
        contentValues.put(rlength,each_roll_length);
        contentValues.put(pproll,priceperroll);
        contentValues.put(d,date);
        contentValues.put(totp,total_price);
        contentValues.put(totlength,total_length);
        long result=db.insert(inventbl,null,contentValues);
        db.close();;
        if(result==-1)
            return false;
        else
            return true;
    }

    Cursor allinvenaccdata(){
        String query="SELECT * FROM "+inventbl;
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor cursor=null;
        if(db !=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
    

    public List<String> getCLothname() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT cloth_name FROM inventory_details", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));  // 0 = first column in result
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public Cursor searchInventoryByOrderID(String orderID) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Use a parameterized query to prevent SQL injection
        Cursor cursor = db.rawQuery("SELECT * FROM " + inventbl + " WHERE " + oid + " = ?", new String[]{orderID});

        return cursor;
    }




}
