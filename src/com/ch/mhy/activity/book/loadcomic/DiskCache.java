package com.ch.mhy.activity.book.loadcomic;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.ch.mhy.util.Utils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.open.utils.SystemUtils;

/**
 * sdcard cache
 * @author xc.li
 * @date 2015-8-17
 */
public class DiskCache {
//	private static OkHttpClient okclient = new OkHttpClient();
//	/**
//	 * 缓存路径
//	 */
//	private static String cachePath = Utils.getCachePath();
//	/**
//	 * 缓存文件夹路径
//	 */
//	private static String dirPath = "";
//	/**
//	 * 缓存数，默认20张
//	 */
//	private static int cacheSize = 20;
//	/**
//	 * 缓存记录key=url.hashCode(); value=System.currentTimeMillis()
//	 */
//	private static LinkedHashMap<Integer, String> diskCaches = 
//			new LinkedHashMap<Integer, String>(0, 0.75f, true);
//	
//	/**
//	 * FIFO列表
//	 */
//	private static ArrayList<Integer> hashCodes = new ArrayList<Integer>();
//	
//	/**
//	 * 获取缓存长度
//	 * @return
//	 */
//	public static int getCacheSize() {
//		return cacheSize;
//	}
//	/**
//	 * 设置缓存长度
//	 */
//	public static void setCacheSize(int size) {
//		cacheSize = size;
//	}
//
//	/**
//	 * 获取网络图片, 自动计算缩放比
//	 * @param url
//	 * @return Bitmap对象，失败时返回null
//	 */
//	public static Bitmap getImgFromNet(String url){
//		int scale = getScale(url);
//		if(scale == -1){
//			return null;
//		}
//		return getImgFromNet(url, scale);
//	}
//	
//	/**
//	 * 获取网络图片
//	 * @param url
//	 * @param scale
//	 * @return Bitmap对象，失败时返回null
//	 */
//	public static Bitmap getImgFromNet(String url, int scale){
//		try {
//			Request request = new Request.Builder().url(url).build();
//			Call call = okclient.newCall(request);
//			Response response = call.execute();
//			if(response.isSuccessful()){
//				InputStream in = response.body().byteStream();
//				BitmapFactory.Options option = new BitmapFactory.Options();
//				option.inSampleSize = scale;
//				option.inPreferredConfig = Config.RGB_565;
//				Bitmap bm = BitmapFactory.decodeStream(in, null, option);
//				in.close();
//				response = null;
//				request = null;
//				if(!call.isCanceled())
//					call.cancel();
//				return bm;
//			}else{
//				response = null;
//				request = null;
//				if(!call.isCanceled())
//					call.cancel();
//			}
//		} catch (IOException e) {
//			Log.e("tag", " 查询超时  ");
//			//e.printStackTrace();
//			return null;
//		}
//		return null;
//	}
//	
//	/**
//	 * 获取网络图片, 优先远程图片地址
//	 * @param url
//	 * @param localurl
//	 * @return Bitmap对象，失败时返回null
//	 */
//	public static Bitmap getImgFromNet(String url, String localurl){
//		Bitmap bm = null;
//		int scale = getScale(url);//请求远程地址，计算缩放比
//		if(scale == -1){//请求失败，后请求本地服务地址
//			scale = getScale(localurl);
//			if(scale == -1){
//				return null;
//			}else{
//				bm = getImgFromNet(localurl, scale);
//			}
//		}else{
//			bm = getImgFromNet(url, scale);
//			if(bm == null){
//				bm = getImgFromNet(localurl, scale);
//			}
//		}
//		return bm;
//	}
//	
//	/**
//	 * 计算图片缩放比
//	 * @param url
//	 * @return 缩放比，为-1时表示地址不可用，请求失败
//	 */
//	public static int getScale(String url){
//		Request request = new Request.Builder().url(url).build();
//		try {
//			okclient.setConnectTimeout(3, TimeUnit.SECONDS);
//			Call call = okclient.newCall(request);
//			Response response = call.execute();
//			if(response.isSuccessful()){
//				BitmapFactory.Options options = new BitmapFactory.Options();//BitmapFactory参数
//				options.inJustDecodeBounds = true;//该参数为true时不会为对象分配内存
//				InputStream in = response.body().byteStream();
//				BitmapFactory.decodeStream(in, null, options);
//				int width = options.outWidth;//原始图片宽
//				int height = options.outHeight;//原始图片高
//				int[] scrWh = Utils.getScreenDispaly();//获取屏幕大小
//				int wscale = width / scrWh[0];//横向比
//				int hscale = height / scrWh[1];//纵向比
//				in.close();
//				response = null;
//				request = null;
//				if(!call.isCanceled())
//					call.cancel();
//				return (wscale > hscale ? wscale : hscale);//最终缩放比
//			}else{
//				response = null;
//				request = null;
//				if(!call.isCanceled())
//					call.cancel();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			return -1;
//		}
//		return -1;
//	}
//	/**
//	 *  
//	 * @param url
//	 * @return
//	 */
//	public static boolean isCache(String url){
//		return hashCodes.contains(url.hashCode());
//	}
//	
//	/**
//	 * 将图片加入到缓存
//	 * @param key url
//	 * @param bm
//	 */
//	public static void addDiskCache(String key, Bitmap bm) {
//		//1.达到了上限值，则先删除最早的cache
//		if(diskCaches.size()>=cacheSize){
//			recycleCache(0);
//		}
//		//2.创建缓存文件
//		StringBuffer filePath = new StringBuffer(128);
//		filePath.append(cachePath).append(dirPath);
//		
//		File dirFile = new File(filePath.toString());
//		if(!dirFile.exists()){
//			dirFile.mkdirs();
//		}
//		int urlHashCode = key.hashCode();
//		filePath.append(File.separator).append(urlHashCode);
////		Log.e("writetag", "filePath="+filePath.toString());
//		try {
//			synchronized (diskCaches) {
//				saveFile(bm, filePath.toString());
//				//3.加入cache记录
//				hashCodes.add(urlHashCode);
//				diskCaches.put(urlHashCode, key);
//			}
//			Log.d("tag", "save 完了。");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//	
//	/**
//	 * 保存缓存图片
//	 * @param bm
//	 * @param filePath
//	 * @throws IOException
//	 */
//	public synchronized static void saveFile(Bitmap bm, String filePath) throws IOException {
//		File myCaptureFile = new File(filePath);
//		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
//		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
//		bos.flush();
//		bos.close();
//		Log.d("tag", "saveFile overn");
//	}
//
//	/**
//	 * 获取缓存图片
//	 * @param key
//	 * @return
//	 */
//	public static Bitmap getCache(String key, String localurl){
//		int urlHashCode = key.hashCode();
//		StringBuffer filePath = new StringBuffer(128);
//		filePath.append(cachePath).append(dirPath).append(File.separator).append(urlHashCode);
//		Bitmap bm = null;
//		if(diskCaches.containsKey(urlHashCode)){//存在时，直接加载
//			bm = BitmapFactory.decodeFile(filePath.toString());
//		}else{//从网络获取
//			ImageCacheLoad load = new ImageCacheLoad(key, localurl);
//			synchronized(load){
//				while(load.isRunning()){//等待加载结束
//					try {
//						load.wait(20);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			bm = load.getBitmap();
//			load = null;
//		}
//		return bm;
//	}
//	
//	/**
//	 * 获取缓存图片
//	 * @param key
//	 * @return
//	 */
//	public static String getCachePath(String key, String localurl){
//		int urlHashCode = key.hashCode();
//		StringBuffer filePath = new StringBuffer(128);
//		filePath.append(cachePath).append(dirPath).append(File.separator).append(urlHashCode);
//		if(diskCaches.containsKey(urlHashCode)){//存在时，直接加载
//			
//			return filePath.toString();
//		}else{//从网络获取
//			return null;
//		}
//	}
//	
//	/**
//	 * 回收清除单个缓存
//	 * @param flag: 0-FIFO, 1- FILO
//	 */
//	public static void recycleCache(int flag) {
//		//1.清除缓存文件
//		int urlHashCode = (flag==0?hashCodes.remove(0) : hashCodes.remove(hashCodes.size()-1));
//		StringBuffer filePath = new StringBuffer(128);
//		filePath.append(cachePath).append(dirPath).append(File.separator).append(urlHashCode);
//		File file = new File(filePath.toString());
//		if(file.exists()){
//			file.delete();
//		}
////		Utils.map.remove(diskCaches.get(urlHashCode));
//		//2.清除cache记录
//		diskCaches.remove(urlHashCode);
//	}
//
//	/**
//	 * 清除所有缓存
//	 */
//	public static void recycleCaches() {
//		//1.清除缓存文件
//		StringBuffer filePath = new StringBuffer(128);
//		filePath.append(cachePath).append(dirPath);
//		File dirFile = new File(filePath.toString());
//		if(dirFile.exists()){
//			dirFile.delete();
//		}
//		//2.清除cache记录
//		hashCodes.clear();
//		diskCaches.clear();
//	}
//
//	public static String getDirPath() {
//		return dirPath;
//	}
//
//	/**
//	 * 设置缓存文件夹
//	 * @param dirPath
//	 */
//	public static void setDirPath(String dirPath) {
//		DiskCache.dirPath = dirPath;
//	}
//	
}
