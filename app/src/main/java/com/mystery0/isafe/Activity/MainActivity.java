package com.mystery0.isafe.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mystery0.isafe.Adapter.ShowAdapter;
import com.mystery0.isafe.BaseClass.SaveInfo;
import com.mystery0.isafe.BaseClass.User;
import com.mystery0.isafe.PublicMethod.CircleImageView;
import com.mystery0.isafe.PublicMethod.Cryptogram;
import com.mystery0.isafe.PublicMethod.ExitApplication;
import com.mystery0.isafe.PublicMethod.GetList;
import com.mystery0.isafe.R;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener
{
    private FloatingActionButton fab;
    private TextView text_menu_username;
    private TextView text_statues_verified;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private CoordinatorLayout coordinatorLayout;
    private CircleImageView img_head;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Tencent tencent;
    private int checked = 0;
    public static final int REQUEST=11;

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
        if (getSharedPreferences("isFirst", MODE_PRIVATE).getBoolean("First", true))
        {
            final EditText editText = new EditText(this);
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
                            getSharedPreferences("isFirst", MODE_PRIVATE)
                                    .edit()
                                    .putBoolean("First", false)
                                    .apply();
                            try
                            {
                                getSharedPreferences("key", MODE_PRIVATE)
                                        .edit()
                                        .putString("key1", Cryptogram.JM(getString(R.string.username), getString(R.string.wrong_key1)))
                                        .putString("key2", Cryptogram.JM(editText.getText().toString(), getString(R.string.true_key)))
                                        .putString("key3", Cryptogram.JM(getString(R.string.username), getString(R.string.wrong_key2)))
                                        .putString("key4", Cryptogram.JM(getString(R.string.username), getString(R.string.wrong_key3)))
                                        .putString("key5", Cryptogram.JM(getString(R.string.username), getString(R.string.wrong_key4)))
                                        .putString("key6", Cryptogram.JM(java.util.UUID.randomUUID().toString(),getString(R.string.app_name)))
                                        .apply();
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            Snackbar.make(coordinatorLayout, getString(R.string.snack_bar_done_set), Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .show();

        }
    }

    private void initialization()
    {
        ExitApplication.getInstance().addActivity(this);
        tencent = Tencent.createInstance(getString(R.string.app_id), this.getApplicationContext());
        Bmob.initialize(this, getString(R.string.application_id));

        text_menu_username=(TextView)findViewById(R.id.text_menu_username);
        text_statues_verified=(TextView)findViewById(R.id.verified_statues) ;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        img_head = (CircleImageView) headerLayout.findViewById(R.id.image_menu_head);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_main);
        listView = (ListView) findViewById(R.id.listView);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);

        User user= BmobUser.getCurrentUser(User.class);
        if(user!=null)
        {
            if(user.getEmailVerified())
            {
                text_statues_verified.setText(getString(R.string.verified_done));
            }else
            {
                text_statues_verified.setText(getString(R.string.verified_null));
            }
            text_menu_username.setText(user.getUsername());
        }

        setSupportActionBar(toolbar);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        showList(MainActivity.this,checked);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        showList(MainActivity.this, GetList.ALL);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                SaveInfo saveInfo = GetList.getList(MainActivity.this, checked).get(i);
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                intent.putExtra("type", "Show");
                intent.putExtra("title", saveInfo.getTitle());
                intent.putExtra("username", saveInfo.getUsername());
                intent.putExtra("password", saveInfo.getPassword());
                intent.putExtra("item_type", saveInfo.getType());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            drawer.openDrawer(GravityCompat.START);
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
                                if (localEditText.getText().toString().length() == 0)
                                    Snackbar.make(coordinatorLayout, getString(R.string.snack_bar_error_change), Snackbar.LENGTH_SHORT)
                                            .show();
                                else
                                {
                                    try
                                    {
                                        getSharedPreferences("key", MODE_PRIVATE)
                                                .edit().putString("key2", Cryptogram.JM(localEditText.getText().toString(), getString(R.string.true_key)))
                                                .apply();
                                    } catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                    Snackbar.make(coordinatorLayout, getString(R.string.snack_bar_done_change), Snackbar.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        })
                        .show();
                break;
            case R.id.action_settings:
                Snackbar.make(coordinatorLayout, "Test", Snackbar.LENGTH_SHORT)
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.item_all:
                checked = 0;
                showList(MainActivity.this, GetList.ALL);
                break;
            case R.id.item_computer:
                checked = 1;
                showList(MainActivity.this, GetList.COMPUTER);
                break;
            case R.id.item_Internet:
                checked = 2;
                showList(MainActivity.this, GetList.INTERNET);
                break;
            case R.id.item_Email:
                checked = 3;
                showList(MainActivity.this, GetList.E_MAIL);
                break;
            case R.id.item_game:
                checked = 4;
                showList(MainActivity.this, GetList.GAME);
                break;
            case R.id.item_member:
                checked = 5;
                showList(MainActivity.this, GetList.MEMBER);
                break;
            case R.id.item_cloud:
                break;

            case R.id.nav_setting://Setting
                Snackbar.make(coordinatorLayout, "Test", Snackbar.LENGTH_SHORT)
                        .show();
                break;
            case R.id.nav_share://Share with friends
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
                                        params.putString(QQShare.SHARE_TO_QQ_TITLE, getString(R.string.information_share_title));
                                        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, getString(R.string.information_share_summary));
                                        //params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,);
                                        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, getString(R.string.information_share_url));
                                        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getString(R.string.app_name));
                                        tencent.shareToQQ(MainActivity.this, params, new IUiListener()
                                        {
                                            @Override
                                            public void onComplete(Object o)
                                            {
                                                Toast.makeText(MainActivity.this, getString(R.string.toast_complete_share), Toast.LENGTH_SHORT)
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
                                                Toast.makeText(MainActivity.this, getString(R.string.toast_cancel_share), Toast.LENGTH_SHORT)
                                                        .show();
                                            }
                                        });
                                        break;
                                    case 1:
                                        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, getString(R.string.information_share_title));//必填
                                        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, getString(R.string.information_share_summary));//选填
                                        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, getString(R.string.information_share_url));//必填
                                        tencent.shareToQzone(MainActivity.this, params, new IUiListener()
                                        {
                                            @Override
                                            public void onComplete(Object o)
                                            {
                                                Toast.makeText(MainActivity.this, getString(R.string.toast_complete_share), Toast.LENGTH_SHORT)
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
                                                Toast.makeText(MainActivity.this, getString(R.string.toast_cancel_share), Toast.LENGTH_SHORT)
                                                        .show();
                                            }
                                        });
                                        break;
                                }
                            }
                        })
                        .show();
                break;
            case R.id.nav_send://Feedback
                startActivity(new Intent(MainActivity.this, FeedBackActivity.class));
                break;
            case R.id.nav_about://About Us
                new AlertDialog.Builder(this)
                        .setView(R.layout.dialog_about_us)
                        .setNegativeButton("Ok", null)
                        .show();
                break;
            case R.id.nav_exit://Exit App
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
                intent.putExtra("item_type","");
                startActivityForResult(intent,REQUEST);
                break;
            case R.id.image_menu_head:
                User user= BmobUser.getCurrentUser(User.class);
                if(user!=null)
                {

                }else
                {
                    startActivity(new Intent(MainActivity.this, SignInActivity.class));
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==REQUEST)
        {
            if (resultCode==RESULT_OK)
            {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        showList(MainActivity.this,checked);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        }
    }

    private void showList(Context context, int type)
    {
        ShowAdapter showAdapter = new ShowAdapter(context, GetList.getList(context, type));
        listView.setAdapter(showAdapter);
    }
}
