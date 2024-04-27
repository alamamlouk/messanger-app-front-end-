package com.example.messasingchat.Shared;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    private static final String SHARED_PREF_NAME = "login_share_pref";
    private static final String KEY_TOKEN = "token";
    private static final String USER_ID = "userId";
    private static final String USER_NAME="userName";
    private static final String USER_EMAIL="userEmail";
    private static SharedPreferenceManager mInstance;
    private static final String REMEMBER="remember";
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
    public void clearLogin() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(REMEMBER);
        editor.apply();
    }
    public void saveId(String userId){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(USER_ID, userId);
        editor.apply();
    }
    public String getUserId() {
        return mSharedPreferences.getString(USER_ID, null);
    }
    public void clearId(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(USER_ID);
        editor.apply();
    }
    public void saveEmail(String email){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(USER_EMAIL, email);
        editor.apply();
    }    public void saveUserName(String name){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(USER_NAME, name);
        editor.apply();
    }
    public String getUserName() {
        return mSharedPreferences.getString(USER_NAME, null);
    }

    public String getUserEmail() {
        return mSharedPreferences.getString(USER_NAME, null);
    }
    public void saveLogin(boolean remeberMe){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(REMEMBER, remeberMe);
        editor.apply();
    }
    public boolean getLoginRememver() {
        return mSharedPreferences.getBoolean(REMEMBER, false);
    }

}
