package com.ch.mhy;

import java.util.ArrayList;

import com.tencent.weibo.sdk.android.api.util.SharePersistent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * 聚漫导向图
 * @author xc.li
 * @date 2015年7月16日
 */
public class GuideActivity extends Activity {

	/**
	 * 指示器布局
	 */
	private LinearLayout dotGroup;

	/**
	 * 滚动图片指示器-视图列表
	 */
	public ImageView[] mImageViews = null;

	/**
	 * 手机密度
	 */
	private float mScale;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_guid);
		
		SharedPreferences sp=this.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
		/**
		 * 是否是首次使用
		 */
		boolean isFirst=sp.getBoolean("isfirst", true);
		if(!isFirst){
			Intent intent = new Intent();
			intent.setClass(GuideActivity.this, MainActivity.class);
			GuideActivity.this.startActivity(intent);
			this.finish();
		}else{
			Editor editor=sp.edit();
			editor.putBoolean("isfirst", false);
			editor.commit();
			initGuideViewPager();
		}
		
	}


	/**
	 * 初始化导向图ViewPager
	 */
	private void initGuideViewPager(){
		mScale = this.getResources().getDisplayMetrics().density;

		dotGroup = (LinearLayout)this.findViewById(R.id.dotGroup);
		ViewPager guideViewPager = (ViewPager)this.findViewById(R.id.guid_viewpager);
		ArrayList<View> viewList = new ArrayList<View>();

		//添加导向页
		viewList.add(LayoutInflater.from(this).inflate(R.layout.activity_guid_fir_page, null));
		viewList.add(LayoutInflater.from(this).inflate(R.layout.activity_guid_two_page, null));
		viewList.add(LayoutInflater.from(this).inflate(R.layout.activity_guid_for_page, null));

		mImageViews = new ImageView[viewList.size()];

		/*动态添加指示器*/
		for (int i = 0; i < viewList.size(); i++) {
			ImageView	mImageView = new ImageView(this);
			int imageParams = (int) (mScale * 10 + 0.5f);// XP与DP转换，适应不同分辨率
			int imagePadding = (int) (mScale * 5 + 0.5f);

			LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(imageParams, imageParams);
			lp.gravity= Gravity.CENTER;
			mImageView.setLayoutParams(lp);
			mImageView.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
			mImageViews[i] = mImageView;
			if (i == 0) {
				mImageViews[i].setBackgroundResource(R.drawable.dot_selected);
			} else {
				mImageViews[i].setBackgroundResource(R.drawable.dot_default);
			}
			dotGroup.addView(mImageViews[i]);
		}

		TabPagerAdapter mAdapter = new TabPagerAdapter(viewList);
		guideViewPager.setAdapter(mAdapter);
		guideViewPager.setOnPageChangeListener(new MyPageChangeListener(viewList));



		initButton();
	}

	private Button guideBtn;
	/**
	 * 初始化按钮组件
	 */
	private void initButton(){
		guideBtn = (Button)this.findViewById(R.id.guideBtn);
		guideBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				//判断是否是第一次使用聚漫
				intent.setClass(GuideActivity.this, MainActivity.class);
				GuideActivity.this.startActivity(intent);
				GuideActivity.this.finish();
			}
		});
	}
	
	public void onclick(View view){
		switch (view.getId()) {
//		case R.id.btn_gone:
//			Intent intent = new Intent();
//			//判断是否是第一次使用聚漫
//			intent.setClass(GuideActivity.this, SexSelectActivity.class);
//			GuideActivity.this.startActivity(intent);
//			break;

		default:
			break;
		}
	}
	
	
	/**
	 *
	 * @author xc.li
	 * @date 2015年7月16日
	 */
	class MyPageChangeListener implements OnPageChangeListener{
		ArrayList<View> viewList;
		public MyPageChangeListener(ArrayList<View> viewList){
			this.viewList = viewList;
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			mImageViews[position].setBackgroundResource(R.drawable.dot_selected);
			// 设置图片滚动指示器背景
			for (int i = 0; i < mImageViews.length; i++) {
				if (position != i) {
					mImageViews[i].setBackgroundResource(R.drawable.dot_default);
				}
				if(position == viewList.size()-1){//当到达最后一页时
					dotGroup.getChildAt(position-1).setBackgroundResource(R.drawable.dot_default);
					guideBtn.setVisibility(View.VISIBLE);
				}else{
					guideBtn.setVisibility(View.GONE);
				}
			}
		}

	}
}
