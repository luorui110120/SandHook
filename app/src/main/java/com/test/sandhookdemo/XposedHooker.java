package com.test.sandhookdemo;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.util.Log;

import com.kd.utils.LogUtils;
import com.swift.sandhook.xposedcompat.XposedCompat;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class XposedHooker {
    public static void xposedInit(Context context){
        ///// 下面是SandHook 延用的 Xposed 的hook 写法
        //for xposed compat(no need xposed comapt new)
        ///只初始化一次
        LogUtils.e("XposedHookerk, xposedInit");
        if(XposedCompat.isFirstApplication){
            LogUtils.e("XposedHookerk, exit");
            return;
        }
        XposedCompat.cacheDir = context.getCacheDir();

        //for load xp module(sandvxp)
        XposedCompat.context = context;
        XposedCompat.classLoader = context.getClassLoader();
        XposedCompat.isFirstApplication= true;

        XposedHelpers.findAndHookMethod("android.app.Activity", XposedCompat.classLoader,"onCreate", Bundle.class,new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                LogUtils.e( "beforeHookedMethod: " + param.thisObject.getClass().toString() + "." + param.method.getName());
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                LogUtils.e( "afterHookedMethod: " + param.thisObject.getClass().toString() + "." + param.method.getName());
            }
        });


//        XposedHelpers.findAndHookMethod(ContextWrapper.class, "getPackageName", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                Log.e("mlog", "beforeHookedMethod: " + param.method.getName());
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                Log.e("mlog", "afterHookedMethod: " + param.getResult());
//                param.setResult((String)"my.xpsoed.cn");
//            }
//        });

//        XposedHelpers.findAndHookMethod(MainActivity.class, "test", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                ///将这个值设置为true 就不会调用原来的方法了
//                //param.returnEarly = true;
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//            }
//        });

//        XposedBridge.hookAllConstructors(Thread.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                Log.e("mlog", "afterHookedMethod: " + param.method.getName());
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//            }
//        });
    }
}
