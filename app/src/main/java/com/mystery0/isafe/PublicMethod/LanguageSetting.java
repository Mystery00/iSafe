package com.mystery0.isafe.PublicMethod;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LanguageSetting
{
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean get(Locale index, Context context)
    {
        Locale currentLocale = context.getResources().getConfiguration().locale;
        return currentLocale.equals(index);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void set(Locale index, Context context)
    {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(index);
        resources.updateConfiguration(config, dm);
    }
}
