package com.mystery0.isafe.BaseClass;

/**
 * Created by myste on 2016-8-16-0016.
 * Save Info Base Class
 */
public class SaveInfo
{
    private String Title;
    private String Username;
    private String Password;
    private String Type;

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        this.Title = title;
    }

    public String getUsername()
    {
        return Username;
    }

    public void setUsername(String username)
    {
        this.Username = username;
    }

    public String getPassword()
    {
        return Password;
    }

    public void setPassword(String password)
    {
        this.Password = password;
    }

    public String getType()
    {
        return Type;
    }

    public void setType(String type)
    {
        this.Type = type;
    }
}
