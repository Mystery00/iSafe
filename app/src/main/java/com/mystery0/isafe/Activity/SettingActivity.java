package com.mystery0.isafe.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mystery0.isafe.Adapter.SettingItemAdapter;
import com.mystery0.isafe.PublicMethod.CopyFile;
import com.mystery0.isafe.PublicMethod.DeleteFile;
import com.mystery0.isafe.PublicMethod.ExitApplication;
import com.mystery0.isafe.PublicMethod.GetPath;
import com.mystery0.isafe.PublicMethod.GetSettingList;
import com.mystery0.isafe.R;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;
import java.io.IOException;

public class SettingActivity extends AppCompatActivity
{
    private Tencent tencent;
    private Toolbar toolbar;
    private ListView listView;
    private CoordinatorLayout coordinatorLayout;
    private static final int REQUEST_BACKUP = 1111;
    private static final int REQUEST_RESTORE = 2222;
    private static final int REQUEST_DELETE = 3333;

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
        listView = (ListView) findViewById(R.id.listView);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        SettingItemAdapter adapter = new SettingItemAdapter(SettingActivity.this, GetSettingList.getList(SettingActivity.this));
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
                    case 2://Backup
                        new AlertDialog.Builder(SettingActivity.this)
                                .setTitle(R.string.text_setting_backup)
                                .setMessage(R.string.dialog_backup_message)
                                .setIcon(R.drawable.ic_backup)
                                .setNegativeButton("Backup", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        showFileChooser(REQUEST_BACKUP);
                                    }
                                })
                                .setPositiveButton("Restore", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        showFileChooser(REQUEST_RESTORE);
                                    }
                                })
                                .show();
                        break;
                    case 3://Delete
                        new AlertDialog.Builder(SettingActivity.this)
                                .setTitle(R.string.text_setting_delete)
                                .setIcon(R.drawable.ic_delete)
                                .setMessage(R.string.dialog_delete_message)
                                .setNegativeButton("Cancel",null)
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        DeleteFile.delete(new File(getString(R.string.data_base_table_path)));
                                        Snackbar.make(coordinatorLayout,getString(R.string.snack_bar_complete_delete),Snackbar.LENGTH_SHORT)
                                                .show();
                                    }
                                })
                                .show();

                        break;
                    case 4://Share With Friends
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
                    case 5://Feed Back
                        startActivity(new Intent(SettingActivity.this, FeedBackActivity.class));
                        break;
                    case 6://About Us
                        new AlertDialog.Builder(SettingActivity.this)
                                .setView(R.layout.dialog_about_us)
                                .setNegativeButton("Ok", null)
                                .show();
                        break;
                }
            }
        });
    }

    /**
     * 调用文件选择软件来选择文件
     **/
    private void showFileChooser(int type)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath())), "*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try
        {
            startActivityForResult(Intent.createChooser(intent, getString(R.string.title_choose_file)), type);
        } catch (android.content.ActivityNotFoundException ex)
        {
            Snackbar.make(coordinatorLayout, getString(R.string.snack_bar_choose), Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 根据返回选择的文件，来进行上传操作
     **/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case REQUEST_BACKUP:
                    try
                    {
                        if (CopyFile.fileCopy(getString(R.string.data_base_table_path), GetPath.getSavePath(GetPath.getPath(this, data.getData()))))
                        {
                            Snackbar.make(coordinatorLayout, getString(R.string.snack_bar_done_backup), Snackbar.LENGTH_SHORT)
                                    .show();
                        } else
                        {
                            Snackbar.make(coordinatorLayout, getString(R.string.snack_bar_failed_backup), Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (IOException e)
                    {
                        Log.e("error", e.toString());
                        e.printStackTrace();
                    }
                    break;
                case REQUEST_RESTORE:
                    try
                    {
                        if (CopyFile.fileCopy(GetPath.getPath(this, data.getData()),getString(R.string.data_base_table_path)))
                        {
                            Snackbar.make(coordinatorLayout, getString(R.string.snack_bar_done_restore), Snackbar.LENGTH_SHORT)
                                    .show();
                        } else
                        {
                            Snackbar.make(coordinatorLayout, getString(R.string.snack_bar_failed_restore), Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (IOException e)
                    {
                        Log.e("error", e.toString());
                        e.printStackTrace();
                    }
            break;
        }
    }
}
}
