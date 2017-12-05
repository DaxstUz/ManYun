package com.ch.mhy.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ch.mhy.application.MhyApplication;

/**
 * 网络请求帮助类
 * @author DaxstUz 2416738717
 * 2015年7月6日
 *
 */
public class NetUtil {
    public static final RequestQueue rqueue = Volley.newRequestQueue(MhyApplication.getApplication());
    
    public static boolean value=false;
    
    /**
     * 判断url地址是否有效
     * @param url
     * @return
     */
    public static boolean isEnable(final String urlst){
    	
    	try {
    		HttpURLConnection conn=(HttpURLConnection)new URL(urlst).openConnection();
    		int code=conn.getResponseCode();
    		System.out.println(">>>>>>>>>>>>>>>> "+code+" <<<<<<<<<<<<<<<<<<");
    		if(code!=200){
    		value=false;
    		}else{
    		value=true;
    		}
    		} catch (MalformedURLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		}
    		return value;
    }
    
    
    /**
     * 判断当前网络状态是否可用
     * @param context
     * @return
     */
    public static boolean checkNetWorkStatus(Context context){
    	boolean result;
    	ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo netinfo = cm.getActiveNetworkInfo();
    	if ( netinfo !=null && netinfo.isConnected() ) {
    	result=true;
//    	Log.i(TAG, "The net was connected" );
    	}else{
    	result=false;
//    	Log.i(TAG, "The net was bad!");
    	}
    	return result;
    	}
    	
    	
}
