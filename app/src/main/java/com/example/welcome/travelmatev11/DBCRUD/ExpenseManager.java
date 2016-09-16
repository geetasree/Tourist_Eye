package com.example.welcome.travelmatev11.DBCRUD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.welcome.travelmatev11.DBHelper.DataBaseHelper;
import com.example.welcome.travelmatev11.Model.EventInfo;
import com.example.welcome.travelmatev11.Model.ExpenseInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 22-Aug-16.
 */
public class ExpenseManager {
    private DataBaseHelper helper;
    private SQLiteDatabase database;
    private Context context;

    public ExpenseManager(Context context) {
        this.context = context;
        helper = new DataBaseHelper(context);
    }
    private void open() {
        database = helper.getWritableDatabase();
    }

    private void close() {
        helper.close();
    }

    public boolean addExpenseInfo(ExpenseInfo expenseInfo) {

        this.open();

        ContentValues cv = new ContentValues();

        cv.put(DataBaseHelper.COL_USERNAME, expenseInfo.getUserName());
        cv.put(DataBaseHelper.COL_EVENTID, expenseInfo.getEventId());
        cv.put(DataBaseHelper.COL_EXPENSENAME, expenseInfo.getExpenseTitle());
        cv.put(DataBaseHelper.COL_EXPENSEAMOUNT, expenseInfo.getExpenseAmount());

        long inserted = database.insert(DataBaseHelper.TABLE_EVENTEXPENSE, null, cv);
        this.close();

        database.close();

        if (inserted > 0) {
            return true;
        } else return false;
    }

    public void getExpenseInfo(String userName) {

        this.open();

        Cursor cursor = database.query(DataBaseHelper.TABLE_EVENTEXPENSE, new String[]
                        { DataBaseHelper.COL_USERNAME, DataBaseHelper.COL_EVENTID, DataBaseHelper.COL_EXPENSENAME,DataBaseHelper.COL_EXPENSEAMOUNT },
                DataBaseHelper.COL_USERNAME + "=?",
                new String[] { String.valueOf(userName) }, null, null, null, null);

        /*String selectQuery = "SELECT eventName FROM event_list WHERE userName=?";
        Cursor cursor = database.rawQuery(selectQuery, new String[] { userName });*/

        cursor.moveToFirst();
        userName = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_USERNAME));
        int COL_EVENTID = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_EVENTID));
        String COL_EXPENSENAME = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_EXPENSENAME));
        Double COL_EXPENSEAMOUNT = cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.COL_EXPENSEAMOUNT));
        Log.d("COL_EVENTNAME:",COL_EVENTID+"");
        Log.d("COL_EVENTFROMDate:",COL_EXPENSENAME);
        Log.d("COL_EVENTBUDGET:",COL_EXPENSEAMOUNT+"");
        Log.d("userName:",userName);
        //    UserInfo userinfo = new UserInfo(userName, password,email,gender);
        this.close();
        // return userinfo;
    }

    public int getExpenseTotal(int eventId) {

        this.open();
        int expenseTotal=0;
        Cursor cursor = database.query(DataBaseHelper.TABLE_EVENTEXPENSE, new String[]
                        {DataBaseHelper.COL_EXPENSEAMOUNT },
                DataBaseHelper.COL_EVENTID + " = " + eventId, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                expenseTotal += cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_EXPENSEAMOUNT));
                cursor.moveToNext();
            }

        }
        this.close();
        return expenseTotal;
    }

    public ArrayList<ExpenseInfo> getAllExpenseInfo() {

        this.open();

           ArrayList<ExpenseInfo> expenseList = new ArrayList<ExpenseInfo>();

        Cursor cursor = database.query(DataBaseHelper.TABLE_EVENTEXPENSE, null, null, null, null, null, null);

       /* Cursor cursor = database.query(DataBaseHelper.TABLE_USERINFO, new String[]
                        { DataBaseHelper.COL_EMAIL, DataBaseHelper.COL_PASSWORD, DataBaseHelper.COL_USERNAME,DataBaseHelper.COL_GENDER },
                 null, null, null, null);*/


        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_ID));

                String userName = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_USERNAME));
                int COL_EVENTID = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_EVENTID));
                String COL_EXPENSENAME = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_EXPENSENAME));
                Double COL_EXPENSEAMOUNT = cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.COL_EXPENSEAMOUNT));

                Log.d("CHKCOL_EVENTNAME:",userName);
                Log.d("CHKCOL_EVENTFROMDate:",COL_EVENTID+"");
                Log.d("CHKCOL_EVENTTODate:",COL_EXPENSENAME);
                Log.d("CHKCOL_EVENTBUDGET:",COL_EXPENSEAMOUNT+"");
                Log.d("CHKID:",id+"");
                ExpenseInfo expenseInfo = new ExpenseInfo(userName,COL_EVENTID,COL_EXPENSENAME,COL_EXPENSEAMOUNT);
                expenseList.add(expenseInfo);
                cursor.moveToNext();
            }
            this.close();
        }
        return expenseList;

    }


}
