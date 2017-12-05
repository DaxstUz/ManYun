package com.ch.comm.saevent;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ch.mhy.util.NetUtil;
import com.ch.mhy.util.TelPhoneInfo;
import com.ch.mhy.util.UrlConstant;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

/**
 * 统计事件
 * 
 * @author xc.li
 * @date 2015-9-14
 */
public class MobSaAgent {

	/**
	 * 点击事件
	 * 
	 * @param context
	 * @param eventId
	 * @throws NameNotFoundException 
	 */
	public static void onEvent(Context context, String eventId) throws NameNotFoundException {
		initParam(context,null,eventId);
	}

	/**
	 * 统计事件
	 * 
	 * @param context
	 * @param eventId
	 * @param map
	 * @throws NameNotFoundException 
	 */
	public static void onEvent(Context context, String eventId,
			HashMap<String, String> map) throws NameNotFoundException {
		initParam(context,map,eventId);
	}

	/**
	 * 统计事件
	 * 
	 * @param context
	 * @param eventId
	 * @param map
	 * @param value
	 * @throws NameNotFoundException 
	 */
	public static void onEventValue(Context context, String eventId,
			HashMap<String, String> map, double value) throws NameNotFoundException {
		initParam(context,map,eventId,value);
	}

	/**
	 * 从Manifest.xml配置文件中获取数据
	 * 
	 * @param context
	 * @param metaKey
	 * @return
	 */
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String metaValue = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				metaValue = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {
		}
		return metaValue;// xxx
	}

	/**
	 * 初始化请求参数
	 * @throws NameNotFoundException 
	 */
	public static void initParam(Context context,HashMap<String, String> map,String eventId,double value) throws NameNotFoundException {
		ApplicationInfo ai =context.getApplicationInfo();
		PackageInfo info = context.getPackageManager().getPackageInfo(ai.packageName, 0);
		
	    ApplicationInfo appInfo = context.getPackageManager()
                .getApplicationInfo(ai.packageName,
        PackageManager.GET_META_DATA);
	    Object msg=appInfo.metaData.get("UMENG_CHANNEL");

		JSONObject json = new JSONObject();
		try {
			json.put("appPkg", ai.packageName); // 应用包路径
			json.put("eventCode", eventId); // 事件编码
			json.put("deviceId", TelPhoneInfo.getDeviceId());// 设备号
			json.put("channelCode", msg); // 推广渠道
			json.put("appVer",info.versionName); // 版本号
			json.put("platForm", 1); // 1：android；2：ios
			json.put("value", value); // 统计事件时需要
			JSONObject jb;// 统计项
			if(map!=null){
				jb=new JSONObject(map);
				json.put("map", jb);
			}else{
				Map<String, String> map2=new HashMap<String, String>();
				map2.put(eventId, eventId);
				jb=new JSONObject(map2);
				json.put("map", jb);
			}

			commit(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化请求参数
	 * @throws NameNotFoundException 
	 */
	public static void initParam(Context context,HashMap<String, String> map,String eventId) throws NameNotFoundException {
		ApplicationInfo ai =context.getApplicationInfo();
		PackageInfo info = context.getPackageManager().getPackageInfo(ai.packageName, 0);
		
		ApplicationInfo appInfo = context.getPackageManager()
				.getApplicationInfo(ai.packageName,
						PackageManager.GET_META_DATA);
		Object msg=appInfo.metaData.get("UMENG_CHANNEL");
		
		JSONObject json = new JSONObject();
		try {
			json.put("appPkg", ai.packageName); // 应用包路径
			json.put("eventCode", eventId); // 事件编码
			json.put("deviceId", TelPhoneInfo.getDeviceId());// 设备号
			json.put("channelCode", msg); // 推广渠道
			json.put("appVer",info.versionName); // 版本号
			json.put("platForm", 1); // 1：android；2：ios
			JSONObject jb;// 统计项
			if(map!=null){
				jb=new JSONObject(map);
				json.put("map", jb);
			}else{
				Map<String, String> map2=new HashMap<String, String>();
				map2.put(eventId, eventId);
				jb=new JSONObject(map2);
				json.put("map", jb);
			}
			
			commit(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * 提交到后台服务器
	 */
	private static void commit(JSONObject joObject) {
		JSONObject joOb=new JSONObject();
		try {
			joOb.put("object", joObject);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonObjectRequest joqRequest = new JsonObjectRequest(Method.POST,
				UrlConstant.UtlCommit, joOb, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
					}
				}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				// TODO Auto-generated method stub
				Map<String, String> map = new HashMap<String, String>();
				map.put("Content-Type", "application/json; charset=utf-8");
				return super.getHeaders();
			}
		};

		joqRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		NetUtil.rqueue.add(joqRequest);
	}

}
