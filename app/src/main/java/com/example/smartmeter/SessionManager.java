package com.example.smartmeter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.smartmeter.Models.User;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String Shared_pref_name = "session";
    String SESSION_KEY = "session_user";

    public SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences(Shared_pref_name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void saveSession(User user){
        //save user session after login
        int id = user.getId();
        editor.putInt(SESSION_KEY,id).commit();
    }
    public int getSession(){
        //Return user session
        return  sharedPreferences.getInt(SESSION_KEY, -1);
    }
    public void removeSession(){
        editor.putInt(SESSION_KEY, -1).commit();
    }
}
