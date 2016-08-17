package com.mystery0.isafe.Activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.mystery0.isafe.R;

public class SignUpActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private TextInputEditText sign_username;
    private TextInputEditText sign_password;
    private TextInputEditText sign_re_password;
    private TextInputEditText sign_email;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initialization();

        monitor();
    }

    private void initialization()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sign_username=(TextInputEditText)findViewById(R.id.sign_username);
        sign_password=(TextInputEditText)findViewById(R.id.sign_password);
        sign_re_password=(TextInputEditText)findViewById(R.id.sign_re_password);
        sign_email=(TextInputEditText)findViewById(R.id.sign_email);

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
        sign_username.addTextChangedListener(new TextWatcher()
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
                if (sign_username.getText().toString().length() == 0)
                {
                    sign_username.setError(getString(R.string.error_title));
                } else
                {
                    sign_username.setError(null);
                }
            }
        });
    }
}
