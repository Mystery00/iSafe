package com.mystery0.isafe.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mystery0.isafe.BaseClass.Feedback;
import com.mystery0.isafe.PublicMethod.ExitApplication;
import com.mystery0.isafe.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class FeedBackActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TextInputLayout layout_message;
    private TextInputLayout layout_contact;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        initialization();

        monitor();
    }

    private void initialization()
    {
        ExitApplication.getInstance().addActivity(this);
        Bmob.initialize(this, getString(R.string.application_id));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        layout_message=(TextInputLayout)findViewById(R.id.layout_feedback_message);
        layout_contact=(TextInputLayout)findViewById(R.id.layout_feedback_contact);

        progressDialog=new ProgressDialog(this);

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
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(final View view)
            {
                if(layout_contact.getEditText().getText().toString().length()!=0&&
                        layout_message.getEditText().getText().toString().length()!=0)
                {
                    Feedback feedback = new Feedback();
                    feedback.setFeedback(layout_message.getEditText().getText().toString());
                    feedback.setContact(layout_contact.getEditText().getText().toString());
                    feedback.save(new SaveListener<String>()
                    {
                        @Override
                        public void done(String s, BmobException e)
                        {
                            progressDialog.dismiss();
                            if (e == null)
                            {
                                Snackbar.make(view, getString(R.string.snack_bar_done_feedback), Snackbar.LENGTH_SHORT)
                                        .show();
                            } else
                            {
                                Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        }

                        @Override
                        public void onStart()
                        {
                            progressDialog.setTitle(getString(R.string.dialog_feedback_title));
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                            super.onStart();
                        }
                    });
                } else
                {
                    if(layout_contact.getEditText().getText().toString().length()==0&&
                            layout_message.getEditText().getText().toString().length()!=0)
                    {
                        Snackbar.make(view, getString(R.string.error_contact), Snackbar.LENGTH_SHORT)
                                .show();
                    }
                    else
                    {
                        Snackbar.make(view, getString(R.string.snack_bar_error_feedback), Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });
    }
}
