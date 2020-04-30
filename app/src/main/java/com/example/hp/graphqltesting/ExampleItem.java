package com.example.hp.graphqltesting;

import android.renderscript.Double2;

public class ExampleItem {
    private int mImageResource;
    private String mText1;

    public ExampleItem(int imageResource,String text1)
    {
        mImageResource=imageResource;
        mText1=text1;
    }

    public int getmImageResource()
    {
        return mImageResource;
    }
    public String getmText1()
    {
        return mText1;
    }
}
