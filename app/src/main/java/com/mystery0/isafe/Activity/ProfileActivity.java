package com.mystery0.isafe.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mystery0.isafe.BaseClass.User;
import com.mystery0.isafe.PublicMethod.CircleImageView;
import com.mystery0.isafe.PublicMethod.CopyFile;
import com.mystery0.isafe.PublicMethod.Cryptogram;
import com.mystery0.isafe.PublicMethod.ExitApplication;
import com.mystery0.isafe.PublicMethod.GetPath;
import com.mystery0.isafe.R;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class ProfileActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    private CircleImageView img_small;
    private ImageView img;
    private RelativeLayout layout_change_password;
    private RelativeLayout layout_change_email;
    private RelativeLayout layout_change_head;
    private RelativeLayout layout_logout;
    private static final int REQUEST_IMG = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initialization();

        monitor();
    }

    private void initialization()
    {
        ExitApplication.getInstance().addActivity(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        img_small = (CircleImageView) collapsingToolbarLayout.findViewById(R.id.img_circle);
        img = (ImageView) collapsingToolbarLayout.findViewById(R.id.img);
        TextView text_username = (TextView) findViewById(R.id.text_username);
        TextView text_email = (TextView) findViewById(R.id.text_email);
        TextView text_key = (TextView) findViewById(R.id.text_key);
        layout_change_password=(RelativeLayout)findViewById(R.id.layout_profile_change_password);
        layout_change_email=(RelativeLayout)findViewById(R.id.layout_profile_change_email);
        layout_change_head=(RelativeLayout)findViewById(R.id.layout_profile_change_head);
        layout_logout=(RelativeLayout)findViewById(R.id.layout_profile_logout);

        text_username.setText(BmobUser.getCurrentUser(User.class).getUsername());
        text_email.setText(BmobUser.getCurrentUser(User.class).getEmail());
        text_key.setText(BmobUser.getCurrentUser(User.class).getOne_key());
        if (BitmapFactory.decodeFile(getSharedPreferences("kk", MODE_PRIVATE).getString("head", null)) != null)
        {
            img.setImageBitmap(BitmapFactory.decodeFile(getSharedPreferences("kk", MODE_PRIVATE).getString("head", null)));
            img_small.setImageBitmap(BitmapFactory.decodeFile(getSharedPreferences("kk", MODE_PRIVATE).getString("head", null)));
        }

        setSupportActionBar(toolbar);
    }

    private void monitor()
    {
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        img_small.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ChooseImg();
            }
        });
        layout_change_password.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            {
                @SuppressLint("InflateParams")
                final View change_password= LayoutInflater.from(ProfileActivity.this).inflate(R.layout.dialog_change_password,null);
                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle(R.string.title_change_password)
                        .setView(change_password)
                        .setNegativeButton(getString(R.string.action_cancel),null)
                        .setPositiveButton(getString(R.string.action_ok), new DialogInterface.OnClickListener()
                        {
                            @SuppressWarnings("ConstantConditions")
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                final ProgressDialog progressDialog=new ProgressDialog(ProfileActivity.this);
                                progressDialog.setMessage(getString(R.string.dialog_feedback_title));
                                progressDialog.setCancelable(true);
                                progressDialog.show();
                                TextInputLayout Old=(TextInputLayout)change_password.findViewById(R.id.old_password);
                                TextInputLayout New=(TextInputLayout)change_password.findViewById(R.id.new_password);
                                try
                                {
                                    BmobUser.updateCurrentUserPassword(
                                            Cryptogram.JM(Old.getEditText().getText().toString(), getSharedPreferences("key", MODE_PRIVATE).getString("key3", "null")),
                                            Cryptogram.JM(New.getEditText().getText().toString(), getSharedPreferences("key", MODE_PRIVATE).getString("key3", "null")),
                                            new UpdateListener()
                                            {
                                                @Override
                                                public void done(BmobException e)
                                                {
                                                    progressDialog.dismiss();
                                                    if(e==null)
                                                    {
                                                        Snackbar.make(coordinatorLayout,getString(R.string.snack_bar_complete_update),Snackbar.LENGTH_SHORT)
                                                                .show();
                                                    }else
                                                    {
                                                        Log.e("error",e.toString());
                                                        Snackbar.make(coordinatorLayout,e.getMessage(),Snackbar.LENGTH_SHORT)
                                                                .show();
                                                    }
                                                }
                                            }
                                    );
                                } catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .show();
            }
        });
        layout_change_email.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                @SuppressLint("InflateParams") final
                EditText new_email=new EditText(ProfileActivity.this);
                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle(R.string.title_change_password)
                        .setView(new_email)
                        .setNegativeButton(getString(R.string.action_cancel),null)
                        .setPositiveButton(getString(R.string.action_ok), new DialogInterface.OnClickListener()
                        {
                            @SuppressWarnings("ConstantConditions")
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                final ProgressDialog progressDialog=new ProgressDialog(ProfileActivity.this);
                                progressDialog.setMessage(getString(R.string.dialog_feedback_title));
                                progressDialog.setCancelable(true);
                                progressDialog.show();
                                User user=new User();
                                user.setEmail(new_email.getText().toString());
                                user.update(BmobUser.getCurrentUser(User.class).getObjectId(),new UpdateListener()
                                {
                                    @Override
                                    public void done(BmobException e)
                                    {
                                        progressDialog.dismiss();
                                        if(e==null)
                                        {
                                            Snackbar.make(coordinatorLayout,getString(R.string.snack_bar_complete_update),Snackbar.LENGTH_SHORT)
                                                    .show();
                                        }else
                                        {
                                            Log.e("error",e.toString());
                                            Snackbar.make(coordinatorLayout,e.getMessage(),Snackbar.LENGTH_SHORT)
                                                    .show();
                                        }
                                    }
                                });
                            }
                        })
                        .show();
            }
        });
        layout_change_head.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ChooseImg();
            }
        });
        layout_logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                BmobUser.logOut();
                finish();
            }
        });
    }

    private void ChooseImg()
    {
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setType("image/*");
        intent.putExtra("outputX", 800);  //裁剪图片的宽
        intent.putExtra("outputY", 800);
        intent.putExtra("scale", true);  //是否保持比例
        intent.putExtra("return-data", false);  //是否返回bitmap
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());  //输出格式
        startActivityForResult(intent, REQUEST_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_IMG)
        {
            if (resultCode == RESULT_OK)
            {
                final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this);
                progressDialog.setTitle(getString(R.string.dialog_title_upload));
                progressDialog.setMessage("0% Completed...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                String path = GetPath.getPath(ProfileActivity.this, data.getData());
                try
                {
                    File file = new File(getFilesDir().getPath() + "/head/user.png");
                    CopyFile.fileCopy(path, file.getPath());
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                if (path != null)
                {
                    final BmobFile bmobFile = new BmobFile(new File(path));
                    bmobFile.uploadblock(new UploadFileListener()
                    {
                        @SuppressLint("NewApi")
                        @Override
                        public void done(BmobException e)
                        {
                            if (e == null)
                            {
                                User user = new User();
                                user.setHeadFileUrl(bmobFile.getFileUrl());
                                User localUser = BmobUser.getCurrentUser(User.class);
                                if (!Objects.equals(localUser.getHeadFileUrl(), "null"))
                                {
                                    BmobFile oldFile = new BmobFile();
                                    oldFile.setUrl(localUser.getHeadFileUrl());
                                    oldFile.delete();
                                }
                                user.update(localUser.getObjectId(), new UpdateListener()
                                {
                                    @Override
                                    public void done(BmobException e)
                                    {
                                        if (e == null)
                                        {
                                            img.setImageBitmap(BitmapFactory.decodeFile(getSharedPreferences("kk", MODE_PRIVATE).getString("head", null)));
                                            img_small.setImageBitmap(BitmapFactory.decodeFile(getSharedPreferences("kk", MODE_PRIVATE).getString("head", null)));
                                            progressDialog.dismiss();
                                            Snackbar.make(coordinatorLayout, getString(R.string.snack_bar_complete_update), Snackbar.LENGTH_SHORT)
                                                    .show();
                                        } else
                                        {
                                            Log.e("error", e.toString());
                                        }
                                    }
                                });
                            } else
                            {
                                Log.e("error", e.toString());
                                Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }

                        @Override
                        public void onProgress(Integer value)
                        {
                            progressDialog.setMessage(value + "% Completed...");
                        }
                    });
                }
            }
        }
    }
}
