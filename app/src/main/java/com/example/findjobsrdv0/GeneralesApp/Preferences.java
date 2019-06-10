package com.example.findjobsrdv0.GeneralesApp;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    public static final String Preferences = "com.example.findjobsrdv0";
    public static final String Preference_button = "Estado_button";

    public static void Guardar_estado_button(Context c, boolean b, String Key) {
        SharedPreferences preferences = c.getSharedPreferences(Preferences, c.MODE_PRIVATE);
        preferences.edit().putBoolean(Key, b).apply();
    }

    public static boolean Obtener_estado_button(Context c, String Key){
        SharedPreferences preferences = c.getSharedPreferences(Preferences, c.MODE_PRIVATE);
        return preferences.getBoolean(Key, false);
    }
}
