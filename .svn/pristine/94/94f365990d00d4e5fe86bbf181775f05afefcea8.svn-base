package com.ch.mhy.activity.my;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.ch.mhy.R;
import com.umeng.analytics.MobclickAgent;

public class ToWorkActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_towrok);
	}
	
	public void onclick(View view)//窗口上面左边的后退按钮......
	{
		switch (view.getId())
		{
		case R.id.btn_show_back_aboutus://后退按钮，结束本Activity，跳转到上一个Activity
			this.finish();
			break;
			
		default:
			break;
		}
	}

	public void onResume()
	{
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause()
	{
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
