package com.mystery0.isafe.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mystery0.isafe.BaseClass.User;
import com.mystery0.isafe.PublicMethod.Cryptogram;
import com.mystery0.isafe.PublicMethod.ExitApplication;
import com.mystery0.isafe.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class SignInActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TextInputLayout login_username;
    private TextInputLayout login_password;
    private TextView text_new;
    private TextView text_forget;
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
        login_username=(TextInputLayout)findViewById(R.id.login_username);
        login_password=(TextInputLayout)findViewById(R.id.login_password);
        text_new=(TextView)findViewById(R.id.login_new);
        text_forget =(TextView)findViewById(R.id.login_forget);

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
                login();
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
        text_forget.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final EditText localEditText = new EditText(SignInActivity.this);
                localEditText.setHint(R.string.hint_email);
                new AlertDialog.Builder(SignInActivity.this)
                        .setMessage(getString(R.string.dialog_message_forget))
                        .setView(localEditText)
                        .setNegativeButton(getString(R.string.cancel),null)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                final ProgressDialog progressDialog=new ProgressDialog(SignInActivity.this);
                                progressDialog.setMessage(getString(R.string.dialog_feedback_title));
                                progressDialog.setCancelable(true);
                                progressDialog.show();
                                BmobUser.resetPasswordByEmail(localEditText.getText().toString(), new UpdateListener()
                                {
                                    @Override
                                    public void done(BmobException e)
                                    {
                                        progressDialog.dismiss();
                                        if(e==null)
                                        {
                                            Snackbar.make(findViewById(R.id.coordinatorLayout_sign_in), getString(R.string.snack_bar_reset), Snackbar.LENGTH_SHORT)
                                                    .show();
                                        }else
                                        {
                                            Log.e("error",e.toString());
                                            Snackbar.make(findViewById(R.id.coordinatorLayout_sign_in),e.getMessage(),Snackbar.LENGTH_SHORT)
                                                    .dismiss();
                                        }
                                    }
                                });
                            }
                        })
                        .show();
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode==REQUEST)
        {
            if (resultCode==RESULT_OK)
            {
                login_username.getEditText().setText(data.getStringExtra("username"));
                login_password.getEditText().setText(data.getStringExtra("password"));
                login();
            }
        }
    }
    @SuppressWarnings("ConstantConditions")
    private void login()
    {
        if(
                login_username.getEditText().getText().toString().length()!=0&&
                login_password.getEditText().getText().toString().length()!=0)
        {
            final ProgressDialog progressDialog = new ProgressDialog(SignInActivity.this);
            progressDialog.setMessage(getString(R.string.dialog_feedback_title));
            progressDialog.setCancelable(true);
            progressDialog.show();
            User user = new User();
            user.setUsername(login_username.getEditText().getText().toString());
            try
            {
                user.setPassword(Cryptogram.JM(login_password.getEditText().getText().toString(), getSharedPreferences("key", MODE_PRIVATE).getString("key3", "null")));
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            user.login(new SaveListener<User>()
            {
                @Override
                public void done(User user, BmobException e)
                {
                    progressDialog.dismiss();
                    if (e == null)
                    {
                        try
                        {
                            getSharedPreferences("key",MODE_PRIVATE)
                                    .edit()
                                    .putString("key",Cryptogram.JM(login_username.getEditText().getText().toString(), getString(R.string.text_username)))
                                    .putString("keyKey",Cryptogram.JM(login_password.getEditText().getText().toString(), getSharedPreferences("key", MODE_PRIVATE).getString("key1", "null")))
                                    .apply();
                        } catch (Exception e1)
                        {
                            e1.printStackTrace();
                        }
                        Toast.makeText(SignInActivity.this, getString(R.string.toast_complete_sign_in), Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                    {
                        Log.e("error",e.toString());
                        Snackbar.make(findViewById(R.id.coordinatorLayout_sign_in),e.getMessage(),Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
            });
        }else
        {
            Snackbar.make(findViewById(R.id.coordinatorLayout_sign_in),getString(R.string.snack_bar_error_add),Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
}
