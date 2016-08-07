package com.mystery0.isafe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mystery0.isafe.PublicMethod.CircleImageView;
import com.mystery0.isafe.PublicMethod.ExitApplication;
import com.mystery0.isafe.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener
{
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private CoordinatorLayout coordinatorLayout;
    private CircleImageView img_head;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        monitor();
    }

    private void monitor()
    {
        fab.setOnClickListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        img_head.setOnClickListener(this);
    }

    private void initialization()
    {
        ExitApplication.getInstance().addActivity(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout=navigationView.getHeaderView(0);
        img_head=(CircleImageView)headerLayout.findViewById(R.id.image_menu_head);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout);

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
                break;
            case R.id.nav_setting:
                Snackbar.make(coordinatorLayout, "Test", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
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
                startActivity(new Intent(MainActivity.this,AddActivity.class));
                break;
            case R.id.image_menu_head:
                Snackbar.make(coordinatorLayout,"你点击的是头像",Snackbar.LENGTH_SHORT)
                        .setAction("Action",null).show();
                break;
        }
    }
}
