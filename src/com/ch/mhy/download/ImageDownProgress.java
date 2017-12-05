package com.ch.mhy.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import com.ch.mhy.util.SDUtil;
import com.ch.mhy.util.UrlConstant;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public final class ImageDownProgress {
	private  final OkHttpClient client = new OkHttpClient();
	
	private Call call = null;
	public ImageDownProgress() {
	}

	public boolean startDown(String saveFilePath,String url,String urlLocal,String port) throws Exception {
		if(!(urlLocal.length()>0)){
			return true;
		}
		File file = new File(saveFilePath+urlLocal.substring(urlLocal.lastIndexOf("/")));
		if(file.exists()&&file.length()!=0){
			client.cancel(file);
			return true;
		}
			Request request = new Request.Builder().url(url).build();
			call = client.newCall(request);
	
			Response response = null;
			try {
				response = call.execute();
			} catch (Exception e1) {
				e1.printStackTrace();
				return false;
			}
			if (!response.isSuccessful()){//外网请求失败,请求内网
				 request = new Request.Builder().url(UrlConstant.Ip1+":"+port+"/"+urlLocal).build();
				 call = client.newCall(request);
				 response = call.execute();//同步进行图片下载
				 if (!response.isSuccessful()){//内网也没有，下载哭脸图片
					 String[] portArray = {"8080","8081","8082"};
					 String port1 = portArray[(int) (Math.random()*2)] ;
					 String urlerror = "http://219.135.99.159:"+port1+"/firstTurn/d17a64a81f1f4e388196b80c34dc5feb.jpg";
					 request = new Request.Builder().url(urlerror).build();
					 call = client.newCall(request);
					 response = call.execute();//同步进行图片下载
				 }
			}
			
			File storeFile = new File(saveFilePath);
            if (!storeFile.exists()) {
                // 若不存在，创建目录，可以在应用启动的时候创建
            	storeFile.mkdirs();
                try {//创建该文件，使得文件夹下的媒体文件不被图库扫描 2015-06-26 xc.li
					new File(storeFile, ".nomedia").createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
            }
			FileOutputStream output = null;
			try {
				if(call!=null&&!call.isCanceled()){
					output = new FileOutputStream(saveFilePath+urlLocal.substring(urlLocal.lastIndexOf("/")));
					// 得到网络资源的字节数组,并写入文件
					output.write(response.body().bytes());
					output.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				if(output!=null){
					output.close();
				}
				if(call!=null){
					call.cancel();
				}
			}
		return true;
	}
	
	
	public void renotify(){
		if(call!=null){
			call.notifyAll();
		}
		
	}
	/**
	 * 删除文件夹时要用到，停止下载
	 * @throws Exception
	 */
	public void stopDown() throws Exception{
		if(call!=null){
			if(!call.isCanceled()){
				call.cancel();
				call=null;
			}
		}
	}
	
	/**
	 * 下载暂停
	 * @throws InterruptedException 
	 */
	public void downWait() throws InterruptedException{
		if(call!=null){
			if(!call.isCanceled()){
				call.wait();
			}
		}
	}
	
	/*
	 * 继续下载
	 */
	public void downNotifi(){
		if(call!=null){
			if(!call.isCanceled()){
				try {
					call.notify();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
