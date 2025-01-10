package com.kd.utils;

import android.os.Build;

import java.lang.reflect.Method;

public class Reflection {
    private static final int ERROR_EXEMPT_FAILED = -21;
    private static final int ERROR_SET_APPLICATION_FAILED = -20;
    private static final String TAG = "Reflection";
    private static int UNKNOWN;
    private static Object sVmRuntime;
    private static Method setHiddenApiExemptions;
    private static int unsealed;

    static {
        if(Build.VERSION.SDK_INT >= 28) {
            try {
                Class[] v3 = new Class[]{String.class};
                Class[] v6 = new Class[]{String.class, Class[].class};
                Class v0_1 = (Class)Class.class.getDeclaredMethod("forName", v3).invoke(null, "dalvik.system.VMRuntime");
                Method v3_1 = (Method)Class.class.getDeclaredMethod("getDeclaredMethod", v6).invoke(v0_1, "getRuntime", null);
                Object[] v4 = new Object[]{"setHiddenApiExemptions", null};
                v4[1] = new Class[]{String[].class};
                Reflection.setHiddenApiExemptions = (Method)Class.class.getDeclaredMethod("getDeclaredMethod", v6).invoke(v0_1, v4);
                Reflection.sVmRuntime = v3_1.invoke(null);
            }
            catch(Throwable v0) {
                LogUtils.e( "reflect bootstrap failed:"+ v0);
            }
        }

        Reflection.UNKNOWN = 0xFFFFD8F1;
        Reflection.unsealed = Reflection.UNKNOWN;
    }
    public static boolean exempt(String arg2) {
        return Reflection.exempt(new String[]{arg2});
    }

    public static boolean exempt(Object[] arg5) {
        Object v0 = Reflection.sVmRuntime;
        if(v0 != null) {
            Method v2 = Reflection.setHiddenApiExemptions;
            if(v2 != null) {
                try {
                    v2.invoke(v0, arg5);
                }
                catch(Throwable unused_ex) {
                    return false;
                }

                return true;
            }
        }

        return false;
    }

    public static boolean exemptAll() {
        return Reflection.exempt(new String[]{"L"});
    }

    public static int unseal() {
        if(Build.VERSION.SDK_INT < 28) {
            return 0;
        }
        return Reflection.exemptAll() ? 0 : -21;
    }
}
