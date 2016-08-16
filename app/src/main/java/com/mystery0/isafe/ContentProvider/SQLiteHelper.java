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
                "create table app (id integer,title text,username integer primary key autoincrement,mm integer,password text)"
        );
        sqLiteDatabase.execSQL(
                "create table src (id integer,version integer primary key autoincrement,username text,key text,kk integer)"
        );
        sqLiteDatabase.execSQL(
                "create table kk (id integer primary key autoincrement,title text,username text,password text,type integer)"
        );
        sqLiteDatabase.execSQL(
                "create table username (id integer,app text,username text,password integer primary key autoincrement)"
        );
        sqLiteDatabase.execSQL(
                "create table password (id integer,title integer primary key autoincrement,key integer,username text,password text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
