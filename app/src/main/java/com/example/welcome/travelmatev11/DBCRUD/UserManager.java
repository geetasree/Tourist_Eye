package com.example.welcome.travelmatev11.DBCRUD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.welcome.travelmatev11.DBHelper.DataBaseHelper;
import com.example.welcome.travelmatev11.Model.UserInfo;

import java.util.ArrayList;

/**
 * Created by Welcome on 7/18/2016.
 */
public class UserManager {

    private DataBaseHelper helper;
    private SQLiteDatabase database;
    private Context context;

    public UserManager(Context context) {
        this.context = context;
        helper = new DataBaseHelper(context);
    }

    private void open() {
        database = helper.getWritableDatabase();
    }

    private void close() {
        helper.close();
    }

    public boolean addUserInfo(UserInfo userInfo) {

        this.open();

        ContentValues cv = new ContentValues();

        cv.put(DataBaseHelper.COL_USERNAME, userInfo.getUserName());
        cv.put(DataBaseHelper.COL_PASSWORD, userInfo.getPassword());
        cv.put(DataBaseHelper.COL_GENDER, userInfo.getGender());
        cv.put(DataBaseHelper.COL_EMAIL, userInfo.geteMail());
        cv.put(DataBaseHelper.COL_PICPATH,userInfo.getPicpath());

        long inserted = database.insert(DataBaseHelper.TABLE_USERINFO, null, cv);
        this.close();

        database.close();

        if (inserted > 0) {
            return true;
        } else return false;
    }

    public int getUserCount(String userName) {

        this.open();

       /* Cursor cursor = database.query(DataBaseHelper.TABLE_USERINFO, new String[]
                        { DataBaseHelper.COL_EMAIL, DataBaseHelper.COL_PASSWORD, DataBaseHelper.COL_USERNAME,DataBaseHelper.COL_GENDER },
                DataBaseHelper.COL_USERNAME + "=?",
                new String[] { String.valueOf(userName) }, null, null, null, null);*/

        String selectQuery = "SELECT count(id) FROM user_info WHERE userName=?";
        Cursor cursor = database.rawQuery(selectQuery, new String[] { userName });

        cursor.moveToFirst();
        int count= cursor.getColumnCount();
        this.close();
        return count;
    }

    public UserInfo getUserInfo(String userName) {

        this.open();

        Cursor cursor = database.query(DataBaseHelper.TABLE_USERINFO, new String[]
                { DataBaseHelper.COL_EMAIL, DataBaseHelper.COL_PASSWORD, DataBaseHelper.COL_USERNAME,DataBaseHelper.COL_GENDER,DataBaseHelper.COL_PICPATH },
                DataBaseHelper.COL_USERNAME + "=?",
                new String[] { String.valueOf(userName) }, null, null, null, null);

//        String selectQuery = "SELECT userName,password FROM user_info WHERE userName=?";
        //Cursor cursor = database.rawQuery(selectQuery, new String[] { userName });

        cursor.moveToFirst();
        userName = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_USERNAME));
        String password = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_PASSWORD));
        String email = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_EMAIL));
        String gender = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_GENDER));
        String picPath = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_PICPATH));
      //  Log.d("picpath: ",picPath);
        UserInfo userinfo = new UserInfo(userName, password,email,gender,picPath);
        this.close();
        return userinfo;
    }

    public ArrayList<UserInfo> getAllUserInfo() {

        this.open();

        ArrayList<UserInfo> userList = new ArrayList<UserInfo>();

        Cursor cursor = database.query(DataBaseHelper.TABLE_USERINFO, null, null, null, null, null, null);

       /* Cursor cursor = database.query(DataBaseHelper.TABLE_USERINFO, new String[]
                        { DataBaseHelper.COL_EMAIL, DataBaseHelper.COL_PASSWORD, DataBaseHelper.COL_USERNAME,DataBaseHelper.COL_GENDER },
                 null, null, null, null);*/


        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_ID));
                String username = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_PASSWORD));
                String email = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_EMAIL));
                String gender = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_GENDER));

                UserInfo userInfo = new UserInfo(username, password, email,gender);
                userList.add(userInfo);
                cursor.moveToNext();
            }
            this.close();
        }
        return userList;
    }

}
