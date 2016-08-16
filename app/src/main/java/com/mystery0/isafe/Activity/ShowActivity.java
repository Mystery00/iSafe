package com.mystery0.isafe.Activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mystery0.isafe.ContentProvider.SQLiteHelper;
import com.mystery0.isafe.PublicMethod.ExitApplication;
import com.mystery0.isafe.R;


public class ShowActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private TextInputLayout layout_title;
    private TextInputLayout layout_username;
    private TextInputLayout layout_password;
    private TextView show_title;
    private TextView show_username;
    private TextView show_password;
    private CoordinatorLayout coordinatorLayout;
    private Spinner spinner;
    private int item_type;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

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

        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout_show);
        spinner=(Spinner)findViewById(R.id.spinner);

        layout_title = (TextInputLayout) findViewById(R.id.layout_title);
        layout_username = (TextInputLayout) findViewById(R.id.layout_username);
        layout_password = (TextInputLayout) findViewById(R.id.layout_password);

        show_title=(TextView)findViewById(R.id.show_text_title);
        show_username=(TextView)findViewById(R.id.show_text_username);
        show_password=(TextView)findViewById(R.id.show_text_password);

        show_title.setText(this.getIntent().getStringExtra("title"));
        show_username.setText(this.getIntent().getStringExtra("username"));
        show_password.setText(this.getIntent().getStringExtra("password"));

        layout_title.getEditText().setText(this.getIntent().getStringExtra("title"));
        layout_username.getEditText().setText(this.getIntent().getStringExtra("username"));
        layout_password.getEditText().setText(this.getIntent().getStringExtra("password"));

        spinner.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.spinner_item)));
        spinner.setSelection(this.getIntent().getIntExtra("item_type",0),true);
        switch (this.getIntent().getStringExtra("type"))
        {
            case "Edit":
                layout_title.setVisibility(View.VISIBLE);
                layout_username.setVisibility(View.VISIBLE);
                layout_password.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                break;
            case "Show":
                show_title.setVisibility(View.VISIBLE);
                show_username.setVisibility(View.VISIBLE);
                show_password.setVisibility(View.VISIBLE);
                break;
            case "Add":
                layout_title.setVisibility(View.VISIBLE);
                layout_username.setVisibility(View.VISIBLE);
                layout_password.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                break;
        }
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
        layout_title.getEditText().addTextChangedListener(new TextWatcher()
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
                if (layout_title.getEditText().getText().toString().length()==0)
                {
                    layout_title.setError(getString(R.string.error_title));
                } else
                {
                    layout_title.setError(null);
                }
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
                if (layout_password.getEditText().getText().toString().length()==0)
                {
                    layout_password.setError(getString(R.string.error_password));
                } else
                {
                    layout_password.setError(null);
                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                item_type=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.show, menu);
        switch (this.getIntent().getStringExtra("type"))
        {
            case "Add":
                menu.findItem(R.id.action_edit).setVisible(false);
                menu.findItem(R.id.action_finish).setVisible(true);
                break;
            case "Show":
                menu.findItem(R.id.action_edit).setVisible(true);
                menu.findItem(R.id.action_finish).setVisible(false);
                break;
            case "Edit":
                menu.findItem(R.id.action_edit).setVisible(false);
                menu.findItem(R.id.action_finish).setVisible(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_finish:
                switch (toolbar.getTitle().toString())
                {
                    case "Add":
                        //noinspection ConstantConditions
                        if (layout_title.getEditText().getText().toString().length()==0&&
                                layout_username.getEditText().getText().toString().length()==0&&
                                layout_password.getEditText().getText().toString().length()==0)
                        {
                            Snackbar.make(coordinatorLayout, getString(R.string.snack_bar_error_add), Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                        else
                        {
                            SQLiteHelper data=new SQLiteHelper(ShowActivity.this,getString(R.string.data_base_file_name));
                            SQLiteDatabase db=data.getWritableDatabase();
                            ContentValues values=new ContentValues();
                            values.put("title",layout_title.getEditText().getText().toString());
                            values.put("username",layout_username.getEditText().getText().toString());
                            values.put("password",layout_password.getEditText().getText().toString());
                            values.put("item_type",item_type);
                            db.insert("kk",null,values);
                        }
                        break;
                    case "Edit":
                        //noinspection ConstantConditions
                        if (layout_title.getEditText().getText().toString().length()==0&&
                                layout_username.getEditText().getText().toString().length()==0&&
                                layout_password.getEditText().getText().toString().length()==0)
                            Snackbar.make(coordinatorLayout,getString(R.string.snack_bar_error_add),Snackbar.LENGTH_SHORT)
                                    .show();
                        else
                        {
                            SQLiteHelper data=new SQLiteHelper(ShowActivity.this,"app.db");
                            SQLiteDatabase db=data.getWritableDatabase();
                            ContentValues values=new ContentValues();
                            values.put("title",layout_title.getEditText().getText().toString());
                            values.put("username",layout_username.getEditText().getText().toString());
                            values.put("password",layout_password.getEditText().getText().toString());
                            values.put("item_type",item_type);
                            db.update("kk",values,null,null);
                        }
                        break;
                }
                break;
            case R.id.action_edit:
                toolbar.setTitle(R.string.menu_edit);
                toolbar.getMenu().findItem(R.id.action_edit).setVisible(false);

                show_title.setVisibility(View.GONE);
                show_username.setVisibility(View.GONE);
                show_password.setVisibility(View.GONE);

                layout_title.setVisibility(View.VISIBLE);
                layout_username.setVisibility(View.VISIBLE);
                layout_password.setVisibility(View.VISIBLE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
