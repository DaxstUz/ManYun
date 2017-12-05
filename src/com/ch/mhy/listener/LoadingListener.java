package com.ch.mhy.listener;

import com.ch.mhy.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;


public class LoadingListener implements ImageLoadingListener {
	
	private RotateAnimation refreshingAnimation;
	
	private ImageView imageView;
	private ImageView iv2;
	
	public LoadingListener(Context context,ImageView imageView,ImageView iv2) {
		// TODO Auto-generated constructor stub
		this.imageView=imageView;
		this.iv2=iv2;
		
		 refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
				 context, R.anim.rotating);
	        // 添加匀速转动动画
	        LinearInterpolator lir = new LinearInterpolator();
	        refreshingAnimation.setInterpolator(lir);
	}

	@Override
	public void onLoadingStarted(String imageUri, View view) {
		// TODO Auto-generated method stub
		imageView.setVisibility(View.GONE);
		iv2.setVisibility(View.VISIBLE);
		iv2.startAnimation(refreshingAnimation);
	}

	@Override
	public void onLoadingFailed(String imageUri, View view,
			FailReason failReason) {
		// TODO Auto-generated method stub
		iv2.clearAnimation();
		iv2.setVisibility(View.GONE);
		 new Handler() {
	            @Override
	            public void handleMessage(Message msg) {
		           imageView.setVisibility(View.VISIBLE);
	            }
	        }.sendEmptyMessageDelayed(0, 1000);
		
	}

	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		// TODO Auto-generated method stub
		iv2.clearAnimation();
		iv2.setVisibility(View.GONE);
		 new Handler() {
	            @Override
	            public void handleMessage(Message msg) {
		           imageView.setVisibility(View.VISIBLE);
	            }
	        }.sendEmptyMessageDelayed(0, 1000);
	}

	@Override
	public void onLoadingCancelled(String imageUri, View view) {
		// TODO Auto-generated method stub
		iv2.clearAnimation();
		iv2.setVisibility(View.GONE);
		 new Handler() {
	            @Override
	            public void handleMessage(Message msg) {
		           imageView.setVisibility(View.VISIBLE);
	            }
	        }.sendEmptyMessageDelayed(0, 1000);
	}

}
