package com.mystery0.isafe.Activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mystery0.isafe.ContentProvider.SQLiteHelper;
import com.mystery0.isafe.PublicMethod.Cryptogram;
import com.mystery0.isafe.PublicMethod.ExitApplication;
import com.mystery0.isafe.R;

import java.util.Objects;


public class ShowActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private TextInputLayout layout_title;
    private TextInputLayout layout_username;
    private TextInputLayout layout_password;
    private RelativeLayout show_title;
    private RelativeLayout show_username;
    private RelativeLayout show_password;
    private CoordinatorLayout coordinatorLayout;
    private Spinner spinner;
    private String item_type;
    private String type;
    private Intent intent = new Intent();

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
        type = this.getIntent().getStringExtra("type");
        toolbar.setTitle(type);
        setSupportActionBar(toolbar);
        item_type = getIntent().getStringExtra("item_type");

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_show);
        spinner = (Spinner) findViewById(R.id.spinner);

        layout_title = (TextInputLayout) findViewById(R.id.layout_title);
        layout_username = (TextInputLayout) findViewById(R.id.layout_username);
        layout_password = (TextInputLayout) findViewById(R.id.layout_password);

        show_title = (RelativeLayout) findViewById(R.id.show_title);
        show_username = (RelativeLayout) findViewById(R.id.show_username);
        show_password = (RelativeLayout) findViewById(R.id.show_password);

        TextView show_text_title = (TextView) findViewById(R.id.show_text_title);
        TextView show_text_username = (TextView) findViewById(R.id.show_text_username);
        TextView show_text_password = (TextView) findViewById(R.id.show_text_password);

        show_text_title.setText(this.getIntent().getStringExtra("title"));
        show_text_username.setText(this.getIntent().getStringExtra("username"));
        show_text_password.setText(this.getIntent().getStringExtra("password"));

        layout_title.getEditText().setText(this.getIntent().getStringExtra("title"));
        layout_username.getEditText().setText(this.getIntent().getStringExtra("username"));
        layout_password.getEditText().setText(this.getIntent().getStringExtra("password"));

        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinner_item)));
        switch (this.getIntent().getStringExtra("type"))
        {
            case "Edit":
                layout_title.setVisibility(View.VISIBLE);
                layout_username.setVisibility(View.VISIBLE);
                layout_password.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                spinner.setSelection(Integer.parseInt(this.getIntent().getStringExtra("item_type")), true);
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
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v)
            {
                if (Objects.equals(type, "Edit"))
                {
                    toolbar.setTitle("Show");
                    toolbar.getMenu().findItem(R.id.action_edit).setVisible(true);
                    toolbar.getMenu().findItem(R.id.action_finish).setVisible(false);

                    show_title.setVisibility(View.VISIBLE);
                    show_username.setVisibility(View.VISIBLE);
                    show_password.setVisibility(View.VISIBLE);

                    layout_title.setVisibility(View.GONE);
                    layout_username.setVisibility(View.GONE);
                    layout_password.setVisibility(View.GONE);
                    spinner.setVisibility(View.GONE);
                    type="Show";
                } else
                {
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }
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
                if (layout_title.getEditText().getText().toString().length() == 0)
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
                if (layout_username.getEditText().getText().toString().length() == 0)
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
                if (layout_password.getEditText().getText().toString().length() == 0)
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
                item_type = Integer.toString(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
            }
        });
        show_username.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                //noinspection deprecation
                clipboardManager.setText(getIntent().getStringExtra("username"));
                Snackbar.make(coordinatorLayout, getString(R.string.snack_bar_copied), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
        show_password.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                //noinspection deprecation
                clipboardManager.setText(getIntent().getStringExtra("password"));
                Snackbar.make(coordinatorLayout, getString(R.string.snack_bar_copied), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed()
    {
        if (Objects.equals(type, "Edit"))
        {
            toolbar.setTitle("Show");
            toolbar.getMenu().findItem(R.id.action_edit).setVisible(true);
            toolbar.getMenu().findItem(R.id.action_finish).setVisible(false);

            show_title.setVisibility(View.VISIBLE);
            show_username.setVisibility(View.VISIBLE);
            show_password.setVisibility(View.VISIBLE);

            layout_title.setVisibility(View.GONE);
            layout_username.setVisibility(View.GONE);
            layout_password.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            type="Show";
        } else
        {
            setResult(RESULT_CANCELED, intent);
            finish();
        }
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

    @SuppressLint("NewApi")
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
                        if (layout_title.getEditText().getText().toString().length() == 0 ||
                                layout_username.getEditText().getText().toString().length() == 0 ||
                                layout_password.getEditText().getText().toString().length() == 0 ||
                                Objects.equals(item_type, "0"))
                        {
                            Snackbar.make(coordinatorLayout, getString(R.string.snack_bar_error_add), Snackbar.LENGTH_SHORT)
                                    .show();
                        } else
                        {
                            SQLiteHelper data = new SQLiteHelper(ShowActivity.this, getString(R.string.data_base_file_name));
                            SQLiteDatabase db = data.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("title", layout_title.getEditText().getText().toString());
                            try
                            {
                                values.put("username", Cryptogram.JM(layout_username.getEditText().getText().toString(), Cryptogram.JX(getSharedPreferences("key", Context.MODE_PRIVATE).getString("key2", getSharedPreferences("key",MODE_PRIVATE).getString("key6",getString(R.string.app_name))), getString(R.string.true_key))));
                                values.put("password", Cryptogram.JM(layout_password.getEditText().getText().toString(), Cryptogram.JX(getSharedPreferences("key", Context.MODE_PRIVATE).getString("key2", getSharedPreferences("key",MODE_PRIVATE).getString("key6",getString(R.string.app_name))), getString(R.string.true_key))));
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            values.put("item_type", item_type);
                            db.insert("kk", null, values);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        break;
                    case "Edit":
                        //noinspection ConstantConditions
                        if (layout_title.getEditText().getText().toString().length() == 0 ||
                                layout_username.getEditText().getText().toString().length() == 0 ||
                                layout_password.getEditText().getText().toString().length() == 0)
                            Snackbar.make(coordinatorLayout, getString(R.string.snack_bar_error_add), Snackbar.LENGTH_SHORT)
                                    .show();
                        else
                        {
                            SQLiteHelper data = new SQLiteHelper(ShowActivity.this, getString(R.string.data_base_file_name));
                            SQLiteDatabase db = data.getWritableDatabase();
                            ContentValues values=new ContentValues();
                            try
                            {
                                values.put("title",layout_title.getEditText().getText().toString());
                                values.put("username",Cryptogram.JM(layout_username.getEditText().getText().toString(), Cryptogram.JX(getSharedPreferences("key", MODE_PRIVATE).getString("key2", getSharedPreferences("key",MODE_PRIVATE).getString("key6",getString(R.string.app_name))), getString(R.string.true_key))));
                                values.put("password",Cryptogram.JM(layout_password.getEditText().getText().toString(), Cryptogram.JX(getSharedPreferences("key", MODE_PRIVATE).getString("key2", getSharedPreferences("key",MODE_PRIVATE).getString("key6",getString(R.string.app_name))), getString(R.string.true_key))));
                                values.put("item_type",item_type);
                                db.update(
                                        getString(R.string.data_base_table_name),
                                        values,
                                        "title=? and username=? and password=? and item_type=?",
                                        new String[]{
                                                getIntent().getStringExtra("title"),
                                                Cryptogram.JM(getIntent().getStringExtra("username"), Cryptogram.JX(getSharedPreferences("key", MODE_PRIVATE).getString("key2", getSharedPreferences("key",MODE_PRIVATE).getString("key6",getString(R.string.app_name))), getString(R.string.true_key))),
                                                Cryptogram.JM(getIntent().getStringExtra("password"), Cryptogram.JX(getSharedPreferences("key", MODE_PRIVATE).getString("key2", getSharedPreferences("key",MODE_PRIVATE).getString("key6",getString(R.string.app_name))), getString(R.string.true_key))),
                                                getIntent().getStringExtra("item_type")
                                        });
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        break;
                }
                break;
            case R.id.action_edit:
                toolbar.setTitle(R.string.menu_edit);
                type = "Edit";
                toolbar.getMenu().findItem(R.id.action_edit).setVisible(false);
                toolbar.getMenu().findItem(R.id.action_finish).setVisible(true);

                show_title.setVisibility(View.GONE);
                show_username.setVisibility(View.GONE);
                show_password.setVisibility(View.GONE);

                layout_title.setVisibility(View.VISIBLE);
                layout_username.setVisibility(View.VISIBLE);
                layout_password.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
