package com.example.welcome.travelmatev11.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 8/4/2016.
 */
public class CustomSharedPreference {

    private SharedPreferences sharedPref;

    public CustomSharedPreference(Context context) {
        sharedPref = context.getSharedPreferences(Helper.PREFS_TAG, Context.MODE_PRIVATE);
    }
    public void setLocationInPreference(String cityName){
        sharedPref.edit().putString(Helper.LOCATION_PREFS, cityName).commit();
    }

    public String getLocationInPreference(){
        return sharedPref.getString(Helper.LOCATION_PREFS, "");
    }
}
