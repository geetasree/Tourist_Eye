package com.example.welcome.travelmatev11.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BITM Trainer - 401 on 7/10/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

   private static final String DATABASE_NAME = "TravelMonitor";
   private static final int DATABASE_VERSION = 1;

    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "userName";// common for 4 table
    public static final String COL_PASSWORD = "password";
    public static final String COL_GENDER = "gender";
    public static final String COL_EMAIL = "userEmail";
    public static final String COL_PICPATH = "picpath";

    public static final String TABLE_USERINFO = "user_info";

    public static final String COL_EVENTNAME = "eventName";
    public static final String COL_EVENTFROMDate = "eventFromDate";
    public static final String COL_EVENTTODate = "eventToDate";
    public static final String COL_EVENTBUDGET = "eventBudget";

    public static final String TABLE_EVENTLIST = "event_list";


    public static final String COL_MEMORYPICPATH = "memoryPicPath";
    public static final String COL_MEMORYCAPTION = "memoryCaption";
    public static final String COL_MEMORYDATE = "memoryDate";
    public static final String COL_EVENTID = "eventId"; // common for 2 table

    public static final String TABLE_EVENTMEMORY = "event_memory";

    public static final String COL_EXPENSENAME = "expenseName";
    public static final String COL_EXPENSEAMOUNT = "expenseAmount";

    public static final String TABLE_EVENTEXPENSE = "event_expense";


   private static final String CREATE_USERINFO_TABLE = " CREATE TABLE " + TABLE_USERINFO +
            "( " + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERNAME + " TEXT, " +
           COL_PASSWORD + " TEXT, " + COL_GENDER + " TEXT, " + COL_EMAIL + " TEXT, " + COL_PICPATH + " TEXT )";

    private static final String CREATE_EVENTLIST_TABLE = " CREATE TABLE " + TABLE_EVENTLIST +
            "( " + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERNAME + " TEXT, " + COL_EVENTNAME +
            " TEXT, " + COL_EVENTFROMDate + " TEXT, " + COL_EVENTTODate + " TEXT, " + COL_EVENTBUDGET + " INTEGER )";

    private static final String CREATE_EVENTMEMORY_TABLE = " CREATE TABLE " + TABLE_EVENTMEMORY +
            "( " + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERNAME + " TEXT, " + COL_MEMORYPICPATH + " TEXT, "
            + COL_MEMORYCAPTION + " TEXT, " + COL_MEMORYDATE + " TEXT, " + COL_EVENTID + " INTEGER )";

    private static final String CREATE_EVENTEXPENSE_TABLE = " CREATE TABLE " + TABLE_EVENTEXPENSE +
            "( " + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERNAME + " TEXT, " + COL_EVENTID + " INTEGER, " +
            COL_EXPENSENAME + " TEXT, " + COL_EXPENSEAMOUNT + " INTEGER )";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_USERINFO_TABLE);
        sqLiteDatabase.execSQL(CREATE_EVENTLIST_TABLE);
        sqLiteDatabase.execSQL(CREATE_EVENTMEMORY_TABLE);
        sqLiteDatabase.execSQL(CREATE_EVENTEXPENSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       /* sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERINFO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTLIST);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTMEMORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTEXPENSE);*/
    }
}
