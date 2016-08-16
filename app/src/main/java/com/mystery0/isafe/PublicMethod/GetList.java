package com.mystery0.isafe.PublicMethod;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mystery0.isafe.BaseClass.SaveInfo;
import com.mystery0.isafe.ContentProvider.SQLiteHelper;
import com.mystery0.isafe.R;

import java.util.ArrayList;

public class GetList
{
    public static final int ALL=0;
    public static final int COMPUTER=1;
    public static final int INTERNET=2;
    public static final int E_MAIL=3;
    public static final int GAME=4;
    public static final int MEMBER=5;

    public static ArrayList<SaveInfo> getList(Context context,int type)
    {
        ArrayList<SaveInfo> saveInfoArrayList=new ArrayList<>();
        SQLiteHelper sqLiteHelper=new SQLiteHelper(context,context.getString(R.string.data_base_file_name));
        SQLiteDatabase db=sqLiteHelper.getReadableDatabase();
        Cursor cursor;
        if(type==ALL)
        {
            cursor=db.query(context.getString(R.string.data_base_table_name),new String[]{"title", "username", "password"}, null, null, null, null, null);
        }else
        {
            cursor = db.query(context.getString(R.string.data_base_table_name), new String[]{"title", "username", "password","type"}, "type=?", new String[]{""+type}, null, null, null);
        }
        while (cursor.moveToNext())
        {
            SaveInfo saveInfo=new SaveInfo();
            saveInfo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            try
            {
                saveInfo.setUsername(
                        Cryptogram.JX(cursor.getString(cursor.getColumnIndex("username")),
                        Cryptogram.JX(context.getSharedPreferences("key",Context.MODE_PRIVATE).getString("key2","Mystery0"),
                                context.getString(R.string.true_key))));
                saveInfo.setPassword(
                        Cryptogram.JX(cursor.getString(cursor.getColumnIndex("password")),
                        Cryptogram.JX(context.getSharedPreferences("key",Context.MODE_PRIVATE).getString("key2","Mystery0"),
                                context.getString(R.string.true_key))));
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            saveInfoArrayList.add(saveInfo);
        }
        cursor.close();
        return saveInfoArrayList;
    }
}
