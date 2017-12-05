package com.ch.mhy.activity.my;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ch.mhy.R;
import com.ch.mhy.util.DataCleanManager;
import com.ch.mhy.util.NetUtil;
import com.ch.mhy.util.UrlConstant;
import com.ch.mhy.util.Utils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

/**
 * 通用设置
 * @author DaxstUz 2416738717
 * 2015年8月4日
 *
 */
public class GeneralsettingActivity extends Activity
{
	ImageButton wifiread;
	ImageButton wifidown;	
	
	TextView tv;
	
	Button tt;// 窗口上面的后退按钮所在的LinearLayout
	
	LinearLayout tv0;
	LinearLayout tv1;
	LinearLayout tv2;
	LinearLayout tv3;
	LinearLayout tv4;
	
	PackageManager mPackageManager;
	
	long totalCacheSize;
	ProgressDialog mypDialog;
	Handler myHandler;
	UMSocialService mController;
	String path;
	
	Toast toast;

	SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generalsetting);
		
		sp = this.getSharedPreferences("userConfig", 0);
		
		init();// 初始化所有的控件
		
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		shareMySoftware();// 初始化分享信息
		
		myHandler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				
				if (msg != null && msg.what == 1)
				{
					String size = (String) msg.obj;
					tv.setText(size);
				}
				if (msg != null && msg.what == 2)
				{
					String name = (String) msg.obj;
					if (mypDialog != null && name.equals("success"))
					{
						mypDialog.dismiss();
						
						String str=DataCleanManager.getAllCacheSize(GeneralsettingActivity.this);
						tv.setText(str);
					}
				}
			}
		};
		
		try
		{
			
			tv.setText(DataCleanManager.getAllCacheSize(this));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// 初始化所有控件的方法
	public void init()
	{
		wifiread = (ImageButton) this.findViewById(R.id.togglebutton1);
		wifidown = (ImageButton) this.findViewById(R.id.togglebutton2);
		
		this.setImageButton(wifiread);
		this.setImageButton(wifidown);
		
		wifiread.setOnClickListener(new MyListener());
		wifidown.setOnClickListener(new MyListener());
		
		tv = (TextView) this.findViewById(R.id.clearcache);// 清理缓存
		
		tv0 = (LinearLayout) this.findViewById(R.id.clearlayout);
		tv1 = (LinearLayout) this.findViewById(R.id.tofriend);
		tv2 = (LinearLayout) this.findViewById(R.id.torefresh);
		tv3 = (LinearLayout) this.findViewById(R.id.tous);
		tv4=(LinearLayout) this.findViewById(R.id.towork);
		
		tt = (Button) this.findViewById(R.id.btn_show_back_setting);// 实例化窗口上面的后退按钮所在的LinearLayout
	}
	
	public class MyListener implements OnClickListener
	{
		@Override
		public void onClick(View buttonView)
		{
			if (buttonView.getId() == R.id.togglebutton1)// 点击了非网络阅读提示按钮
			{
				GeneralsettingActivity.this.setSharedPreferences(
						"wifiread", wifiread);// key1
			}
			if (buttonView.getId() == R.id.togglebutton2)// 点击了仅在wifi下下载按钮
			{
				GeneralsettingActivity.this.setSharedPreferences(
						"wifidown", wifidown);// key2
			}
		}
	}
	
	// 不重复显示Toast的方法
	private void showTextToast(String msg)
	{
		if (toast == null)
		{
			toast = Toast.makeText(getApplicationContext(), msg, 500);
		}
		else
		{
			toast.setText(msg);
		}
		toast.show();
	}
	
	// 关闭Toast的方法
	private void closeToast(Toast toast)
	{
		Object obj = null;
		if (toast != null)
		{
			try
			{
				// 通过反射技术，从toast对象中获取mTN对象
				Field field = toast.getClass().getDeclaredField("mTN");
				field.setAccessible(true);
				obj = field.get(toast);
				Method method = obj.getClass().getDeclaredMethod("hide", null);
				method.invoke(obj, null);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	// 改变用户偏好参数设置的方法
	public void setSharedPreferences(String name, ImageButton button)
	{
		if(button.getId()==R.id.togglebutton1)
		{
			if(sp.getBoolean("wifiread", false))
			{
				button.setImageResource(R.drawable.new_off_button_my);
				Editor ed=sp.edit();
				ed.putBoolean(name, false);
				ed.commit();
			}
			else
			{
				button.setImageResource(R.drawable.new_on_button_my);
				Editor ed=sp.edit();
				ed.putBoolean(name, true);
				ed.commit();
			}
		}
		
		if(button.getId()==R.id.togglebutton2)
		{
			if(sp.getBoolean("wifidown", false))
			{
				button.setImageResource(R.drawable.new_off_button_my);
				Editor ed=sp.edit();
				ed.putBoolean(name, false);
				ed.commit();
			}
			else
			{
				button.setImageResource(R.drawable.new_on_button_my);
				Editor ed=sp.edit();
				ed.putBoolean(name, true);
				ed.commit();
			}
		}
	}

	// 读取用户偏好参数的设置
	public void setImageButton(ImageButton button)
	{
		if (button.getId() == R.id.togglebutton1)
		{
			if(sp.getBoolean("wifiread", true))
			{
				button.setImageResource(R.drawable.new_on_button_my);
			}
			else
			{
				button.setImageResource(R.drawable.new_off_button_my);
			}
		}
		if (button.getId() == R.id.togglebutton2)
		{
			if(sp.getBoolean("wifidown", false))
			{
				button.setImageResource(R.drawable.new_on_button_my);
			}
			else
			{
				button.setImageResource(R.drawable.new_off_button_my);
			}
		}
	}
	
	public void onclick(View view)// 窗口上面左边的后退按钮
	{
		switch (view.getId())
		{
			case R.id.btn_show_back_setting:// 点击窗口上面的后退按钮所在的LinearLayout，结束本Activity，跳转到上一个Activity
				GeneralsettingActivity.this.closeToast(toast);
				this.finish();
				break;
			
			case R.id.clearlayout:// 清理缓存的功能
				try
				{
					mypDialog = new ProgressDialog(this);
					mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					mypDialog.setMessage("正在清理，请稍候 ...");
					mypDialog.setIndeterminate(false);
					mypDialog.setCancelable(true);
					mypDialog.setCanceledOnTouchOutside(false);
					mypDialog.show();
					
					new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								Thread.sleep(800);
								DataCleanManager.clearAllCache(getApplicationContext());
								
								Message mess = new Message();
								mess.what = 2;
								mess.obj = "success";
								myHandler.sendMessage(mess);
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					}).start();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				break;
			
			case R.id.tofriend:// 分享给朋友
				this.shareInfo();
				break;
			
			case R.id.torefresh:// 检查版本的情况
				UmengUpdateAgent.forceUpdate(GeneralsettingActivity.this);
				
				UmengUpdateAgent.setUpdateListener(new UmengUpdateListener()
				{
					@Override
					public void onUpdateReturned(int arg0, UpdateResponse arg1)
					{
						switch (arg0)
						{
							case 0:
								UmengUpdateAgent
										.update(getApplicationContext());
								break;
							case 1:
								GeneralsettingActivity.this
										.showTextToast("当前已经是最新版本");
								break;
							default:
								GeneralsettingActivity.this
										.showTextToast("网络连接超时，请稍候再试");
								break;
						}
					}
				});
				break;
			
			case R.id.tous:// 关于我们
				Intent intent = new Intent();
				intent.setClass(this, AboutUsActivity.class);
				this.startActivity(intent);
				break;
			
			case R.id.towork:// 免责声明
				Intent intent2 = new Intent();
				intent2.setClass(this, ToWorkActivity.class);
				this.startActivity(intent2);
				break;
			
			default:
				break;
		}
	}
	
	
	// 清理缓存
	private long getEnvironmentSize()
	{
		File localFile = Environment.getDataDirectory();
		long l1;
		if (localFile == null) l1 = 0L;
		while (true)
		{
			String str = localFile.getPath();
			StatFs localStatFs = new StatFs(str);
			long l2 = localStatFs.getBlockSize();
			l1 = localStatFs.getBlockCount() * l2;
			return l1;
		}
	}
	
	
	// 实现分享的方法,方法参数就是该软件的下载地址
	public void shareMySoftware()
	{
		String appID = "wx51997f48a10bdff8";
		String appSecret = "5d7d2282149b115c8965f91bb71d7350";
		
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
		wxHandler.addToSocialSDK();
		
		// 添加微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		
//		// 设置新浪SSO handler
//		mController.getConfig().setSsoHandler(new SinaSsoHandler());
//		// 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this,
//				"100424468", "c7394704798a158208a74ab60104f0ba");
//		qZoneSsoHandler.addToSocialSDK();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null)
		{
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}
	
	/**
	 * 分享的信息
	 */
	public void shareInfo()
	{
		
		// // 设置分享图片, 参数2为图片的url地址 
		// mController.setShareMedia(new UMImage(this,
		// "http://www.umeng.com/images/pic/banner_module_social.png"));
		 //设置分享图片，参数2为本地图片的资源引用
		UMImage umi=new UMImage(this,
				 R.drawable.my);
		umi.setTitle("222222");
		umi.setTargetUrl("http://my.67az.com");
		mController.setShareMedia(umi);
		 
		 // 设置分享内容
		 mController.setShareContent("快来一起用漫云看漫画吧!最新最全的热门漫画都在里面哦~赶紧戳我下载");
		
		mController.getConfig().removePlatform(SHARE_MEDIA.SINA,
				SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.TENCENT);
		
		// 是否只有已登录用户才能打开分享选择页
		mController.openShare(this, false);
		
		mController.registerListener(mSnsPostListener);
	}
	
	SnsPostListener mSnsPostListener = new SnsPostListener()
	{
		@Override
		public void onStart()
		{
		}
		
		@Override
		public void onComplete(SHARE_MEDIA platform, int stCode,
				SocializeEntity entity)
		{
			if (stCode == 200)
			{
				GeneralsettingActivity.this.insertIntoServer(platform);
			}
			else
			{
//				Toast.makeText(GeneralsettingActivity.this,
//						"分享失败 : error code : " + stCode, Toast.LENGTH_SHORT)
//						.show();
			}
		}
	};
	
	private JSONObject param = new JSONObject();
	
	private void insertIntoServer(SHARE_MEDIA platform)
	{
		initParam(platform);
		JsonObjectRequest jsonRequest = new JsonObjectRequest(
				com.android.volley.Request.Method.POST,
				UrlConstant.UrlinsertBookShare, param,
				new Listener<JSONObject>()
				{
					@Override
					public void onResponse(JSONObject response)
					{
						
					}
				}, new ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						
					}
				})
		{
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("Content-Type", "application/json; charset=utf-8");
				return map;
			}
		};
		
		jsonRequest.setRetryPolicy(new DefaultRetryPolicy(6000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		
		NetUtil.rqueue.add(jsonRequest);
	}
	
	private void initParam(SHARE_MEDIA platform)
	{
		try
		{
			param.put("pageSize", Utils.PageSize);
			param.put("currentPage", "1");
			param.put("orderBy", "");
			JSONObject js = new JSONObject();
			js.put("mId", -1);
			js.put("shareType", platform);
			js.put("shareTo", "");
			js.put("shareFrom", "");
			param.put("object", new JSONArray().put(js));
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
	
	public void onResume()
	{
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	public void onPause()
	{
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		
		GeneralsettingActivity.this.closeToast(toast);
		this.finish();
	}
}
