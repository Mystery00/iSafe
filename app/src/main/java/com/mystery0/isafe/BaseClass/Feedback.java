package com.mystery0.isafe.BaseClass;

import cn.bmob.v3.BmobObject;

/**
 * Created by myste on 2016-8-15-0015.
 * 意见反馈
 */
public class Feedback extends BmobObject
{
    private String contact;
    private String feedback;


    public String getContact()
    {
        return contact;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String getFeedback()
    {
        return feedback;
    }

    public void setFeedback(String feedback)
    {
        this.feedback = feedback;
    }
}
