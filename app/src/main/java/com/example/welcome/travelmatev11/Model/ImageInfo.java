package com.example.welcome.travelmatev11.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Ashna on 8/26/2016.
 */
public class ImageInfo {
    private int id;
    private String caption;
    private String username;
    private String path;
    private long datetimeLong;
    private int eventId;
    private SimpleDateFormat df = new SimpleDateFormat("MMMM d, yy  h:mm");

    public ImageInfo(String username,String caption, String path, long datetimeLong,int eventId) {
        this.caption = caption;
        this.path = path;
        this.username = username;
        this.eventId = eventId;
        this.datetimeLong = datetimeLong;
    }


    public ImageInfo(int id, String caption, String path, long datetimeLong) {
        this.id = id;
        this.caption = caption;
        this.path = path;
        this.datetimeLong = datetimeLong;
    }

    public ImageInfo() {
    }

    public String getUsername() {
        return username;
    }

    public int getEventId() {
        return eventId;
    }

    public String getCaption() {
        return caption;
    }

    public String getPath() {
        return path;
    }

    public Calendar getDatetime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(datetimeLong);
        return cal;
    }

    public long getDatetimeLong() {
        return datetimeLong;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDatetimeLong(long datetimeLong) {
        this.datetimeLong = datetimeLong;
    }

    public void setDatetime(Calendar datetime) {
        this.datetimeLong = datetime.getTimeInMillis();
    }

    @Override
    public String toString() {
        return "Caption:" + caption + "   " + df.format(getDatetime().getTime());
    }
}
