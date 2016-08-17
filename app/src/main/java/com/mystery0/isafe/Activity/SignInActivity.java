package com.mystery0.isafe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.mystery0.isafe.PublicMethod.ExitApplication;
import com.mystery0.isafe.R;

public class SignInActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TextInputEditText login_username;
    private TextInputEditText login_password;
    private TextView text_new;
    private TextView text_forge;
    public static final int REQUEST=111;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initialization();

        monitor();
    }

    private void initialization()
    {
        ExitApplication.getInstance().addActivity(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        login_username=(TextInputEditText)findViewById(R.id.login_username);
        login_password=(TextInputEditText)findViewById(R.id.login_password);
        text_new=(TextView)findViewById(R.id.login_new);
        text_forge=(TextView)findViewById(R.id.login_forget);

        setSupportActionBar(toolbar);
    }

    private void monitor()
    {
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
        text_new.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivityForResult(new Intent(SignInActivity.this,SignUpActivity.class),REQUEST);
            }
        });
    }

}
