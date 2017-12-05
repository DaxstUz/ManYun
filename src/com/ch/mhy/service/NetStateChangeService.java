package com.ch.mhy.service;

import java.util.List;

import com.ch.mhy.net.NetReceiver;
import com.ch.mhy.net.NetReceiver.NetState;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.widget.Toast;

/**
 * 网络状态改变服务及广播监听
 * @author xc.li
 * @date 2015年6月26日
 */
public class NetStateChangeService extends Service {
	private NetStateChangeReceiver netStateReceiver;

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	/**
	 * 当服务创建时，注册广播
	 */
	@Override
	public void onCreate() {
		netStateReceiver = new NetStateChangeReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(Integer.MAX_VALUE);
		//监听网络状态改变事件
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(netStateReceiver, filter);
		super.onCreate();
	}

	/**
	 * 当服务销毁时，同时取消广播接收者,销毁接收对象
	 */
	@Override
	public void onDestroy() {
		this.unregisterReceiver(netStateReceiver);
		netStateReceiver = null;
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 网络状态改变事件广播接收
	 * @author xc.li
	 * @date 2015年6月26日
	 */
	class NetStateChangeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			NetState netState = NetReceiver.isConnected(context);//获取网络状态
			//没有网络且APP在前台运行时，提示并且做其他事情:例如取消当前请求；
			if (NetState.NET_NO.equals(netState) && !isAppRunBg(context)) {
				Toast.makeText(context, "当前无网络，请检查您的手机是否已连接网络！", Toast.LENGTH_LONG).show();
			}
		}

	}
	
	/**
	 * 判断漫云APP是否运行在后台
	 * @return boolean
	 */
	public boolean isAppRunBg(Context context){
		//获取活动状态的服务
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		boolean isRunningBg = false;//是否运行在后台，默认否
		for (RunningAppProcessInfo info : appProcesses) {
			if(info.processName.equals("com.ch.mhy")){//漫云APP做判断
				/*
				 * BACKGROUND=400 EMPTY=500 FOREGROUND=100 GONE=1000 PERCEPTIBLE=130
				 * SERVICE=300 ISIBLE=200
				 */
				if (info.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {// 处于后台
					isRunningBg = true;
				}
				break;
			}
		}
		return isRunningBg;
	}
}
