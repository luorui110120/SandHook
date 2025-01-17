package com.kd.appcalition;

import android.app.Application;
import android.util.Log;

import com.swift.sandhook.SandHook;
import com.swift.sandhook.annotation.HookClass;
import com.swift.sandhook.annotation.HookMethod;
import com.swift.sandhook.annotation.HookMethodBackup;
import com.swift.sandhook.annotation.ThisObject;
import com.test.sandhookdemo.XposedHooker;

import java.lang.reflect.Method;

@HookClass(Application.class)
public class ApplicationHooker {

    @HookMethodBackup("onCreate")
    static Method onCreateBackup;

    @HookMethod("onCreate")
    public static void MyonCreate(@ThisObject Application thiz) throws Throwable {
        Log.e("mlog", thiz.getClass().toString() + ",call XposedInit_MyonCreate:");
        XposedHooker.xposedInit(thiz);
        SandHook.callOriginByBackup(onCreateBackup, thiz);

    }
}