package com.ch.mhy.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class FeedBackListView extends ListView
{
	public FeedBackListView(Context context) 
	{
		super(context);
	}
	
    public FeedBackListView(Context context, AttributeSet attrs) 
    {
        super(context, attrs);
    }
     
    //重写该方法，为了在SrcollView中可以嵌套ListView
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
