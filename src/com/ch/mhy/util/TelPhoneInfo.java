package com.ch.mhy.util;

import com.ch.mhy.application.MhyApplication;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * 用户手机基本信息获取
 * @author xc.li
 *
 */
public class TelPhoneInfo {
	private static TelephonyManager telPhoneMag = (TelephonyManager)
			MhyApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE); 
	
	/**
	 * 获取用户手机号码，可能为空的，各位，不是所有手机都能获取到手机号码，取决于运营商
	 * 
	 * @return
	 */
	public static String getPhoneNum() {
		return telPhoneMag.getLine1Number();
	}
	
	/**
	 * 获取设备ID，即IMEI
	 * 
	 * @return
	 */
	public static String getDeviceId() {
		return telPhoneMag.getDeviceId();
	}
	
	/**
	 * 手机的SIM卡号
	 * 
	 * @return
	 */
	public static String getSIMID() {
		return telPhoneMag.getSimSerialNumber();
	}
	
	/**
	 * 手机用户唯一识别码
	 * 
	 * @return
	 */
	public static String getIMSI() {
		return telPhoneMag.getSubscriberId();
	}
	
	/**
	 * 取手机型号
	 * 
	 * @return
	 */
	public static String getPhoneModel() {
		return Build.MODEL;
	}
	
	/**
	 * 获取SDK版本号
	 * 
	 * @return
	 */
	public static String getSdkVersion() {
		return Build.VERSION.SDK;
	}
	
	/**
	 * 获取系统版本号
	 * 
	 * @return
	 */
	public static String getOsVersion() {
		return Build.VERSION.RELEASE;
	}
	
}
