package com.ch.comm.resquest;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ch.mhy.util.NetUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 * 数据请求帮助类
 * 
 * @author xc.li
 * @date 2015年7月7日
 */
public class RequestDataUtil {
	
	/**
	 * 默认每页查询条数
	 */
	public static final int pageSize = 10;
	
	/**
	 * 分页数据请求
	 * @param url 地址
	 * @param param 请求参数
	 * @param data 返回数据处理类
	 */
	public static void requestPageData(Context context, String url, 
			HashMap<String, Object> param, AbsResponseData data) {
		sendRequest(context, url, param, 1, data);
	}

	/**
	 * 实体对象请求
	 * @param context
	 * @param url
	 * @param param
	 * @param data
	 */
	public static void requestObjectData(Context context, String url, 
			HashMap<String, Object> param, AbsResponseData data){
		sendRequest(context, url, param, 2, data);
	}

	/**
	 * 数据更新保存请求
	 * @param context
	 * @param url
	 * @param param
	 * @param data
	 */
	public static void updateData(Context context, String url, 
			HashMap<String, Object> param, AbsResponseData data){
		sendRequest(context, url, param, 3, data);
	}
	
	/**
	 * 获取数据
	 * @param context
	 * @param url
	 * @param param
	 * @param dataRes
	 */
	public static void  getData(Context context,String url, JSONObject param, AbsResponseData dataRes){
		sendRequest(context, url, param, dataRes);
	}
	
