package com.mystery0.isafe.ContentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by myste on 2016-8-14-0014.
 *
 */
public class SQLiteHelper extends SQLiteOpenHelper
{
    public SQLiteHelper(Context context, String name)
    {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(
                "create table if not exists app (id integer primary key autoincrement,title text,username integer primary key autoincrement,password text)"
        );
        sqLiteDatabase.execSQL(
                "create table if not exists src (id integer primary key autoincrement,version integer primary key autoincrement,username text,key text)"
        );
        sqLiteDatabase.execSQL(
                "create table if not exists kk (id integer primary key autoincrement,title text,username text,password text)"
        );
        sqLiteDatabase.execSQL(
                "create table if not exists username (id integer primary key autoincrement,app text,username text,password integer primary key autoincrement)"
        );
        sqLiteDatabase.execSQL(
                "create table if not exists password (id integer primary key autoincrement,title integer primary key autoincrement,username text,password text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
