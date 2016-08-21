package com.mystery0.isafe.BaseClass;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser
{
    private String one_key;
    private String headFileUrl;
    private String databaseUrl;

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

    public String getDatabaseUrl()
    {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl)
    {
        this.databaseUrl = databaseUrl;
    }
}
