package com.example.finalyearproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class resizeDatabase extends SQLiteOpenHelper {
    private SQLiteDatabase database;
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
        String query="CREATE TABLE "+resizetbl+" ("+resizeid+" TEXT PRIMARY KEY, "+phnno+" TEXT, "+custname+" TEXT, "+height+" TEXT, "+widthc+" TEXT, "+widthh+" TEXT, "+shoulders+"  TEXT, "+back+" TEXT, "+hand+" TEXT, "+arms+"  TEXT, "+waist+" TEXT, "+totamt+" TEXT, "+odate+" TEXT, "+deliverydate+" CHAR(10), "+empid+" TEXT, "+empname+" TEXT, "+status+" TEXT, "+tailor+" TEXT, "+isle+" TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+resizetbl);
        onCreate(db);
    }

    public String generateNextResizeID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + resizeid + " FROM " + resizetbl +
                " ORDER BY " + resizeid + " DESC LIMIT 1", null);

        String nextID = "R01";
        if (cursor != null && cursor.moveToFirst()) {
            String lastID = cursor.getString(0); // e.g., "R09"
            try {
                int num = Integer.parseInt(lastID.substring(1)); // Remove 'R'
                num++;
                nextID = String.format("R%02d", num); // Format as R01, R02...
            } catch (NumberFormatException e) {
                nextID = "R01";
            }
            cursor.close();
        }
        return nextID;
    }

    public boolean addnewresize(String Phone_Number, String Customer_Name, String Height, String Width_Chest, String Width_Hip,
                                String Shoulders, String Back, String Hand, String Arms, String Waist, String Totamt,
                                String Order_Date, String DeliveryDate, String Empid, String Empname, String Status,
                                String Tailor, String Isle) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Generate next Resize_ID like R01, R02...
        String newResizeID = generateNextResizeID();
        contentValues.put(resizeid, newResizeID);

        contentValues.put(phnno, Phone_Number);
        contentValues.put(custname, Customer_Name);
        contentValues.put(height, Height);
        contentValues.put(widthc, Width_Chest);
        contentValues.put(widthh, Width_Hip);
        contentValues.put(shoulders, Shoulders);
        contentValues.put(back, Back);
        contentValues.put(hand, Hand);
        contentValues.put(arms, Arms);
        contentValues.put(waist, Waist);
        contentValues.put(totamt, Totamt);
        contentValues.put(odate, Order_Date);
        contentValues.put(deliverydate, DeliveryDate);
        contentValues.put(empid, Empid);
        contentValues.put(empname, Empname);
        contentValues.put(status, Status);
        contentValues.put(tailor, Tailor);
        contentValues.put(isle, Isle);

        long result = db.insert(resizetbl, null, contentValues);
        db.close();

        return result != -1;
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
        Cursor cursor=db.rawQuery("SELECT * FROM "+resizetbl+" WHERE Status='Stitching' AND Tailor="+phn,null);
        return cursor;
    }

    public Cursor viewresizedataforTAILOR(String phn)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+resizetbl+" WHERE  Tailor="+phn,null);
        return cursor;
    }


    public Cursor viewepecificresize2(String phn, String oid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + resizetbl +
                " WHERE Status='Cut' AND Employee_ID='" + phn + "' AND Resize_ID='" + oid + "'", null);
        return cursor;
    }

    public Cursor viewresizedatabyID(String resizeID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + resizetbl + " WHERE " + resizeid + " = ?";
        return db.rawQuery(query, new String[]{resizeID});
    }

    public Cursor viewresizedatabyPhone(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + resizetbl + " WHERE " + phnno + " = ?", new String[]{phone});
    }

    public Cursor viewepecificresize4(String oid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + resizetbl +
                " WHERE Resize_ID='" + oid + "'", null);
        return cursor;
    }

    public Cursor viewepecificresize3(String phn, String oid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + resizetbl +
                " WHERE Employee_ID='" + phn + "' AND Resize_ID='" + oid + "'", null);
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

    public boolean updatestatusRESIZEStITCH(String oid, String stat, String t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(status, stat);
        contentValues.put(isle, t);

        // Update only using Order_ID
        int rowsAffected = db.update("resizeDB", contentValues, "Resize_ID = ?", new String[]{oid});

        return rowsAffected > 0;
    }

    public List<ReportModel> getResizeReportByDate(String date) {
        List<ReportModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Resize_ID, Total_Amount, Order_Date FROM " + resizetbl + " WHERE Order_Date = ?", new String[]{date});
        while (cursor.moveToNext()) {
            String id = "R" + cursor.getString(0);
            String amt = cursor.getString(1);
            String d = cursor.getString(2);
            list.add(new ReportModel(id, amt, d));
        }
        cursor.close();
        return list;
    }



    public List<ReportModel> getResizeReportByDateRange(String startDate, String endDate) {
        List<ReportModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT Resize_ID, Total_Amount, Order_Date FROM resizeDB WHERE Order_Date BETWEEN ? AND ?";
        Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate});
        while (cursor.moveToNext()) {
            String id = cursor.getString(0); // Prefix "R" to ID
            String amt = cursor.getString(1); // Get total amount
            String d = cursor.getString(2); // Get the date
            list.add(new ReportModel(id, amt, d));
        }
        cursor.close();
        return list;
    }

    public int getResizeCountByEmployee(String employeeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM resizeDB WHERE Employee_ID = ?";
        Cursor cursor = db.rawQuery(query, new String[]{employeeId});

        int orderCount = 0;
        if (cursor.moveToFirst()) {
            orderCount = cursor.getInt(0); // Get the first column value (the count)
        }
        cursor.close();

        return orderCount;
    }

    public int getResizeCountByTailor(String employeeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM resizeDB WHERE Tailor = ?";
        Cursor cursor = db.rawQuery(query, new String[]{employeeId});

        int orderCount = 0;
        if (cursor.moveToFirst()) {
            orderCount = cursor.getInt(0); // Get the first column value (the count)
        }
        cursor.close();

        return orderCount;
    }

    public int getTotalResizeInLast30Days(String employeeId) {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + odate + " FROM " + resizetbl + " WHERE " + tailor + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{employeeId});

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar today = Calendar.getInstance();
        Calendar past30Days = Calendar.getInstance();
        past30Days.add(Calendar.DAY_OF_YEAR, -30);

        if (cursor.moveToFirst()) {
            do {
                String dateStr = cursor.getString(0);
                try {
                    Date date = sdf.parse(dateStr);
                    if (date != null && !date.before(past30Days.getTime()) && !date.after(today.getTime())) {
                        count++;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return count;
    }

    public int getTailorTotalResizeInLast30Days(String employeeId) {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + odate + " FROM " + resizetbl + " WHERE " + tailor + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{employeeId});

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar today = Calendar.getInstance();
        Calendar past30Days = Calendar.getInstance();
        past30Days.add(Calendar.DAY_OF_YEAR, -30);

        if (cursor.moveToFirst()) {
            do {
                String dateStr = cursor.getString(0);
                try {
                    Date date = sdf.parse(dateStr);
                    if (date != null && !date.before(past30Days.getTime()) && !date.after(today.getTime())) {
                        count++;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return count;
    }




}
