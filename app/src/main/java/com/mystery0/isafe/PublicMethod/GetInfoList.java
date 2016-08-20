package com.mystery0.isafe.PublicMethod;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mystery0.isafe.BaseClass.SaveInfo;
import com.mystery0.isafe.ContentProvider.SQLiteHelper;
import com.mystery0.isafe.R;

import java.util.ArrayList;

public class GetInfoList
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
            cursor=db.query(context.getString(R.string.data_base_table_name),new String[]{"id","title", "username", "password","item_type"}, null, null, null, null, null);
        }else
        {
            cursor = db.query(context.getString(R.string.data_base_table_name), new String[]{"id","title", "username", "password","item_type"}, "item_type=?", new String[]{""+type}, null, null, null);
        }
        while (cursor.moveToNext())
        {
            SaveInfo saveInfo=new SaveInfo();
            saveInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
            saveInfo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            try
            {
                saveInfo.setUsername(
                        Cryptogram.JX(cursor.getString(cursor.getColumnIndex("username")), GetKey.getKey(context)));
                saveInfo.setPassword(
                        Cryptogram.JX(cursor.getString(cursor.getColumnIndex("password")), GetKey.getKey(context)));
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            saveInfo.setType(cursor.getString(cursor.getColumnIndex("item_type")));
            saveInfoArrayList.add(saveInfo);
        }
        cursor.close();
        return saveInfoArrayList;
    }
}
