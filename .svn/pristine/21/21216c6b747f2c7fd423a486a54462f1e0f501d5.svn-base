package com.ch.mhy.activity.my;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ch.mhy.R;
import com.ch.mhy.fragment.CollectFragment;
import com.ch.mhy.fragment.DownloadFragment;
import com.ch.mhy.fragment.ReadedFragment;
import com.ch.mhy.interf.UpdateEditFlag;
import com.ch.mhy.util.Utils;
import com.umeng.analytics.MobclickAgent;

/**
 * 我的收藏界面
 *
 * @author DaxstUz 2416738717 2015年5月6日
 */
public class CollectActivity extends FragmentActivity implements UpdateEditFlag{

	private CollectFragment cFragment;
	private DownloadFragment dFragment;
	private ReadedFragment rFragment;

	private LinearLayout ll_aboutmy_title_bg;

	private Button btn_my_collect;
	private Button btn_my_readed;
	private Button btn_my_download;

	private String operate;
	
	private boolean flag=true;//true 表示编辑状态，false表示取消状态
	
	private TextView tv_my_edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__collect);
		ll_aboutmy_title_bg = (LinearLayout) findViewById(R.id.ll_aboutmy_title_bg);
		btn_my_collect = (Button) findViewById(R.id.btn_my_collect);
		btn_my_readed = (Button) findViewById(R.id.btn_my_readed);
		btn_my_download = (Button) findViewById(R.id.btn_my_download);
		tv_my_edit = (TextView) findViewById(R.id.tv_my_edit);

		initTextColor();
		
		operate = (String) getIntent().getSerializableExtra("operate");
		if ("1".equals(operate)) {
			ll_aboutmy_title_bg
					.setBackgroundResource(R.drawable.ch_aboutmy_reading);
		}
		if ("2".equals(operate)) {
			ll_aboutmy_title_bg
					.setBackgroundResource(R.drawable.ch_aboutmy_down);
		}
		if ("3".equals(operate)) {
			ll_aboutmy_title_bg
					.setBackgroundResource(R.drawable.ch_aboutmy_collect);
		}

		setDefaultFragment();
		Utils.updateEditFlag = this;
	}

	private void initTextColor() {
		btn_my_collect.setTextColor(Color.argb(255, 168, 168, 168));
		btn_my_readed.setTextColor(Color.argb(255, 168, 168, 168));
		btn_my_download.setTextColor(Color.argb(255, 168, 168, 168));

	}

	/**
	 * 设置默认显示的碎片
	 */
	private void setDefaultFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		cFragment = new CollectFragment();
		rFragment = new ReadedFragment();
		dFragment = new DownloadFragment();

		initTextColor();

		if ("1".equals(operate)) {
			btn_my_readed.setTextColor(Color.WHITE);
			transaction.replace(R.id.collent_content, rFragment);
		}
		if ("2".equals(operate)) {
			btn_my_download.setTextColor(Color.WHITE);
			transaction.replace(R.id.collent_content, dFragment);
		}
		if ("3".equals(operate)) {
			btn_my_collect.setTextColor(Color.WHITE);
			transaction.replace(R.id.collent_content, cFragment);
		}

		transaction.commit();
	}

	public void onclick(View view) {

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();

		initTextColor();

		switch (view.getId()) {
		case R.id.btn_collect_back:
			this.finish();
			break;

		case R.id.btn_my_collect:
			flag=true;
			if(flag){
				tv_my_edit.setText("编辑");
			}else{
				tv_my_edit.setText("取消");
			}
			btn_my_collect.setTextColor(Color.WHITE);
			ll_aboutmy_title_bg
					.setBackgroundResource(R.drawable.ch_aboutmy_collect);
			if (cFragment == null) {
				cFragment = new CollectFragment();
			}
			if (!cFragment.isAdded()) {
				transaction.replace(R.id.collent_content, cFragment);
			}
			transaction.commit();
			break;

		case R.id.btn_my_readed://
			btn_my_readed.setTextColor(Color.WHITE);
			ll_aboutmy_title_bg
					.setBackgroundResource(R.drawable.ch_aboutmy_reading);

			flag=true;
			if(flag){
				tv_my_edit.setText("编辑");
			}else{
				tv_my_edit.setText("取消");
			}
			
			if (rFragment == null) {
				rFragment = new ReadedFragment();
			}

			if (!rFragment.isAdded()) {
				transaction.replace(R.id.collent_content, rFragment);
			}
			transaction.commit();
			break;

		case R.id.btn_my_download:
			flag=true;
			if(flag){
				tv_my_edit.setText("编辑");
			}else{
				tv_my_edit.setText("取消");
			}
			
			btn_my_download.setTextColor(Color.WHITE);
			ll_aboutmy_title_bg
					.setBackgroundResource(R.drawable.ch_aboutmy_down);

			if (dFragment == null) {
				dFragment = new DownloadFragment();
			}
			if (!dFragment.isAdded()) {
				transaction.replace(R.id.collent_content, dFragment);
			}
			transaction.commit();
			break;

		case R.id.ll_my_edit:
			flag=!flag;
			
			if(flag){
				tv_my_edit.setText("编辑");
			}else{
				tv_my_edit.setText("取消");
			}
			
			if (rFragment != null) {
				rFragment.updateStatus(flag);
			}
			if (dFragment != null) {
				dFragment.updateStatus(flag);
			}
			if (cFragment != null) {
				cFragment.updateStatus(flag);
			}
			
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

	@Override
	public void update(String flags) {
		if("down".equals(flags)){
			tv_my_edit.setText("编辑");
			flag = true;
		}else if("read".equals(flags)){
			tv_my_edit.setText("编辑");
			flag = true;
		}else if("collect".equals(flags)){
			tv_my_edit.setText("编辑");
			flag = true;
		}
		
	}

}
