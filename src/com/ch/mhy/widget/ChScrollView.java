package com.ch.mhy.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ChScrollView extends ScrollView {
	/**
	 * 滑动到顶部
	 */
	public final static int IS_TOP = 0;
	/**
	 * 滑动到底部
	 */
	public final static int IS_BOTTOM = 1;
	/**
	 * 是否在加载数据
	 */
	private boolean isQry = false;
	
	public void setIsQry(boolean isQry) {
		this.isQry = isQry;
	}

	private Handler handler;
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ChScrollView(Context context) {
		super(context);
	}

	public ChScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ChScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 
	 */
	@Override
	protected void onScrollChanged(int x, int y, int orix, int oriy){  
		Message msg = new Message();
		if(y + getHeight() >=  computeVerticalScrollRange() && !isQry ){//滑动到底部
			isQry = true;
			msg.what = 1;
			handler.sendMessage(msg);
		}  
	}  

}
