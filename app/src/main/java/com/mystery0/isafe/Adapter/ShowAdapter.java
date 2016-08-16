package com.mystery0.isafe.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mystery0.isafe.BaseClass.SaveInfo;
import com.mystery0.isafe.ContentProvider.SQLiteHelper;
import com.mystery0.isafe.R;

import java.util.ArrayList;
import java.util.List;

public class ShowAdapter extends BaseAdapter
{
    private Context context;
    private List<SaveInfo> saveInfoList;
    private TextView text_title;
    public ShowAdapter(Context context,List<SaveInfo> saveInfoList)
    {
        this.context=context;
        this.saveInfoList=saveInfoList;
    }
    @Override
    public int getCount()
    {
        return saveInfoList.size();
    }

    @Override
    public Object getItem(int i)
    {
        return i;
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if(view==null)
        {
            view= LayoutInflater.from(context).inflate(R.layout.item_list_password,null);
            text_title=(TextView)view.findViewById(R.id.show_text);
        }
        SaveInfo saveInfo=saveInfoList.get(i);
        text_title.setText(saveInfo.getTitle());
        return view;
    }
}
