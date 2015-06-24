package com.example.zhenfang.myapplication.utils;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

/**
 * Created with yanghx.
 * User: yanghx
 * Date: 13-1-30
 * Time: 下午6:55
 * Author: yang.hx@gmail.com
 */
public class BBAppResource {
    private static Resources mAppResource;

    private static void __checkResource() {
        if (mAppResource == null) {
            mAppResource = ApplicationInterface.application.getResources();
        }
    }

    public static int color(int colorId) {
        __checkResource();
        return mAppResource.getColor(colorId);
    }


    public static boolean bool(int rId) {
        __checkResource();
        return mAppResource.getBoolean(rId);
    }

    public static float dimension(int fontId) {
        __checkResource();
        return mAppResource.getDimension(fontId);
    }

    public static String string(int stringId) {
        __checkResource();
        return mAppResource.getString(stringId);
    }

    public static int getStringId(String stringName) {
        __checkResource();
        return mAppResource.getIdentifier(stringName, "string", ApplicationInterface.application.getPackageName());
    }
    public static String string(int stringId, Object... formatArgs) {
        __checkResource();
        return mAppResource.getString(stringId, formatArgs);
    }

    public static Drawable drawable(int resourceId) {
        __checkResource();
        return mAppResource.getDrawable(resourceId);
    }

    // note: source should be without postfix, the same rule apply to getDrawableId
    public static Drawable drawable(String source) {
        __checkResource();
        int resId = mAppResource.getIdentifier(source, "drawable", ApplicationInterface.application.getPackageName());
        return drawable(resId);
    }

    public static int getDrawableId(String source) {
        __checkResource();
        return mAppResource.getIdentifier(source, "drawable", ApplicationInterface.application.getPackageName());
    }

    public static int getInt(int resourceId) {
        __checkResource();
        return mAppResource.getInteger(resourceId);
    }

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) ApplicationInterface.application.getSystemService(Application.WINDOW_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            return size.x;
        }

        return wm.getDefaultDisplay().getWidth();
    }

    public static boolean isScreenNarrow() {
        return BBAppResource.getScreenWidth() < 600;
    }

    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) ApplicationInterface.application.getSystemService(Application.WINDOW_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            return size.y;
        }

        return wm.getDefaultDisplay().getHeight();
    }

    public static Locale getLanguageLocale() {
        __checkResource();
        android.content.res.Configuration conf = mAppResource.getConfiguration();
        return conf.locale;
    }

    public static String[] stringArray(int stringArrayId) {
        __checkResource();
        return mAppResource.getStringArray(stringArrayId);
    }

    public static Bitmap bitmap(int resourceId) {
        __checkResource();
        return BitmapFactory.decodeResource(mAppResource, resourceId);
    }

    public static Drawable drawable(Bitmap bitmap) {
        __checkResource();
        return new BitmapDrawable(mAppResource, bitmap);
    }

    public static String loadRawString(int resId) {
        try {
            InputStream is = ApplicationInterface.application.getResources().openRawResource(resId);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                if (is != null)
                    is.close();
            }
            return writer.toString();
        } catch (IOException e) {
        }
        return "";
    }
}
