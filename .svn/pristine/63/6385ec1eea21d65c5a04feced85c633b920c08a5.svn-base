package com.ch.mhy.activity.my;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.ch.mhy.R;
import com.ch.mhy.adapter.FragmentPagerAdapter;
import com.ch.mhy.adapter.MsgAdapter;
import com.ch.mhy.fragment.IJoinFragment;
import com.ch.mhy.fragment.ISayFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;

/**
 * 我的消息
 * 
 * @author DaxstUz 2416738717 2015年10月17日
 *
 */
public class MyMsgActivity extends FragmentActivity {

	private ViewPager mPager;
	private ArrayList<Fragment> fragmentList;

	private TextView tv_rc;
	private TextView tv_rcb;
	private TextView tv_update;
	private TextView tv_updateb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_msg);
		init();
	}

	private void init() {

		tv_update = (TextView) this.findViewById(R.id.tv_update);
		tv_updateb = (TextView) this.findViewById(R.id.tv_updateb);
		tv_rc = (TextView) this.findViewById(R.id.tv_rc);
		tv_rcb = (TextView) this.findViewById(R.id.tv_rcb);
		mPager = (ViewPager) this.findViewById(R.id.vp_mall);
		fragmentList = new ArrayList<Fragment>();
		Fragment btFragment = new ISayFragment();
		Fragment secondFragment = new IJoinFragment();
		fragmentList.add(btFragment);
		fragmentList.add(secondFragment);

		// 给ViewPager设置适配器
		mPager.setAdapter(new FragmentPagerAdapter(this
				.getSupportFragmentManager(), fragmentList));
		mPager.setCurrentItem(0);// 设置当前显示标签页为第一页
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());// 页面变化时的监听器
	}

	/**
	 * 设置title的 默认颜色
	 */
	private void setTextColor() {
		tv_update.setTextColor(getResources().getColor(R.color.white));
		tv_rc.setTextColor(getResources().getColor(R.color.white));
		tv_updateb.setBackgroundResource(R.color.white);
		tv_rcb.setBackgroundResource(R.color.white);
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageSelected(int arg0) {
			setTextColor();

			/* 显示选择当前的标签页 */
			switch (arg0) {
			case 0:
				tv_rc.setTextColor(getResources().getColor(R.color.light_blue));
				tv_rcb.setBackgroundResource(R.color.light_blue);
				break;
			case 1:
				tv_update.setTextColor(getResources().getColor(
						R.color.light_blue));
				tv_updateb.setBackgroundResource(R.color.light_blue);
				break;

			default:
				break;
			}
		}
	}

	public void onclick(View view) {
		switch (view.getId()) {
		case R.id.btn_show_back:
			this.finish();
			break;
		case R.id.fl_rc:
			mPager.setCurrentItem(0);
			break;
		case R.id.fl_update:
			mPager.setCurrentItem(1);
			break;

		default:
			break;
		}
	}
}