	/**
	 * 发送请求
	 * @param context
	 * @param url
	 * @param param
	 * @param type
	 * @param dataRes
	 */
	private static void sendRequest(Context context, String url,
			JSONObject param, final AbsResponseData dataRes) {
		JsonObjectRequest joObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, param,
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						List<JSONObject > list=new ArrayList<JSONObject>();
						try {
							if(response.getBoolean("result")){
								if(response.get("object")  instanceof JSONObject) {
									if(response.getJSONObject("object").has("data")){
										if(response.getJSONObject("object").get("data") instanceof JSONArray){
											JSONArray array=response.getJSONObject("object").getJSONArray("data");
											for (int i = 0; i < array.length(); i++) {
												list.add(array.getJSONObject(i));
											}
											dataRes.dataBusi(list);
										}
									}
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("Content-Type", "application/json; charset=utf-8");
				return map;
			}
		};

		joObjectRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		NetUtil.rqueue.add(joObjectRequest);
	}

	/**
	 * 请求发送
	 * @param context
	 * @param url
	 * @param param
	 * @param type 1：分页列表请求；2：单个对象请求；3：更新保存数据请求
	 * @param data
	 */
	private static void sendRequest(final Context context, final String url, 
			final HashMap<String, Object> param, final int type, final AbsResponseData data) {
		JSONObject params = toJSONOBject(param);
		JsonObjectRequest joObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, params,
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						convertData(type, data, response);
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						
					}
				}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("Content-Type", "application/json; charset=utf-8");
				return map;
			}
		};

		joObjectRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		NetUtil.rqueue.add(joObjectRequest);
	}
	
	private static void convertData(final int type,
			final AbsResponseData data, JSONObject response) {
		try {
			if(type == 3) {// 保存更新数据
				String msg = null;
				if(response.has("object")){
					msg = response.getString("object");
				}else{
					msg = response.getString("message");
				}
				data.dataBusi(msg);
			}else{
				JSONObject joObject = new JSONObject();
				JSONArray array = new JSONArray();
				Object responseObj = response.get("object");
				if (responseObj instanceof JSONObject) {
					joObject = response.getJSONObject("object");
					if(joObject.has("data")){
						array = joObject.getJSONArray("data");
					}
				}else if (responseObj instanceof JSONArray) {
					array = response.getJSONArray("object");
				}else{
					joObject.put("object", responseObj);
				}
				
				Class<?> clz = data.getClz();
				if (type == 1) {// 分页返回列表
					if (clz != null && array != null) {
						ArrayList<Object> dataList = new ArrayList<Object>();
						for (int i = 0; i < array.length(); i++) {// 数据转换
							JSONObject jsonObject = array.getJSONObject(i);
							Object obj = clz.newInstance();
							RequestDataUtil.toEntity(jsonObject, obj);
							dataList.add(obj);
						}
						data.dataBusi(dataList);
					} else {//未设置实体对象，则直接返回
						data.dataBusi(array);
					}
				} else if (type == 2) {// 单个对象返回
					if(joObject != null && !"".equals(joObject) && joObject.has("object")){
						Object jo = joObject.get("object");
						if(jo instanceof JSONObject){
							joObject = joObject.getJSONObject("object");
						}else{
							data.dataBusi(joObject);
							return;
						}
					}
					if (joObject != null && clz != null) {
						Object obj = clz.newInstance();
						RequestDataUtil.toEntity(joObject, obj);
						data.dataBusi(obj);
					}else{//未设置实体对象，则直接返回
						data.dataBusi(joObject);
					}
				} 
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将HashMap类型参数转换为JSONObject
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject toJSONOBject(HashMap<String, Object> params){
		JSONObject json = new JSONObject();
		if(params == null){
			return json;
		}
		if(params.containsKey("object")){
			Object obj = params.get("object");
			if(obj instanceof HashMap){
				JSONObject jobj = toJSONOBject((HashMap<String, Object>)obj);
				params.put("object", jobj);
			}
		}
		Iterator<String> it = params.keySet().iterator();
		try {
			while(it.hasNext()){
				String key = it.next();
				json.put(key, params.get(key));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	/**
	 * 把JSONObject对象转换成实体对象
	 * @param joObject
	 * @param entity
	 */
	public static void toEntity(JSONObject joObject, Object entity){
		Class<?> clazz = entity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(int i=0; i<fields.length; i++){
			try {
				String name = fields[i].getName();
				String methodStr = "set"+firstCharUpperCase(name);
				Object obj = joObject.get(name);
				if(obj != null){
					Method method = clazz.getDeclaredMethod(methodStr, new Class[]{fields[i].getType()});
					method.invoke(entity, new Object[]{obj});
				}
			} catch (JSONException e) {
				continue;
			} catch (NoSuchMethodException e) {
				continue;
			} catch (IllegalAccessException e) {
				continue;
			} catch (IllegalArgumentException e) {
				continue;
			} catch (InvocationTargetException e) {
				continue;
			}
		}
	}
	
	/**
	 * 将实体转换为HashMap
	 * @param map
	 * @param entity
	 */
	public static void entityToMap(HashMap<String, Object> map, Object entity){
		Class<?> clazz = entity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(int i=0; i<fields.length; i++){
			try {
				String name = fields[i].getName();
				String methodStr = "get"+firstCharUpperCase(name);
				Method method = clazz.getDeclaredMethod(methodStr);
				Object obj = method.invoke(entity);
				map.put(name, obj);
			} catch (NoSuchMethodException e) {
				continue;
			} catch (IllegalAccessException e) {
				continue;
			} catch (IllegalArgumentException e) {
				continue;
			} catch (InvocationTargetException e) {
				continue;
			}
		}
	}
	/**
	 * 将字符串首字母转化成大写
	 * @param s
	 * @return
	 */
	public static String firstCharUpperCase(String s){
		if (s == null || "".equals(s))
			return ("");
		return s.substring(0, 1).toUpperCase(Locale.CHINESE) + s.substring(1);
	}
	
	public static DisplayImageOptions options = new DisplayImageOptions.Builder()
	    /*.showImageOnLoading(R.drawable.ic_loading_small) //设置图片在下载期间显示的图片         
	    .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
	    .showImageOnFail(R.drawable.dpcq_01)*/ // 设置图片加载或解码过程中发生错误显示的图片
		.cacheInMemory(false) // 设置下载的图片是否缓存在内存中
		.cacheOnDisc(false) // 设置下载的图片是否缓存在SD卡中
		.displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
	    .bitmapConfig(Bitmap.Config.RGB_565).build();
	
	/**
	 * 进度条开始通知标识
	 */
	public static final int LOAD_PGRESS_START = 10000;

	/**
	 * 进度条更新通知标识
	 */
	public static final int LOAD_PGRESS_UPDATE = 10001;
	
	/**
	 * 图片加载器
	 * @param imgUrl
	 * @param imageView
	 * @param options
	 */
	public static void loadImage(String imgUrl, final ImageView imageView, final Handler handler) {
		
		ImageLoader.getInstance().displayImage(imgUrl, imageView, options,
				new ImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						//通知初始化进度条
						if(handler != null){
							Message msg = new Message();
							msg.what = LOAD_PGRESS_START;
							msg.obj = "0";
							handler.handleMessage(msg);
						}
					}

					@Override
					public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
						//imageView.setImageResource(0);
						switch (failReason.getType()) {
		                case IO_ERROR:
		                    break;
		                case DECODING_ERROR:
		                    break;
		                    
		                case NETWORK_DENIED:
		                    break;
		                    
		                case OUT_OF_MEMORY:
		                    break;
		                    
		                case UNKNOWN:
		                    break;
		                default:
		                    break;
		                }

					}

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						imageView.setImageBitmap(loadedImage);
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {//取消加载任务
						ImageLoader.getInstance().cancelDisplayTask(imageView);
					}
				}, 
				new ImageLoadingProgressListener() {

					@Override
					public void onProgressUpdate(String imageUri, View view, int current, int total) {
						if(handler != null){
							NumberFormat numberFormat = NumberFormat.getInstance();// 设置精确到小数点后2位
							numberFormat.setMaximumFractionDigits(0);
							String result = numberFormat.format((float) current / (float) total * 100);
							//通知更新进度条
							Message msg = new Message();
							msg.what = LOAD_PGRESS_UPDATE;
							msg.obj = result;
							handler.handleMessage(msg);
						}
					}
				});
	}
	
	
	
	
	/**
	 * 连接服务器失败
	 */
	public static final String CONN_FAILD_ERR_CODE = "ERR-10001";
	/**
	 * 连接服务器失败
	 */
	public static final String URL_ERR_CODE = "ERR-404";
}
