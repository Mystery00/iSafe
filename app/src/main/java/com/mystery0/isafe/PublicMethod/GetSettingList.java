package com.mystery0.isafe.PublicMethod;

import android.content.Context;

import com.mystery0.isafe.BaseClass.SettingItem;
import com.mystery0.isafe.R;

import java.util.ArrayList;

public class GetSettingList
{
    public static ArrayList<SettingItem> getList(Context context)
    {
        ArrayList<SettingItem> arrayList=new ArrayList<>();
        SettingItem settingItem;

        settingItem=new SettingItem();
        settingItem.setImg(R.drawable.ic_profile);
        settingItem.setText(context.getString(R.string.text_setting_personal));
        arrayList.add(settingItem);

        settingItem=new SettingItem();
        settingItem.setImg(R.drawable.ic_language);
        settingItem.setText(context.getString(R.string.text_setting_language));
        arrayList.add(settingItem);

        settingItem=new SettingItem();
        settingItem.setImg(R.drawable.ic_backup);
        settingItem.setText(context.getString(R.string.text_setting_backup));
        arrayList.add(settingItem);

        settingItem=new SettingItem();
        settingItem.setImg(R.drawable.ic_delete);
        settingItem.setText(context.getString(R.string.text_setting_delete));
        arrayList.add(settingItem);

        settingItem=new SettingItem();
        settingItem.setImg(R.drawable.ic_share);
        settingItem.setText(context.getString(R.string.text_setting_share));
        arrayList.add(settingItem);

        settingItem=new SettingItem();
        settingItem.setImg(R.drawable.ic_send);
        settingItem.setText(context.getString(R.string.text_setting_send));
        arrayList.add(settingItem);

        settingItem=new SettingItem();
        settingItem.setImg(R.drawable.ic_about);
        settingItem.setText(context.getString(R.string.text_setting_about));
        arrayList.add(settingItem);

        return  arrayList;
    }
}
