package com.mystery0.isafe.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mystery0.isafe.Adapter.SettingItemAdapter;
import com.mystery0.isafe.PublicMethod.ExitApplication;
import com.mystery0.isafe.PublicMethod.GetSettingList;
import com.mystery0.isafe.R;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class SettingActivity extends AppCompatActivity
{
    private Tencent tencent;
    private Toolbar toolbar;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initialization();

        monitor();
    }

    private void initialization()
    {
        ExitApplication.getInstance().addActivity(this);
        tencent = Tencent.createInstance(getString(R.string.app_id), this.getApplicationContext());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView=(ListView)findViewById(R.id.listView);

        SettingItemAdapter adapter=new SettingItemAdapter(SettingActivity.this, GetSettingList.getList(SettingActivity.this));
        listView.setAdapter(adapter);

        setSupportActionBar(toolbar);
    }

    private void monitor()
    {
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                switch (i)
                {
                    case 0://Profile
                        break;
                    case 1://Language
                        break;
                    case 2://Share With Friends
                        new AlertDialog.Builder(SettingActivity.this)
                                .setItems(R.array.Share_Menu, new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        final Bundle params = new Bundle();
                                        switch (i)
                                        {
                                            case 0:
                                                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                                                params.putString(QQShare.SHARE_TO_QQ_TITLE, getString(R.string.information_share_title));
                                                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, getString(R.string.information_share_summary));
                                                //params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,);
                                                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, getString(R.string.information_share_url));
                                                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getString(R.string.app_name));
                                                tencent.shareToQQ(SettingActivity.this, params, new IUiListener()
                                                {
                                                    @Override
                                                    public void onComplete(Object o)
                                                    {
                                                        Toast.makeText(SettingActivity.this, getString(R.string.toast_complete_share), Toast.LENGTH_SHORT)
                                                                .show();
                                                    }

                                                    @Override
                                                    public void onError(UiError uiError)
                                                    {
                                                        Log.e("error", uiError.toString());
                                                    }

                                                    @Override
                                                    public void onCancel()
                                                    {
                                                        Toast.makeText(SettingActivity.this, getString(R.string.toast_cancel_share), Toast.LENGTH_SHORT)
                                                                .show();
                                                    }
                                                });
                                                break;
                                            case 1:
                                                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, getString(R.string.information_share_title));//必填
                                                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, getString(R.string.information_share_summary));//选填
                                                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, getString(R.string.information_share_url));//必填
                                                tencent.shareToQzone(SettingActivity.this, params, new IUiListener()
                                                {
                                                    @Override
                                                    public void onComplete(Object o)
                                                    {
                                                        Toast.makeText(SettingActivity.this, getString(R.string.toast_complete_share), Toast.LENGTH_SHORT)
                                                                .show();
                                                    }

                                                    @Override
                                                    public void onError(UiError uiError)
                                                    {
                                                        Log.e("error", uiError.toString());
                                                    }

                                                    @Override
                                                    public void onCancel()
                                                    {
                                                        Toast.makeText(SettingActivity.this, getString(R.string.toast_cancel_share), Toast.LENGTH_SHORT)
                                                                .show();
                                                    }
                                                });
                                                break;
                                        }
                                    }
                                })
                                .show();
                        break;
                    case 3://Feed Back
                        startActivity(new Intent(SettingActivity.this, FeedBackActivity.class));
                        break;
                    case 4://About Us
                        new AlertDialog.Builder(SettingActivity.this)
                                .setView(R.layout.dialog_about_us)
                                .setNegativeButton("Ok", null)
                                .show();
                        break;
                }
            }
        });
    }
}
