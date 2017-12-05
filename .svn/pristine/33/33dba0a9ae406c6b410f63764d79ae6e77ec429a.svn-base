package com.ch.mhy.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 设置的工具类
 * @author Administrator
 *
 */
public class SettingUtil
{
	
    public static boolean onlyWifiReading(Context context)
    {
    	SharedPreferences sp=context.getSharedPreferences("userConfig", 0);
		if(sp.getBoolean("wifiread", false))
		{
			return true;//表示在wifi下阅读要提示
		}
    	
    	return false;
    }
    
    public static boolean onlyWifiDowning(Context context)
    {
    	SharedPreferences sp=context.getSharedPreferences("userConfig", 0);
    	if(sp.getBoolean("wifidown", false))
    	{
    		return true;//表示只能在wifi下才能下载
    	}
    	
    	return false;
    }
    
}
