package com.example.finalyearproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class empdatabase extends SQLiteOpenHelper {
    public static final String empdb="employeedatabase.db";
    public static final String emptbl="Employee_Database";
    public static final String ename="Employee_Name";
    public static final String ephn="Phone_Number";
    public static final String edj="Date_of_Joining";
    public static final String etype="Employee_Type";
    public static final String esal="Employee_Salary";
    public static final String eage="Employee_Age";
    public static final String epass="Employee_Password";

    public empdatabase(Context context){super(context,empdb,null,1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+emptbl+"("+ename+" TEXT, "+ephn+" TEXT PRIMARY KEY, "+edj+" TEXT, "+etype+" TEXT, "+esal+" TEXT, "+eage+" TEXT ,"+epass+" TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int olddb, int newdb) {
        db.execSQL("DROP TABLE IF EXISTS "+emptbl);
        onCreate(db);
    }

    public boolean addnewemployees(String Employee_Name, String Phone_Number, String Date_of_Joining, String Employee_Type, String Employee_Salary, String Employee_Age, String Employee_Password){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ename,Employee_Name);
        contentValues.put(ephn,Phone_Number);
        contentValues.put(edj,Date_of_Joining);
        contentValues.put(etype,Employee_Type);
        contentValues.put(esal,Employee_Salary);
        contentValues.put(eage,Employee_Age);
        contentValues.put(epass,Employee_Password);

        long result=db.insert(emptbl,null,contentValues);
        db.close();
        if(result==-1)
            return false;
        else
            return true;
    }




    public String getpasssalesman (String Phone_Number){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+emptbl+" WHERE "+ephn+"='"+Phone_Number+"' AND "+etype+"='Sales_Man'",null);
        cursor.moveToFirst();
        String Password=cursor.getString(6);
        db.close();
        cursor.close();
        return Password;
    }

    public String getpasstailor(String Phone_Number){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+emptbl+" WHERE "+ephn+"='"+Phone_Number+"' AND "+etype+"='Tailor'",null);
        cursor.moveToFirst();
        String Password=cursor.getString(6);
        db.close();
        cursor.close();
        return Password;
    }





    Cursor allempdata(){
        String query="SELECT * FROM "+emptbl;
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor cursor=null;
        if(db !=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }



    public Cursor viewepecificempdata(String phn)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+emptbl+" WHERE Phone_Number="+phn,null);
        return cursor;
    }
    public Cursor getEmployeeById(String empId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Employee_Name,Employee_Type,Phone_Number FROM Employee_Database WHERE Phone_Number = ?", new String[]{empId});
    }

    public List<String> getempname() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT Employee_Name FROM Employee_Database WHERE Employee_Type='Tailor'", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));  // 0 = first column in result
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public Map<String, String> getTailorNamePhoneMap() {
        Map<String, String> namePhoneMap = new LinkedHashMap<>();  // Keeps insertion order
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT Employee_Name, Phone_Number FROM Employee_Database WHERE Employee_Type = 'Tailor'", null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String phone = cursor.getString(1);
                namePhoneMap.put(name, phone);  // key: name, value: phone
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return namePhoneMap;
    }

    public boolean updateEmployeePasswordByID(String empID, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(epass, newPassword); // Set new password

        // Update password where Phone_Number (Employee ID) matches
        int rowsAffected = db.update(emptbl, contentValues, ephn + " = ?", new String[]{empID});

        return rowsAffected > 0; // return true if update succeeded
    }




}



