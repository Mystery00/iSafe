package com.mystery0.isafe.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mystery0.isafe.BaseClass.SettingItem;
import com.mystery0.isafe.R;

import java.util.List;

public class SettingItemAdapter extends BaseAdapter
{
    private Context context;
    private List<SettingItem> settingItems;
    private ImageView img;
    private TextView text;
    public SettingItemAdapter(Context context,List<SettingItem> settingItems)
    {
        this.context=context;
        this.settingItems=settingItems;
    }
    @Override
    public int getCount()
    {
        return settingItems.size();
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
            view= LayoutInflater.from(context).inflate(R.layout.item_setting,null);
            img=(ImageView)view.findViewById(R.id.item_setting_img);
            text=(TextView)view.findViewById(R.id.item_setting_text);
        }
        img.setImageResource(settingItems.get(i).getImg());
        text.setText(settingItems.get(i).getText());
        return view;
    }
}
