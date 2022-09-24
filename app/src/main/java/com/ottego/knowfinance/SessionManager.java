package com.ottego.knowfinance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ottego.knowfinance.Model.SessionModel;

public class SessionManager {
    public static final String KEY_ID = "id";
    public static final String KEY_FIRST_NAME = "name";
    public static final String KEY_lAST_NAME = "name";
    public static final String KEY_MOB = "mob";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_ROLE = "role";
    public static final String KEY_LOGIN_TOKEN = "token";
    public static final String KEY_USERNAME = "token";

    public static final String REGISTER_TOKEN = "token";




    private static final String PREF_NAME = "userData";
    private static final String IS_LOGIN = "isLogin";
    private static SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createSession(SessionModel model) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, model.user.username);
        editor.putString(KEY_LOGIN_TOKEN, model.token);
        editor.putString(KEY_MOB, model.user.first_name);
        editor.putString(KEY_EMAIL, model.user.email);
        editor.putString(KEY_lAST_NAME, model.user.last_name);
        editor.putString(KEY_MOB, model.user.mobile_no);

        editor.commit();
        //  Utils.sendDeviceId ( _context );
    }


    public void clearSession() {
        editor.clear();
        editor.commit();
    }
    public void logoutUser() {
        clearSession();
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);

    }
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String getId() {
        return pref.getString(KEY_ID, null);
    }

    public String getFirstName() {
        return pref.getString(KEY_FIRST_NAME, null);
    }

    public String getLastName() {
        return pref.getString(KEY_lAST_NAME, null);
    }

    public String getMob() {
        return pref.getString(KEY_MOB, null);
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, null);
    }

    public String getUserLoginToken() {
        return pref.getString(KEY_LOGIN_TOKEN, "");
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, "");
    }

    public void createSessionToken(String token) {
        editor.putString(REGISTER_TOKEN, token);
        editor.commit();
    }

    public String getUserToken() {
        return pref.getString(REGISTER_TOKEN, "");
    }
//
//    public void createSession(SessionModel model) {
//        editor.putBoolean(IS_LOGIN, true);
//        editor.putString(LOGIN_TOKEN,model.token);
//        editor.commit();
//    }
//
//    public String getUserLoginToken() {
//        return pref.getString(LOGIN_TOKEN, "");
//    }
//
//    public void createSessionUsername(String username) {
//        editor.putString(USERNAME, username);
//        editor.commit();
//    }
//
//    public String getUsername() {
//        return pref.getString(USERNAME, "");
//    }
}
