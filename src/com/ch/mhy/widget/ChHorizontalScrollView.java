package com.ch.mhy.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class ChHorizontalScrollView extends HorizontalScrollView {

	public ChHorizontalScrollView(Context context) {
		super(context);
	}
	
	public ChHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public ChHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

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
	
	@Override
	protected void onScrollChanged(int x, int y, int orix, int oriy){  
		Message msg = new Message();
		if(x + this.getWidth() >=  this.computeHorizontalScrollExtent() && !isQry ){//滑动到底部
			isQry = true;
			msg.what = 1;
			handler.sendMessage(msg);
		}  
	} 
}
