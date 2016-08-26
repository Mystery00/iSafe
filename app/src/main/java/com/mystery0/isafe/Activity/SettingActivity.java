package com.mystery0.isafe.Activity;

import android.app.Activity;
import android.content.ClipboardManager;
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

import com.mystery0.isafe.Adapter.SettingItemAdapter;
import com.mystery0.isafe.BaseClass.OnClick;
import com.mystery0.isafe.ContentProvider.SQLiteHelper;
import com.mystery0.isafe.PublicMethod.CopyFile;
import com.mystery0.isafe.PublicMethod.DeleteFile;
import com.mystery0.isafe.PublicMethod.ExitApplication;
import com.mystery0.isafe.PublicMethod.GetPath;
import com.mystery0.isafe.PublicMethod.GetSettingList;
import com.mystery0.isafe.R;

import java.io.File;
import java.io.IOException;


public class SettingActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private ListView listView;
    private CoordinatorLayout coordinatorLayout;
    private static final int REQUEST_BACKUP = 1111;
    private static final int REQUEST_RESTORE = 2222;

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
                switch (i+1)
                {
                    case 1://Language
                        OnClick click=new OnClick(0,getApplicationContext());
                        new AlertDialog.Builder(SettingActivity.this)
                                .setSingleChoiceItems(R.array.language_menu, getSharedPreferences("language",MODE_PRIVATE).getInt("setting",0), click)
                                .setPositiveButton(R.string.action_ok,click)
                                .show();
                        break;
                    case 2://Backup
                        new AlertDialog.Builder(SettingActivity.this)
                                .setTitle(R.string.text_setting_backup)
                                .setMessage(R.string.dialog_backup_message)
                                .setIcon(R.drawable.ic_backup)
                                .setNegativeButton(getString(R.string.action_backup), new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        showFileChooser(REQUEST_BACKUP);
                                    }
                                })
                                .setPositiveButton(getString(R.string.action_restore), new DialogInterface.OnClickListener()
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
                                .setNegativeButton(getString(R.string.action_cancel), null)
                                .setPositiveButton(getString(R.string.action_delete), new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        DeleteFile.delete(new File(getString(R.string.data_base_table_path)));
                                        Snackbar.make(coordinatorLayout, getString(R.string.snack_bar_complete_delete), Snackbar.LENGTH_SHORT)
                                                .show();
                                    }
                                })
                                .show();
                        new SQLiteHelper(SettingActivity.this, getString(R.string.data_base_file_name));
                        break;
                    case 4://Share With Friends
                        Intent intent=new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.information_share_title));
                        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.information_share_summary));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(intent, getTitle()));
                        break;
                    case 5://About Us
                        new AlertDialog.Builder(SettingActivity.this)
                                .setView(R.layout.dialog_about_us)
                                .setNegativeButton(getString(R.string.action_ok), null)
                                .setPositiveButton(getString(R.string.action_feedback), new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                        //noinspection deprecation
                                        clipboardManager.setText("mystery0dyl@icloud.com");
                                        Snackbar.make(coordinatorLayout, getString(R.string.snack_bar_copied), Snackbar.LENGTH_SHORT)
                                                .show();
                                    }
                                })
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
                        if (CopyFile.fileCopy(GetPath.getPath(this, data.getData()), getString(R.string.data_base_table_path)))
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
