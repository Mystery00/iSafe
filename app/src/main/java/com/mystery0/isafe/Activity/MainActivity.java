package com.mystery0.isafe.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;

import com.mystery0.isafe.Adapter.ShowAdapter;
import com.mystery0.isafe.BaseClass.SaveInfo;
import com.mystery0.isafe.BaseClass.User;
import com.mystery0.isafe.PublicMethod.CircleImageView;
import com.mystery0.isafe.PublicMethod.Cryptogram;
import com.mystery0.isafe.PublicMethod.DeleteData;
import com.mystery0.isafe.PublicMethod.ExitApplication;
import com.mystery0.isafe.PublicMethod.GetInfoList;
import com.mystery0.isafe.PublicMethod.GetKey;
import com.mystery0.isafe.PublicMethod.LanguageSetting;
import com.mystery0.isafe.PublicMethod.SlideCutListView;
import com.mystery0.isafe.R;

import java.io.File;
import java.util.Locale;
import java.util.Objects;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.SaveListener;

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
    private SlideCutListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int checked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setLanguage();
        setContentView(R.layout.activity_main);

        isFirstRun();

        initialization();

        monitor();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        showList(getApplicationContext(), checked);
        UserLogin();
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
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.action_ok), new DialogInterface.OnClickListener()
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
                                        .putString("key1", Cryptogram.JM(getString(R.string.text_username), getString(R.string.wrong_key1)))
                                        .putString("key6", Cryptogram.JM(java.util.UUID.randomUUID().toString(), getString(R.string.app_name)))
                                        .putString("key3", Cryptogram.JM(getString(R.string.text_username), getString(R.string.wrong_key2)))
                                        .putString("key4", Cryptogram.JM(getString(R.string.text_username), getString(R.string.wrong_key3)))
                                        .putString("key5", Cryptogram.JM(getString(R.string.text_username), getString(R.string.wrong_key4)))
                                        .apply();
                                getSharedPreferences("key", MODE_PRIVATE)
                                        .edit()
                                        .putString("key2", Cryptogram.JM(editText.getText().toString(), Cryptogram.JX(getSharedPreferences("key", MODE_PRIVATE).getString("key6", "null"), getString(R.string.app_name))))
                                        .apply();
                                getSharedPreferences("kk", MODE_PRIVATE)
                                        .edit()
                                        .putString("head", getFilesDir().getPath() + "/head/user.png")
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
        Bmob.initialize(this, getString(R.string.application_id));

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        text_menu_username = (TextView) headerLayout.findViewById(R.id.text_menu_username);
        text_statues_verified = (TextView) headerLayout.findViewById(R.id.verified_statues);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        img_head = (CircleImageView) headerLayout.findViewById(R.id.image_menu_head);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_main);
        listView = (SlideCutListView) findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        UserLogin();

        setSupportActionBar(toolbar);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        UserLogin();
                        showList(MainActivity.this, checked);
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
        showList(MainActivity.this, GetInfoList.ALL);
        swipeRefreshLayout.setRefreshing(true);
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
                SaveInfo saveInfo = GetInfoList.getList(MainActivity.this, checked).get(i);
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                intent.putExtra("type", "Show");
                intent.putExtra("id", saveInfo.getId());
                intent.putExtra("title", saveInfo.getTitle());
                intent.putExtra("username", saveInfo.getUsername());
                intent.putExtra("password", saveInfo.getPassword());
                intent.putExtra("item_type", saveInfo.getType());
                startActivity(intent);
            }
        });
        listView.setRemoveListener(new SlideCutListView.RemoveListener()
        {
            @Override
            public void removeItem(SlideCutListView.RemoveDirection direction, int position)
            {
                SaveInfo saveInfo=GetInfoList.getList(MainActivity.this,checked).get(position);
                DeleteData.deleteData(getApplicationContext(),saveInfo.getId());
                showList(MainActivity.this, checked);
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
                        .setNegativeButton(getString(R.string.action_cancel), null)
                        .setMessage(getString(R.string.dialog_change_key_message))
                        .setPositiveButton(getString(R.string.action_ok), new DialogInterface.OnClickListener()
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
                                                .edit()
                                                .putString("key2", GetKey.getKey(getApplicationContext()))
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
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            case R.id.action_exit:
                ExitApplication.getInstance().exit();
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
                showList(MainActivity.this, GetInfoList.ALL);
                break;
            case R.id.item_computer:
                checked = 1;
                showList(MainActivity.this, GetInfoList.COMPUTER);
                break;
            case R.id.item_Internet:
                checked = 2;
                showList(MainActivity.this, GetInfoList.INTERNET);
                break;
            case R.id.item_Email:
                checked = 3;
                showList(MainActivity.this, GetInfoList.E_MAIL);
                break;
            case R.id.item_game:
                checked = 4;
                showList(MainActivity.this, GetInfoList.GAME);
                break;
            case R.id.item_member:
                checked = 5;
                showList(MainActivity.this, GetInfoList.MEMBER);
                break;

            case R.id.nav_setting://Setting
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
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
                intent.putExtra("item_type", "0");
                startActivity(intent);
                break;
            case R.id.image_menu_head:
                User user = BmobUser.getCurrentUser(User.class);
                if (user != null)
                {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                } else
                {
                    startActivity(new Intent(MainActivity.this, SignInActivity.class));
                }
                break;
        }
    }

    private void showList(Context context, int type)
    {
        ShowAdapter showAdapter = new ShowAdapter(context, GetInfoList.getList(context, type));
        listView.setAdapter(showAdapter);
    }

    private void UserLogin()
    {
        swipeRefreshLayout.setRefreshing(true);
        showList(getApplicationContext(), checked);
        final User localUser = BmobUser.getCurrentUser(User.class);
        if (localUser != null)
        {
            if (localUser.getEmailVerified())
            {
                text_statues_verified.setText(getString(R.string.verified_done));
            } else
            {
                text_statues_verified.setText(getString(R.string.verified_null));
            }
            text_menu_username.setText(localUser.getUsername());
            getSharedPreferences("key", MODE_PRIVATE)
                    .edit()
                    .putString("key6", localUser.getOne_key())
                    .apply();

            User user = new User();
            try
            {
                user.setUsername(Cryptogram.JX(getSharedPreferences("key", MODE_PRIVATE).getString("key", "null"), getString(R.string.text_username)));
                user.setPassword(Cryptogram.JM(Cryptogram.JX(getSharedPreferences("key", MODE_PRIVATE).getString("keyKey", "null"), getSharedPreferences("key", MODE_PRIVATE).getString("key1", "null")), getSharedPreferences("key", MODE_PRIVATE).getString("key3", "null")));
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            user.login(new SaveListener<User>()
            {
                @SuppressLint("NewApi")
                @Override
                public void done(User user, BmobException e)
                {
                    swipeRefreshLayout.setRefreshing(false);
                    if (e == null)
                    {
                        if (!Objects.equals(BmobUser.getCurrentUser(User.class).getHeadFileUrl(), "null"))
                        {
                            BmobFile bmobFile = new BmobFile("user.png", "", BmobUser.getCurrentUser(User.class).getHeadFileUrl());
                            bmobFile.download(new File(getSharedPreferences("kk", Context.MODE_PRIVATE).getString("head", "null")), new DownloadFileListener()
                            {
                                @Override
                                public void done(String s, BmobException e)
                                {
                                    if (e == null)
                                    {
                                        img_head.setImageBitmap(BitmapFactory.decodeFile(getSharedPreferences("kk", MODE_PRIVATE).getString("head", null)));
                                    } else
                                    {
                                        Log.e("error", e.getMessage());
                                    }
                                }

                                @Override
                                public void onProgress(Integer integer, long l)
                                {
                                }
                            });
                            if (user.getEmailVerified())
                            {
                                text_statues_verified.setText(getString(R.string.verified_done));
                            } else
                            {
                                text_statues_verified.setText(getString(R.string.verified_null));
                            }
                        }
                    } else
                    {
                        Log.e("error", e.toString());
                        Snackbar.make(coordinatorLayout, e.getMessage(), Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
            });
        } else
        {
            text_statues_verified.setText(getString(R.string.verified_null));
            img_head.setImageResource(R.drawable.ic_guest);
        }
    }

    private void setLanguage()
    {
        Locale locale;
        switch (getSharedPreferences("language", MODE_PRIVATE).getInt("setting", 0))
        {
            case 1:
                locale = Locale.SIMPLIFIED_CHINESE;
                break;
            case 2:
                locale = Locale.TRADITIONAL_CHINESE;
                break;
            case 3:
                locale = Locale.ENGLISH;
                break;
            default:
                locale = Locale.getDefault();
                break;
        }
        LanguageSetting.set(locale, getApplicationContext());
    }
}
