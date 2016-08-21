package com.mystery0.isafe.PublicMethod;

import android.content.Context;

import com.mystery0.isafe.R;

public class GetKey
{
    public static String getKey(Context context)
    {
        try
        {
            return Cryptogram.JX(context.getSharedPreferences("key", Context.MODE_PRIVATE).getString("key2", null),Cryptogram.JX(context.getSharedPreferences("key",Context.MODE_PRIVATE).getString("key6",null),context.getString(R.string.app_name)));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
