package com.mystery0.isafe.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mystery0.isafe.BaseClass.SaveInfo;
import com.mystery0.isafe.ContentProvider.SQLiteHelper;
import com.mystery0.isafe.R;

import java.util.ArrayList;
import java.util.List;

public class ShowAdapter extends BaseAdapter
{
    private static final int COMPUTER=1;
    private static final int INTERNET=2;
    private static final int E_MAIL=3;
    private static final int GAME=4;
    private static final int MEMBER=5;

    private Context context;
    private List<SaveInfo> saveInfoList;
    private TextView text_title;
    private ImageView img;
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
            img=(ImageView)view.findViewById(R.id.show_img_icon);
        }
        SaveInfo saveInfo=saveInfoList.get(i);
        try
        {
            switch (Integer.parseInt(saveInfo.getType()))
            {
                case COMPUTER:
                    img.setImageResource(R.drawable.img_computer);
                    break;
                case INTERNET:
                    img.setImageResource(R.drawable.img_internet);
                    break;
                case E_MAIL:
                    img.setImageResource(R.drawable.img_email);
                    break;
                case GAME:
                    img.setImageResource(R.drawable.img_game);
                    break;
                case MEMBER:
                    img.setImageResource(R.drawable.img_member);
                    break;
            }
        }catch (NumberFormatException e)
        {
            Log.e("error",e.toString());
            e.printStackTrace();
        }
        text_title.setText(saveInfo.getTitle());
        return view;
    }
}
