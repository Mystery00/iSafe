package com.mystery0.isafe.PublicMethod;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mystery0.isafe.ContentProvider.SQLiteHelper;
import com.mystery0.isafe.R;

public class DeleteData
{
    public static void deleteData(Context context,int id)
    {
        SQLiteDatabase db=new SQLiteHelper(context,context.getString(R.string.data_base_file_name)).getWritableDatabase();
        db.delete(context.getString(R.string.data_base_table_name),"id=?",new String[]{String.valueOf(id)});
    }
}
