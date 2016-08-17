package com.mystery0.isafe.BaseClass;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser
{
    private String username;
    private String password;
    private String one_key;
    private String headFileUrl;
    private String email;
    private boolean emailVerified;

    public String getOne_key()
    {
        return one_key;
    }

    public void setOne_key(String one_key)
    {
        this.one_key = one_key;
    }

    public String getHeadFileUrl()
    {
        return headFileUrl;
    }

    public void setHeadFileUrl(String headFileUrl)
    {
        this.headFileUrl = headFileUrl;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Boolean getEmailVerified()
    {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified)
    {
        this.emailVerified = emailVerified;
    }
}
