package com.example.welcome.travelmatev11.DBCRUD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.welcome.travelmatev11.DBHelper.DataBaseHelper;
import com.example.welcome.travelmatev11.Model.EventInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 22-Aug-16.
 */
public class EventManager {
    private DataBaseHelper helper;
    private SQLiteDatabase database;
    private Context context;

    public EventManager(Context context) {
        this.context = context;
        helper = new DataBaseHelper(context);
    }
    private void open() {
        database = helper.getWritableDatabase();
    }

    private void close() {
        helper.close();
    }

    public boolean addEventInfo(EventInfo eventInfo) {

        this.open();

        ContentValues cv = new ContentValues();

        cv.put(DataBaseHelper.COL_USERNAME, eventInfo.getUserName());
        cv.put(DataBaseHelper.COL_EVENTNAME, eventInfo.getEventName());
        cv.put(DataBaseHelper.COL_EVENTBUDGET, eventInfo.getEventBudget());
        cv.put(DataBaseHelper.COL_EVENTFROMDate, eventInfo.getEventDateFrom());
        cv.put(DataBaseHelper.COL_EVENTTODate,eventInfo.getEventDateTo());

        long inserted = database.insert(DataBaseHelper.TABLE_EVENTLIST, null, cv);
        this.close();

        database.close();

        if (inserted > 0) {
            return true;
        } else return false;
    }

    public boolean updateEventInfo(EventInfo eventInfo) {
        this.open();
        Log.d("1username:",eventInfo.getUserName());
        Log.d("1eventname:",eventInfo.getEventName());
        Log.d("1budget:",eventInfo.getEventBudget()+"");
        Log.d("1dateFrom:",eventInfo.getEventDateFrom());
        Log.d("1to:",eventInfo.getEventDateTo());
        Log.d("1Id:",eventInfo.getEventId()+"");
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COL_USERNAME, eventInfo.getUserName());
        cv.put(DataBaseHelper.COL_EVENTNAME, eventInfo.getEventName());
        cv.put(DataBaseHelper.COL_EVENTBUDGET, eventInfo.getEventBudget());
        cv.put(DataBaseHelper.COL_EVENTFROMDate, eventInfo.getEventDateFrom());
        cv.put(DataBaseHelper.COL_EVENTTODate, eventInfo.getEventDateTo());

        int updated = database.update(DataBaseHelper.TABLE_EVENTLIST, cv, DataBaseHelper.COL_ID + " = " + eventInfo.getEventId(), null);
        this.close();
        if (updated > 0) {
            return true;
        } else
            return false;
    }

   public ArrayList<EventInfo> getEventInfo(String userName) {

        this.open();
        ArrayList<EventInfo> eventList = new ArrayList<EventInfo>();

        Cursor cursor = database.query(DataBaseHelper.TABLE_EVENTLIST, new String[]
                        {DataBaseHelper.COL_ID, DataBaseHelper.COL_USERNAME, DataBaseHelper.COL_EVENTNAME, DataBaseHelper.COL_EVENTFROMDate,DataBaseHelper.COL_EVENTTODate,
                                DataBaseHelper.COL_EVENTBUDGET },
                DataBaseHelper.COL_USERNAME + "=?",
                new String[] { String.valueOf(userName) }, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_ID));

                userName = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_USERNAME));
                String COL_EVENTNAME = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_EVENTNAME));
                String COL_EVENTFROMDate = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_EVENTFROMDate));
                String COL_EVENTTODate = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_EVENTTODate));
                Double COL_EVENTBUDGET = cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.COL_EVENTBUDGET));

                Log.d("CHKCOL_EVENTNAME:",COL_EVENTNAME);
                Log.d("CHKCOL_EVENTID:",id+"");
                Log.d("CHKCOL_EVENTFROMDate:",COL_EVENTFROMDate+"");
                Log.d("CHKCOL_EVENTTODate:",COL_EVENTTODate+"");
                Log.d("CHKCOL_EVENTBUDGET:",COL_EVENTBUDGET+"");
                Log.d("CHKuserName:",userName);
                Log.d("CHKID:",id+"");
                EventInfo eventInfo = new EventInfo(id,userName,COL_EVENTNAME,COL_EVENTBUDGET,COL_EVENTFROMDate,COL_EVENTTODate );
                eventList.add(eventInfo);
                cursor.moveToNext();
            }
            this.close();
        }
        this.close();
        return eventList;
    }

    public ArrayList<EventInfo> getAllEventInfo() {

        this.open();

           ArrayList<EventInfo> eventList = new ArrayList<EventInfo>();

        Cursor cursor = database.query(DataBaseHelper.TABLE_EVENTLIST, null, null, null, null, null, null);

       /* Cursor cursor = database.query(DataBaseHelper.TABLE_USERINFO, new String[]
                        { DataBaseHelper.COL_EMAIL, DataBaseHelper.COL_PASSWORD, DataBaseHelper.COL_USERNAME,DataBaseHelper.COL_GENDER },
                 null, null, null, null);*/


        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_ID));

                String username = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_USERNAME));
                Double COL_EVENTBUDGET =cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.COL_EVENTBUDGET));
                String COL_EVENTNAME = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_EVENTNAME));
                String COL_EVENTFROMDate =cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_EVENTFROMDate));
                String COL_EVENTTODate = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_EVENTTODate));

                Log.d("CHKCOL_EVENTNAME:",COL_EVENTNAME);
                Log.d("CHKCOL_EVENTFROMDate:",COL_EVENTFROMDate+"");
                Log.d("CHKCOL_EVENTTODate:",COL_EVENTTODate);
                Log.d("CHKCOL_EVENTBUDGET:",COL_EVENTBUDGET+"");
                Log.d("CHKuserName:",username);
                Log.d("CHKID:",id+"");
                EventInfo eventInfo = new EventInfo(username,COL_EVENTNAME,COL_EVENTBUDGET,COL_EVENTFROMDate,COL_EVENTTODate );
                eventList.add(eventInfo);
                cursor.moveToNext();
            }
            this.close();
        }
        return eventList;

    }

    public int getEventBudget(int eventId) {

        this.open();
        int eventBudget = 0;
        Cursor cursor = database.query(DataBaseHelper.TABLE_EVENTLIST, new String[]
                        {DataBaseHelper.COL_EVENTBUDGET },
                DataBaseHelper.COL_ID + " = " + eventId , null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            eventBudget = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_EVENTBUDGET));
                Log.d("CHKCOL_EVENTBUDGET:",eventBudget+"");

                cursor.moveToNext();
            this.close();
        }
        this.close();
        return eventBudget;
    }
}
