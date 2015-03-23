package me.sr1.toolkit.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 增强Log类
 * 1. 支持设定log的输出等级，低于指定输出等级的Log将不会被输出。
 * 2. 支持输出log的同时使用Toast弹出提示框，可以通过全局设置开启或关闭Toast功能。
 * 3. 不需要指定TAG，将当前实例传入即可，将取此实例的类名作为TAG输出。
 * 4. 支持在静态方法里使用时，传入类.class，将取类名作为TAG输出
 * 5. 支持在调试模式下输出方法名和行数，idea来自：https://github.com/MustafaFerhan/DebugLog
 * Created by sr1 on 15/3/19.
 */
public class Log {

    public static final int VERBOSE = 1;

    public static final int DEBUG = 2;

    public static final int INFO = 3;

    public static final int WARN = 4;

    public static final int ERROR = 5;

    public static final int ASSERT = 6;

    private static int sLevel = DEBUG;
    private static boolean sIsDebugModel = true;
    private static boolean sIsAllowToast = true;
    private static Context sContext;


    public static void initial(int level, boolean isDebugModel, boolean isAllowToast, Context applicationContext) {
        if (level > 6 || level < 1) {
            return;
        } else {
            sLevel = level;
            sIsDebugModel = isDebugModel;
            sIsAllowToast = isAllowToast;
            sContext = applicationContext;
        }
    }

    public static void v(Class cls, String msg) {
        v(cls, msg, false);
    }

    public static void d(Class cls, String msg) {
        d(cls, msg, false);
    }

    public static void i(Class cls, String msg) {
        i(cls, msg, false);
    }

    public static void w(Class cls, String msg) {
        w(cls, msg, false);
    }

    public static void e(Class cls, String msg) {
        e(cls, msg, false);
    }

    public static void wtf(Class cls, String msg) {
        wtf(cls, msg, false);
    }

    public static void v(Object obj, String msg) {
        v(obj, msg, false);
    }

    public static void d(Object obj, String msg) {
        d(obj, msg, false);
    }

    public static void i(Object obj, String msg) {
        i(obj, msg, false);
    }

    public static void w(Object obj, String msg) {
        w(obj, msg, false);
    }

    public static void e(Object obj, String msg) {
        e(obj, msg, false);
    }

    public static void wtf(Object obj, String msg) {
        wtf(obj, msg, false);
    }

    public static void v(Class cls, String msg, boolean isNeedToast) {
        log(VERBOSE, cls, msg, isNeedToast);
    }

    public static void d(Class cls, String msg, boolean isNeedToast) {
        log(DEBUG, cls, msg, isNeedToast);
    }

    public static void i(Class cls, String msg, boolean isNeedToast) {
        log(INFO, cls, msg, isNeedToast);
    }

    public static void w(Class cls, String msg, boolean isNeedToast) {
        log(WARN, cls, msg, isNeedToast);
    }

    public static void e(Class cls, String msg, boolean isNeedToast) {
        log(ERROR, cls, msg, isNeedToast);
    }

    public static void wtf(Class cls, String msg, boolean isNeedToast) {
        log(ASSERT, cls, msg, isNeedToast);
    }

    public static void v(Object obj, String msg, boolean isNeedToast) {
        log(VERBOSE, obj, msg, isNeedToast);
    }

    public static void d(Object obj, String msg, boolean isNeedToast) {
        log(DEBUG, obj, msg, isNeedToast);
    }

    public static void i(Object obj, String msg, boolean isNeedToast) {
        log(INFO, obj, msg, isNeedToast);
    }

    public static void w(Object obj, String msg, boolean isNeedToast) {
        log(WARN, obj, msg, isNeedToast);
    }

    public static void e(Object obj, String msg, boolean isNeedToast) {
        log(ERROR, obj, msg, isNeedToast);
    }

    public static void wtf(Object obj, String msg, boolean isNeedToast) {
        log(ASSERT, obj, msg, isNeedToast);
    }

    private static void log(int level, Object obj, String msg, boolean isNeedToast) {
        log(level, obj.getClass(), msg, isNeedToast);
    }

    private static void log(int level, Class cls, String msg, boolean isNeedToast) {

        String extraInfo = "";
        if (sIsDebugModel) {
            extraInfo = getDebugInfo(new Throwable().getStackTrace());
        }

        String tag =cls.getSimpleName();
        String message = (msg == null) ? "message is null" : msg;
        message = extraInfo + message;

        if (sLevel <= level) {

            switch (level) {
                case VERBOSE: android.util.Log.v(tag, message); break;
                case DEBUG:   android.util.Log.d(tag, message); break;
                case INFO:    android.util.Log.i(tag, message); break;
                case WARN:    android.util.Log.w(tag, message); break;
                case ERROR:   android.util.Log.e(tag, message); break;
                case ASSERT:  android.util.Log.wtf(tag, message); break;
            }

            if(sIsAllowToast && isNeedToast) {
                makeToast(String.format("[%s] %s", tag, message));
            }
        }
    }

    private static void makeToast(String message) {
        if (sContext != null) {
            Toast.makeText(sContext, message, Toast.LENGTH_SHORT).show();
        } else {
            android.util.Log.w(Log.class.getCanonicalName(), "fail to make toast: context not set.");
        }
    }

    private static String getDebugInfo(StackTraceElement[] elements) {

        for (StackTraceElement element : elements) {
            if (!element.getClassName().equalsIgnoreCase(Log.class.getCanonicalName())) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[");
                stringBuilder.append(element.getMethodName());
                stringBuilder.append(":");
                stringBuilder.append(element.getLineNumber());
                stringBuilder.append("]");
                return stringBuilder.toString();
            }
        }
        return "";
    }
}