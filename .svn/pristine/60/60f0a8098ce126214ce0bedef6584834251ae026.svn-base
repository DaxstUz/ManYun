package com.ch.mhy.activity.my;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.ch.mhy.R;
import com.umeng.analytics.MobclickAgent;

public class DownLoadSet extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_downset);
	}
	
	public void onclick(View view){
		switch (view.getId()) {
		case R.id.btn_return_back:
			this.finish();
			break;

		default:
			break;
		}
	}
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		}
		public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		}
}
