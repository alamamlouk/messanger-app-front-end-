package com.example.messasingchat.Shared;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    private static final String SHARED_PREF_NAME = "login_share_pref";
    private static final String KEY_TOKEN = "token";
    private static SharedPreferenceManager mInstance;
    private final SharedPreferences mSharedPreferences;
    private SharedPreferenceManager(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }
    public static synchronized SharedPreferenceManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreferenceManager(context);
        }
        return mInstance;
    }
    public void saveToken(String token) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public String getToken() {
        return mSharedPreferences.getString(KEY_TOKEN, null);
    }

    public void clearToken() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(KEY_TOKEN);
        editor.apply();
    }
}
