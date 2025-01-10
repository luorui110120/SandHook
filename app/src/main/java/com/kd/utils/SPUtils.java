package com.kd.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SPUtils {
 
    public final static String spName = "ksblueConfig";
 
    public static void putValue(Context context, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        } else if (value instanceof String) {
            edit.putString(key, (String) value);
        }
        edit.apply();
    }
 
    public static Object getValue(Context context, String key, Object defValue) {
//        if(key.equals("Deviceid")){
//            return "ADEB067E-7061-525E-B91F-A8308CBEC620";
//        }
//        if(key.equals("Globalid")){
//            return "DFPED01CCB1E099F788C5B4EA3105D03D083E8665B3E697EF5E5182C56750811";
//        }
//        if(key.equals("Model")){
//            return "smaliMacBookPro";
//        }
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (Long) defValue);
        } else if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        }
        return null;
    }
 
    public static void clearSP(Context context) {
        context.getSharedPreferences(spName, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
    }
 
    public static void removeSP(Context context, String Key) {
        context.getSharedPreferences(spName, Context.MODE_PRIVATE)
                .edit()
                .remove(Key)
                .apply();
    }
    
    public static Map<String, ?> getAllSP(Context context) {
        return context.getSharedPreferences(spName, Context.MODE_PRIVATE).getAll();
    }

    public static void saveAllDevices(Context context, Map<String, String> maps){
        JSONObject devices_json = new JSONObject();
        try {
            for (String key : maps.keySet()){
                devices_json.put(key, maps.get(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        putValue(context, "allDevices", devices_json.toString());
    }
    public static Map<String, String> getAllDevices(Context context){
        Map<String, String> maps = new HashMap<>();
        String alldevices = (String)getValue(context, "allDevices", "");
        try {

            JSONObject jsonObject = new JSONObject(alldevices);
            Iterator iterator = jsonObject.keys();
            while(iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = jsonObject.getString(key);
                maps.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return maps;
    }
 
}