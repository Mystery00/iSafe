package com.mystery0.isafe.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mystery0.isafe.BaseClass.User;
import com.mystery0.isafe.PublicMethod.Cryptogram;
import com.mystery0.isafe.R;

import java.util.Objects;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class SignUpActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private TextInputLayout sign_username;
    private TextInputLayout sign_password;
    private TextInputLayout sign_re_password;
    private TextInputLayout sign_email;
    private CoordinatorLayout coordinatorLayout;
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
        sign_username=(TextInputLayout)findViewById(R.id.sign_username);
        sign_password=(TextInputLayout)findViewById(R.id.sign_password);
        sign_re_password=(TextInputLayout)findViewById(R.id.sign_re_password);
        sign_email=(TextInputLayout)findViewById(R.id.sign_email);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout_sign_up);

        setSupportActionBar(toolbar);
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
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        sign_username.getEditText().addTextChangedListener(new TextWatcher()
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
                if (sign_username.getEditText().getText().toString().length() == 0)
                {
                    sign_username.setError(getString(R.string.error_username));
                } else
                {
                    sign_username.setError(null);
                }
            }
        });
        sign_password.getEditText().addTextChangedListener(new TextWatcher()
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
                if (sign_password.getEditText().getText().toString().length() == 0)
                {
                    sign_password.setError(getString(R.string.error_password));
                } else
                {
                    sign_password.setError(null);
                }
            }
        });
        sign_re_password.getEditText().addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @SuppressLint("NewApi")
            @Override
            public void afterTextChanged(Editable editable)
            {
                if(!Objects.equals(sign_re_password.getEditText().getText().toString(), sign_password.getEditText().getText().toString()))
                {
                    sign_re_password.setError(getString(R.string.error_repeat));
                }else
                {
                    sign_re_password.setError(null);
                }
            }
        });
        sign_email.getEditText().addTextChangedListener(new TextWatcher()
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
                if (sign_email.getEditText().getText().toString().length() == 0)
                {
                    sign_email.setError(getString(R.string.error_email));
                } else
                {
                    sign_email.setError(null);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.sign_up,menu);
        return true;
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_finish:
                if(
                        sign_username.getEditText().getText().toString().length()!=0&&
                        sign_password.getEditText().getText().toString().length()!=0&&
                        sign_email.getEditText().getText().toString().length()!=0)
                {
                    final ProgressDialog progressDialog=new ProgressDialog(SignUpActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                    User user=new User();
                    user.setUsername(sign_username.getEditText().getText().toString());
                    try
                    {
                        user.setPassword(Cryptogram.JM(sign_password.getEditText().getText().toString(),getSharedPreferences("key",MODE_PRIVATE).getString("key6","null")));
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    user.setEmail(sign_email.getEditText().getText().toString());
                    user.setOne_key(getSharedPreferences("key",MODE_PRIVATE).getString("key6","null"));
                    user.setHeadFileUrl("null");
                    user.setEmailVerified(false);
                    user.signUp(new SaveListener<User>()
                    {
                        @Override
                        public void done(User user, BmobException e)
                        {
                            progressDialog.dismiss();
                            if(e==null)
                            {
                                Toast.makeText(SignUpActivity.this,getString(R.string.toast_complete_sign_up),Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent();
                                intent.putExtra("username",sign_username.getEditText().getText().toString());
                                intent.putExtra("password",sign_password.getEditText().getText().toString());
                                setResult(RESULT_OK,intent);
                                finish();
                            }else
                            {
                                Log.e("error",e.toString());
                                Toast.makeText(SignUpActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else if (!Objects.equals(sign_password.getEditText().getText().toString(), sign_re_password.getEditText().getText().toString()))
                {
                    Snackbar.make(coordinatorLayout,getString(R.string.error_repeat),Snackbar.LENGTH_SHORT)
                            .show();
                }else
                {
                    Snackbar.make(coordinatorLayout,getString(R.string.snack_bar_error_add),Snackbar.LENGTH_SHORT)
                            .show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
