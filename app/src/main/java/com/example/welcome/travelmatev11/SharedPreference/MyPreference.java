package com.example.welcome.travelmatev11.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Welcome on 7/16/2016.
 */
public class MyPreference {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    static final String PREFERENCE_NAME = "NotePad";
    static final String USER_NAME = "UserName";
    static final String PASSWORD = "Password";
    static final String EMAIL = "Email";
    static final String GENDER = "Gender";
    static final String PICPATH = "PicPath";
    static final String LATITUDE = "Latitude";
    static final String LONGITUDE = "Longitude";
    private HashMap<String,String> hashMap;


    public MyPreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        hashMap= new HashMap<String, String>();
    }

    public void saveUserData(String userName, String password, String email, String gender, String picPath) {
        editor.putString(USER_NAME,userName);
        editor.putString(PASSWORD,password);
        editor.putString(EMAIL,email);
        editor.putString(GENDER,gender);
        editor.putString(PICPATH,picPath);
        editor.commit();
    }
    public void setUserName(String userName){
        editor.putString(USER_NAME,userName);
        editor.commit();
    }
    public String getEmailId(){
        String emailId= sharedPreferences.getString(EMAIL,"NoEmailId");
        return emailId;
    }

    public static String getPICPATH() {
        return PICPATH;
    }

    public String getUserName(){
        String userName= sharedPreferences.getString(USER_NAME,"NoUserName");
        return userName;
    }

    public void saveLocationData(Double latitude, Double longitude){
        editor.putString(LATITUDE,latitude+"");
        editor.putString(LONGITUDE,longitude+"");
        editor.commit();
    }
    public Double getLatitude(){
        Double latitude=Double.parseDouble(sharedPreferences.getString(LATITUDE,"000000"));
        return latitude;
    }
    public Double getLongitude(){
        Double longitude=Double.parseDouble(sharedPreferences.getString(LONGITUDE,"00000"));
        return longitude;
    }


    public HashMap<String, String> getUserData() {
        String userName= sharedPreferences.getString(USER_NAME,"NoUserName");
        String password= sharedPreferences.getString(PASSWORD,"NoPassword");
        String email= sharedPreferences.getString(EMAIL,"NoEmail");
        String gender= sharedPreferences.getString(GENDER,"NoGender");
        hashMap.put("Usr",userName);
        hashMap.put("Pass",password);
        hashMap.put("Email",email);
        hashMap.put("Gender",gender);
        return hashMap;
    }
}
