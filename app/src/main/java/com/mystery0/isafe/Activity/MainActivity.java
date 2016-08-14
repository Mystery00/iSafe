package com.mystery0.isafe.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mystery0.isafe.PublicMethod.CircleImageView;
import com.mystery0.isafe.PublicMethod.Cryptogram;
import com.mystery0.isafe.PublicMethod.ExitApplication;
import com.mystery0.isafe.R;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener
{
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private CoordinatorLayout coordinatorLayout;
    private CircleImageView img_head;
    private ListView listView;
    private Tencent mTencent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isFirstRun();

        initialization();

        monitor();
    }

    private void isFirstRun()
    {
        if(getSharedPreferences("isFirst",MODE_PRIVATE).getBoolean("First",true))
        {
            final EditText editText=new EditText(this);
            editText.setHint(R.string.dialog_set_key_hint);
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.dialog_set_key_title))
                    .setMessage(getString(R.string.dialog_set_key_message))
                    .setView(editText)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            getSharedPreferences("isFirst",MODE_PRIVATE).edit().putBoolean("First",false).apply();
                            Snackbar.make(coordinatorLayout,getString(R.string.snack_bar_done_set),Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .show();

        }
    }

    private void monitor()
    {
        fab.setOnClickListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //noinspection deprecation
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        img_head.setOnClickListener(this);
    }

    private void initialization()
    {
        ExitApplication.getInstance().addActivity(this);
        mTencent=Tencent.createInstance(getString(R.string.app_id),this.getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        img_head = (CircleImageView) headerLayout.findViewById(R.id.image_menu_head);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_main);
        listView = (ListView) findViewById(R.id.listView);

        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_changeKey:
                final EditText localEditText = new EditText(this);
                localEditText.setHint(R.string.dialog_change_key_hint);
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.dialog_change_key_title))
                        .setIcon(R.drawable.ic_warning)
                        .setView(localEditText)
                        .setNegativeButton("Cancel", null)
                        .setMessage(getString(R.string.dialog_change_key_message))
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if(localEditText.getText().toString().length()==0)
                                    Snackbar.make(coordinatorLayout,getString(R.string.snack_bar_error_change),Snackbar.LENGTH_SHORT)
                                            .show();
                                else
                                {
                                    SharedPreferences.Editor editor=getSharedPreferences("key",MODE_PRIVATE).edit();
                                    try
                                    {
                                        editor.putString("key1",Cryptogram.JM(getString(R.string.username),getString(R.string.wrong_key1)));
                                        editor.putString("key2",Cryptogram.JM(localEditText.getText().toString(),getString(R.string.true_key)));
                                        editor.putString("key3",Cryptogram.JM(getString(R.string.username),getString(R.string.wrong_key2)));
                                        editor.putString("key4",Cryptogram.JM(getString(R.string.username),getString(R.string.wrong_key3)));
                                        editor.putString("key5",Cryptogram.JM(getString(R.string.username),getString(R.string.wrong_key4)));
                                        editor.apply();
                                        Snackbar.make(coordinatorLayout,getString(R.string.snack_bar_done_change),Snackbar.LENGTH_SHORT)
                                                .show();
                                    } catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        })
                        .show();
                break;
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_all:
                break;
            case R.id.nav_cloud:
                break;
            case R.id.nav_send:
                break;
            case R.id.nav_share:
                new AlertDialog.Builder(this)
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
                                        params.putString(QQShare.SHARE_TO_QQ_TITLE, "404网警已介入,请注意发言!");
                                        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "404网警已介入,请注意发言!");
                                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRYHBU0FkgkfyVGKBIBoeuIAIFlCcSH4E-LVAy61XSWVa9kqsCC");
                                        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, getString(R.string.information_share_url));
                                        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "404网警");
                                        mTencent.shareToQQ(MainActivity.this, params, new IUiListener()
                                        {
                                            @Override
                                            public void onComplete(Object o)
                                            {
                                                Toast.makeText(MainActivity.this,getString(R.string.toast_complete_share),Toast.LENGTH_SHORT)
                                                        .show();
                                            }

                                            @Override
                                            public void onError(UiError uiError)
                                            {
                                                Log.e("error",uiError.toString());
                                            }

                                            @Override
                                            public void onCancel()
                                            {
                                                Toast.makeText(MainActivity.this,getString(R.string.toast_cancel_share),Toast.LENGTH_SHORT)
                                                        .show();
                                            }
                                        });
                                        break;
                                    case 1:
                                        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, getString(R.string.information_share_title));//必填
                                        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, getString(R.string.information_share_summary));//选填
                                        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, getString(R.string.information_share_url));//必填
                                        mTencent.shareToQzone(MainActivity.this, params, new IUiListener()
                                        {
                                            @Override
                                            public void onComplete(Object o)
                                            {
                                                Toast.makeText(MainActivity.this,getString(R.string.toast_complete_share),Toast.LENGTH_SHORT)
                                                        .show();
                                            }

                                            @Override
                                            public void onError(UiError uiError)
                                            {
                                                Log.e("error",uiError.toString());
                                            }

                                            @Override
                                            public void onCancel()
                                            {
                                                Toast.makeText(MainActivity.this,getString(R.string.toast_cancel_share),Toast.LENGTH_SHORT)
                                                        .show();
                                            }
                                        });
                                        break;
                                }
                            }
                        })
                        .show();
                break;
            case R.id.nav_setting:
                Snackbar.make(coordinatorLayout, "Test", Snackbar.LENGTH_SHORT)
                        .show();
                break;
            case R.id.nav_exit:
                ExitApplication.getInstance().exit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fab:
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                intent.putExtra("type", "Add");
                intent.putExtra("title", "");
                intent.putExtra("username", "");
                intent.putExtra("password", "");
                startActivity(intent);
                break;
            case R.id.image_menu_head:
                break;
        }
    }
}
