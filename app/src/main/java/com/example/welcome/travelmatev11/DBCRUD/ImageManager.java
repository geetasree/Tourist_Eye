package com.example.welcome.travelmatev11.DBCRUD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;

import com.example.welcome.travelmatev11.DBHelper.DataBaseHelper;
import com.example.welcome.travelmatev11.Model.ImageInfo;

import java.util.ArrayList;

/**
 * Created by Ashna on 8/26/2016.
 */
public class ImageManager {
    private DataBaseHelper helper;
    private SQLiteDatabase database;
    private Context context;

    public ImageManager(Context context) {
        this.context = context;
        helper = new DataBaseHelper(context);

    }

    private void open() {

        database = helper.getWritableDatabase();
    }

    private void close() {
        helper.close();
    }

    public boolean addImage(ImageInfo images) {

        this.open();

        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COL_MEMORYCAPTION, images.getCaption());
        cv.put(DataBaseHelper.COL_MEMORYPICPATH, images.getPath());
        cv.put(DataBaseHelper.COL_MEMORYDATE, images.getDatetimeLong());
        cv.put(DataBaseHelper.COL_USERNAME, images.getUsername());
        cv.put(DataBaseHelper.COL_EVENTID, images.getEventId());

        long inserted = database.insert(DataBaseHelper.TABLE_EVENTMEMORY, null, cv);
        this.close();

        database.close();

        if (inserted > 0) {
            return true;
        } else return false;

    }



    public boolean deleteImage(int id) {
        this.open();
        int deleted = database.delete(DataBaseHelper.TABLE_EVENTMEMORY, DataBaseHelper.COL_ID + " = " + id, null);
        this.close();
        if (deleted > 0) {
            return true;
        } else return false;


    }

    public ArrayList<ImageInfo> getAllImages(int eventId) {

        this.open();

        ArrayList<ImageInfo> noteList = new ArrayList<>();

        Cursor cursor = database.query(DataBaseHelper.TABLE_EVENTMEMORY, new String[]{DataBaseHelper.COL_ID, DataBaseHelper.COL_MEMORYCAPTION, DataBaseHelper.COL_MEMORYPICPATH,
                        DataBaseHelper.COL_MEMORYDATE },
                DataBaseHelper.COL_EVENTID + " = " + eventId, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_ID));
                String caption = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_MEMORYCAPTION));
                String path = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_MEMORYPICPATH));
                Long time = cursor.getLong(cursor.getColumnIndex(DataBaseHelper.COL_MEMORYDATE));

                ImageInfo myimage = new ImageInfo(id, caption, path, time);
                noteList.add(myimage);
                cursor.moveToNext();
            }
            this.close();

        }
        return noteList;
    }

    public ImageInfo getImage(int id) {

        this.open();
        Cursor cursor = database.query(DataBaseHelper.TABLE_EVENTMEMORY, new String[]{DataBaseHelper.COL_ID, DataBaseHelper.COL_MEMORYCAPTION, DataBaseHelper.COL_MEMORYPICPATH,
                DataBaseHelper.COL_MEMORYDATE },
                DataBaseHelper.COL_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();

        int mId = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_ID));
        String caption = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_MEMORYCAPTION));
        String path = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_MEMORYPICPATH));
        Long time = cursor.getLong(cursor.getColumnIndex(DataBaseHelper.COL_MEMORYDATE));

        ImageInfo myimage = new ImageInfo(id, caption, path, time);

        this.close();
        return myimage;
    }

}

