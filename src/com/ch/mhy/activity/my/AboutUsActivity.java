package com.ch.mhy.activity.my;

import com.ch.mhy.R;
import com.ch.mhy.application.CheckAppVersion;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;


public class AboutUsActivity extends Activity
{
	private TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_aboutus);
		
		tv=(TextView) this.findViewById(R.id.myVersion);
		tv.setText("本版本号："+this.getNewVersion());
		this.getNewVersion();
	}
	
	//获取当前版本号的方法
	private String getNewVersion()
	{
		String version=new CheckAppVersion().getVersionName(this.getPackageManager());
		return version;
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
