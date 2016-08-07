package com.mystery0.isafe.Activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mystery0.isafe.PublicMethod.ExitApplication;
import com.mystery0.isafe.R;


public class AddActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private TextInputLayout layout_title;
    private TextInputLayout layout_username;
    private TextInputLayout layout_password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initialization();

        monitor();
    }

    @SuppressWarnings("ConstantConditions")
    private void initialization()
    {
        ExitApplication.getInstance().addActivity(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(this.getIntent().getStringExtra("type"));
        setSupportActionBar(toolbar);
        layout_title = (TextInputLayout) findViewById(R.id.layout_title);
        layout_username = (TextInputLayout) findViewById(R.id.layout_username);
        layout_password = (TextInputLayout) findViewById(R.id.layout_password);

        layout_title.getEditText().setText(this.getIntent().getStringExtra("title"));
        layout_username.getEditText().setText(this.getIntent().getStringExtra("username"));
        layout_password.getEditText().setText(this.getIntent().getStringExtra("password"));
    }

    @SuppressWarnings("ConstantConditions")
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
        layout_username.getEditText().addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (layout_username.getEditText().getText().toString().length()==0)
                {
                    layout_username.setError(getString(R.string.error_username));
                } else
                {
                    layout_username.setError(null);
                }
            }
        });
        layout_password.getEditText().addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                Log.i("info",layout_password.getEditText().getText().toString());
                if (layout_password.getEditText().getText().toString().length()==0)
                {
                    layout_password.setError(getString(R.string.error_password));
                } else
                {
                    layout_password.setError(null);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_finish:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
