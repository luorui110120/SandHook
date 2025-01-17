package com.kd.appcalition;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;

import com.kd.utils.LogUtils;
import com.kd.utils.Reflection;
import com.kd.utils.Utils;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.SandHookConfig;
import com.swift.sandhook.wrapper.HookErrorException;
import com.swift.sandhook.xposedcompat.BuildConfig;

public class KdAppcalition extends Application
{
    @Override
    public void onCreate(){
        super.onCreate();
        SandHookConfig.DEBUG = BuildConfig.DEBUG;

        if (Build.VERSION.SDK_INT == 29 && getPreviewSDKInt() > 0) {
            // Android R preview
            SandHookConfig.SDK_INT = 30;
        }
        ////标准的 SandHook 写法
        try {
            SandHook.addHookClass(
                    MainActivityHooker.class);
        } catch (HookErrorException e) {
            e.printStackTrace();
        }



    }
    public static int getPreviewSDKInt() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                return Build.VERSION.PREVIEW_SDK_INT;
            } catch (Throwable e) {
                // ignore
            }
        }
        return 0;
    }
    public static void hookinit(String pkg, String hooksopath){
        LogUtils.e("hookinit1 start01 pkg:" + pkg + ",hooksopath:" + hooksopath);
        SandHookConfig.DEBUG = BuildConfig.DEBUG;
        /// 在android9 以后清除反射的限制
        Reflection.unseal();

        //// 设置  libsandhook.so 的加载路径;
//        if(is64()){
//            SandHookConfig.libSandHookPath = "/data/local/tmp/libsandhook_64.so";
//        }
//        else{
//            SandHookConfig.libSandHookPath = "/data/local/tmp/libsandhook_32.so";
//        }
        if(TextUtils.isEmpty(hooksopath)){
            SandHookConfig.libSandHookPath = "/data/data/" + pkg + "/libsandhook.so";
            if(false == Utils.fileIsExists(SandHookConfig.libSandHookPath)){
                SandHookConfig.libSandHookPath = "/data/data/" + pkg + "/_____libsandhook.so";
            }
        }
        else{
            SandHookConfig.libSandHookPath = hooksopath;
        }


        if (Build.VERSION.SDK_INT == 29 && getPreviewSDKInt() > 0) {
            // Android R preview
            SandHookConfig.SDK_INT = 30;
        }
        ////标准的 SandHook 写法
        try {
            SandHook.addHookClass(ApplicationHooker.class);
//            SandHook.addHookClass(MainActivityHooker.class);
//            SandHook.addHookClass(ActivityHooker.class);

        } catch (HookErrorException e) {
            e.printStackTrace();
        }
        LogUtils.e( "hookinit end ");
    }
    public static boolean is64() {
        String arch = System.getProperty("os.arch");
        if (arch != null && arch.contains("64")) {
            return true;
        } else {
            return false;
        }
    }
}
