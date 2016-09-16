package com.example.welcome.travelmatev11.SharedPreference;


public class Helper {

    public static final String MANAGER_CITY = "Change City";

    public static final String LOCATION_ERROR_MESSAGE = "Input field must be filled";

    public static final String LOCATION_VALIDATION_MESSAGE = "You have not entered a correct city name";

    public static final String PREFS_TAG = "prefs";

    public static final String LOCATION_PREFS = "location_prefs";


    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
