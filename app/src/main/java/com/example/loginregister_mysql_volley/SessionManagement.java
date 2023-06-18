package com.example.loginregister_mysql_volley;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    private static final String PREF_NAME = "SessionPrefs";
    private static final String KEY_USERNAME = "username";
    private static SessionManagement instance;
    private SharedPreferences sharedPreferences;

    private SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SessionManagement getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManagement(context.getApplicationContext());
        }
        return instance;
    }

    public void setUsername(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public boolean isLoggedIn() {
        return sharedPreferences.contains(KEY_USERNAME);
    }

    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USERNAME);
        editor.apply();
    }
}
