/*-----------------------------------------------------------------------------
 - Developed by erwin                                                -
 - Last modified 4/20/19 3:12 PM                                              -
 - Subscribe : https://www.youtube.com/erwin                         -
 - Copyright (c) 2019. All rights reserved                                    -
 -----------------------------------------------------------------------------*/
package com.erelmanagement.registerlogin.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.erelmanagement.registerlogin.home.MainActivityhome;
import com.erelmanagement.registerlogin.login.MainActivity;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String ID = "ID";
    public static final String EMAIL = "EMAIL";
    private ProgressBar loading;

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    editor = sharedPreferences.edit();
    }

    public void createSession(String id, String email ){
        editor.putBoolean(LOGIN, true);
        editor.putString(ID, id);
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN, false);

    }

    public void checkLogin(){

        if (!this.isLogin()){
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((MainActivityhome) context).finish();
        }
    }
    public void checkLoginDetail(){

        if (!this.isLogin()){

            Toast.makeText(context ,"Login Terlebih Dahulu", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((MainActivityhome) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        return user;
    }

    public void logout(){

        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
        ((MainActivityhome)context).finish();
    }
}
