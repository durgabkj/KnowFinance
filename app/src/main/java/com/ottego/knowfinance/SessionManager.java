package com.ottego.knowfinance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionManager {
    public static final String KEY_ID = "id";
    public static final String KEY_FIRST_NAME = "name";
    public static final String KEY_lAST_NAME = "name";
    public static final String KEY_MOB = "mob";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_ROLE = "role";
    public static final String KEY_STATUS = "status";
    public static final String KEY_SECRET = "api_secret";
    public static final String KEY_API = "api_key";


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


//    public void createSession(SessionModel model) {
//        editor.putBoolean(IS_LOGIN, true);
//
//        editor.putString(KEY_ID, model.id);
//        editor.putString(KEY_FIRST_NAME, model.name);
//        editor.putString(KEY_lAST_NAME, model.name);
//        editor.putString(KEY_MOB, model.mob);
//        editor.putString(KEY_EMAIL, model.email);
//        editor.putString(KEY_GENDER, model.gender);
//        editor.putString(KEY_ROLE, model.role);
//        editor.putString(KEY_STATUS, model.status);
//
//        editor.commit();
//        //  Utils.sendDeviceId ( _context );
//    }


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

    public String getMob1() {
        return pref.getString(KEY_MOB, null);
    }



    public String getEmail() {
        return pref.getString(KEY_EMAIL, null);
    }

    public String getGender() {
        return pref.getString(KEY_GENDER, null);
    }

    public String getRole() {
        return pref.getString(KEY_ROLE, null);
    }

    public String getStatus() {
        return pref.getString(KEY_STATUS, null);
    }


}
