package com.ch.mhy.util;

import android.app.ProgressDialog;
import android.content.Context;

public class Loading {

	private static ProgressDialog pd;
	//开始请求网络
	public static void startnet(Context content) {
		if(content!=null){
			pd=new ProgressDialog(content);
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setMessage("正在获取用户信息...");
			pd.setCancelable(true); // 璁剧疆瀵硅瘽妗嗚兘鐢�鍙栨秷"鎸夐挳鍏抽棴
			pd.setCanceledOnTouchOutside(false);
			pd.show();
		}
	}
	
	//结束
	public static void endnet(){
		if(pd!=null){
			pd.dismiss();
		}
	}
}