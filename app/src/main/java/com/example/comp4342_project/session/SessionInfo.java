package com.example.comp4342_project.session;

import android.content.Context;
import android.content.SharedPreferences;

//Used as session

public class SessionInfo {
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    //Share preferences name
    static final String SHARED_PREF_NAME = "session";
    //Key name which is used to point to user id
    static final String SESSION_USERID = "session_userID";

    static final String FIRST_NAME = "first_name";
    static final String LAST_NAME = "last_name";
    static final String GENDER = "gender";
    static final String EMAIL = "email";
    static final String PHONE = "phone";
    static final String ROOM = "room";
    static final String BUILDING = "building";
    static final String DISTRICT = "district";
    static final String BIRTH_DATE = "birth_date";

    public SessionInfo(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //To save user id into share preferences
    public static void saveUserID(int id){
        editor.putInt(SESSION_USERID,id).commit();
    }

    //To get user id from share preferences
    public static int returnUserID(){
        return sharedPreferences.getInt(SESSION_USERID, -1);
    }

    //To remove user id from share preferences

    //To remove user id from share preferences
    public void removeSession() {
        editor.putInt(SESSION_USERID, -1).commit();
        editor.putString(FIRST_NAME, null).commit();
        editor.putString(LAST_NAME, null).commit();
        editor.putString(GENDER, null).commit();
        editor.putString(EMAIL, null).commit();
        editor.putString(PHONE, null).commit();
        editor.putString(ROOM, null).commit();
        editor.putString(BUILDING, null).commit();
        editor.putString(DISTRICT, null).commit();
        editor.putString(BIRTH_DATE, null).commit();
    }

    public static void saveInformation(String[] info) {
        editor.putString(FIRST_NAME, info[0]).commit();
        editor.putString(LAST_NAME, info[1]).commit();
        editor.putString(GENDER, info[2]).commit();
        editor.putString(EMAIL, info[3]).commit();
        editor.putString(PHONE, info[4]).commit();
        editor.putString(ROOM, info[5]).commit();
        editor.putString(BUILDING, info[6]).commit();
        editor.putString(DISTRICT, info[7]).commit();
        editor.putString(BIRTH_DATE, info[8]).commit();
    }

    public static String[] loadInformation() {
        String[] string = new String[9];
        string[0] = sharedPreferences.getString(FIRST_NAME, null);
        string[1] = sharedPreferences.getString(LAST_NAME, null);
        string[2] = sharedPreferences.getString(GENDER, null);
        string[3] = sharedPreferences.getString(EMAIL, null);
        string[4] = sharedPreferences.getString(PHONE, null);
        string[5] = sharedPreferences.getString(ROOM, null);
        string[6] = sharedPreferences.getString(BUILDING, null);
        string[7] = sharedPreferences.getString(DISTRICT, null);
        string[8] = sharedPreferences.getString(BIRTH_DATE, null);
        return string;
    }
}
