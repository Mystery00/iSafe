package com.mystery0.isafe.BaseClass;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;


import com.mystery0.isafe.Activity.MainActivity;
import com.mystery0.isafe.PublicMethod.LanguageSetting;

import java.util.Locale;

public class OnClick implements DialogInterface.OnClickListener
{
    private int index;
    private Context context;

    public OnClick(int index,Context context)
    {
        this.index=index;
        this.context=context;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(DialogInterface dialogInterface, int i)
    {
        if(i>=0)
        {
            index=i;
        }else
        {
            if (i == DialogInterface.BUTTON_POSITIVE)
            {
                context.getSharedPreferences("language",Context.MODE_PRIVATE)
                        .edit()
                        .putInt("setting",index)
                        .apply();
                switch (index)
                {
                    case 1:
                        LanguageSetting.set(Locale.SIMPLIFIED_CHINESE,context);
                        break;
                    case 2:
                        LanguageSetting.set(Locale.TRADITIONAL_CHINESE,context);
                        break;
                    case 3:
                        LanguageSetting.set(Locale.ENGLISH,context);
                        break;
                    default:
                        LanguageSetting.set(Locale.getDefault(),context);
                        break;
                }

                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        }
    }
}
